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
package com.manorrock.siamese.shared;

import com.manorrock.siamese.datastore.DataStore;
import com.manorrock.siamese.datastore.DataStoreFactory;
import com.manorrock.siamese.model.Job;
import com.manorrock.siamese.model.JobOutput;
import com.manorrock.siamese.model.JobStatus;
import static com.manorrock.siamese.model.JobStatus.COMPLETED;
import it.sauronsoftware.cron4j.SchedulingPattern;
import java.io.BufferedReader;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
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
        arguments.add("echo");
        arguments.add(job.getType());
        arguments.add("--arguments");
        arguments.add(job.getArguments());
        Process process = null;
        try {
            process = builder.command(arguments).start();
            if (process.waitFor(2, HOURS)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    jobOutput.setOutput(reader.lines().collect(Collectors.joining("\n")));
                }
            }
        } catch(InterruptedException | IOException ie) {
            jobOutput.setOutput(ie.getMessage());
        } finally {
            if (process != null && process.isAlive()) {
                process.destroyForcibly();
            }
        }
        jobOutput.setStatus(COMPLETED);
        dataStore.saveJobOutput(jobOutput);
        if (LOGGER.isLoggable(INFO)) {
            LOGGER.log(INFO, "Finished with job ''{0}''", job.getName());
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
