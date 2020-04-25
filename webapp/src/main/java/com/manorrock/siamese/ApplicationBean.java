/*
 *  Copyright (c) 2002-2020, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *      3. Neither the name of the copyright holder nor the names of its 
 *         contributors may be used to endorse or promote products derived from
 *         this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.siamese;

import static com.manorrock.siamese.JobStatus.COMPLETED;
import it.sauronsoftware.cron4j.SchedulingPattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.omnifaces.cdi.Eager;

/**
 * The one and only application (bean).
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@ApplicationScoped
@Eager
public class ApplicationBean {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ApplicationBean.class.getPackage().getName());

    /**
     * Stores the application configuration.
     */
    private ApplicationConfig config;

    /**
     * Stores the executor.
     */
    private ExecutorService executor;

    /**
     * Stores the scheduler.
     */
    private ScheduledExecutorService scheduler;

    /**
     * Execute a job.
     *
     * @param jobId the job id.
     * @param outputId the output id.
     */
    public void executeJob(String jobId, String outputId) {
        DataStore dataStore = DataStoreFactory.create();
        Job job = dataStore.loadJob(jobId);
        JobOutput jobOutput = dataStore.loadJobOutput(jobId, outputId);
        if (LOGGER.isLoggable(INFO)) {
            LOGGER.log(INFO, "Starting job ''{0}''", job.getName());
        }
        ProcessBuilder builder = new ProcessBuilder();
        ArrayList<String> arguments = new ArrayList<>();
        arguments.add(config.getCli());
        arguments.add(job.getType());
        if (job.getHostname() != null && !job.getHostname().trim().equals("")) {
            arguments.add("--hostname");
            arguments.add(job.getHostname());
        }
        if (job.getImage() != null && !job.getImage().trim().equals("")) {
            arguments.add("--image");
            arguments.add(job.getImage());
        }
        if (job.getPassword() != null && !job.getPassword().trim().equals("")) {
            arguments.add("--password");
            arguments.add(job.getPassword());
        }
        if (job.getUsername() != null && !job.getUsername().trim().equals("")) {
            arguments.add("--username");
            arguments.add(job.getUsername());
        }
        arguments.add("--arguments");
        arguments.add(job.getArguments());
        Process process = null;
        try {
            if (config.getPath() != null && !config.getPath().trim().equals("")) {
                builder.environment().put("PATH", config.getPath());
            }
            process = builder.command(arguments).start();
            if (process.waitFor(2, HOURS)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    jobOutput.setOutput(reader.lines().collect(Collectors.joining("\n")));
                }
            }
        } catch (InterruptedException | IOException ie) {
            jobOutput.setOutput(ie.getMessage());
        } finally {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        }
        jobOutput.setStatus(COMPLETED);
        dataStore.saveJobOutput(jobOutput);
        if (LOGGER.isLoggable(INFO)) {
            LOGGER.log(INFO, "Finished job ''{0}''", job.getName());
        }
    }

    /**
     * Initialize the bean
     */
    @PostConstruct
    public void initialize() {
        if (LOGGER.isLoggable(INFO)) {
            LOGGER.log(INFO, "Starting the scheduler");
        }
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            if (LOGGER.isLoggable(INFO)) {
                LOGGER.log(INFO, "Looking for jobs to be submitted");
            }
            DataStore dataStore = DataStoreFactory.create();
            List<Job> jobs = dataStore.loadAllJobs();
            long currentTime = System.currentTimeMillis();
            jobs.forEach(job -> {
                String schedule = job.getSchedule();
                if (schedule != null && !schedule.trim().equals("")) {
                    SchedulingPattern pattern = new SchedulingPattern(schedule);
                    if (pattern.match(currentTime)) {
                        submitJob(job.getId());
                    }
                }
            });
        }, 1, 1, MINUTES);
        executor = Executors.newFixedThreadPool(10);
        try {
            Jsonb jsonb = JsonbBuilder.create();
            config = jsonb.fromJson(new FileInputStream(new File(
                    System.getProperty("user.home")
                    + "/.manorrock/siamese/config.json")), ApplicationConfig.class);
        } catch (IOException ioe) {
            if (LOGGER.isLoggable(WARNING)) {
                LOGGER.log(WARNING, "Unable to load configuration", ioe);
            }
        }
    }

    /**
     * Submit a job for execution.
     *
     * @param jobId the job id.
     * @return the job output, or null if the job was not found.
     */
    public JobOutput submitJob(String jobId) {
        JobOutput jobOutput = null;
        DataStore dataStore = DataStoreFactory.create();
        Job job = dataStore.loadJob(jobId);
        if (job != null) {
            jobOutput = new JobOutput();
            jobOutput.setJobId(jobId);
            jobOutput.setStartDate(new Date());
            jobOutput.setStatus(JobStatus.PENDING);
            dataStore.saveJobOutput(jobOutput);
            if (LOGGER.isLoggable(INFO)) {
                LOGGER.log(INFO, "Submitted job ''{0}''", job.getName());
            }
            String outputId = Long.toString(jobOutput.getStartDate().getTime());
            executor.submit(() -> {
                executeJob(jobId, outputId);
            });
        }
        return jobOutput;
    }
}
