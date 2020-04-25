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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * A file data store (default for Manorrock Siamese).
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class FileDataStore implements DataStore {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(FileDataStore.class.getPackage().getName());

    /**
     * Stores the base directory.
     */
    private final File baseDirectory;

    /**
     * Constructor.
     *
     * @param baseDirectory the base directory.
     */
    public FileDataStore(File baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    /**
     * Delete a job.
     *
     * @param id the id.
     */
    @Override
    public void deleteJob(String id) {
        File configFile = new File(baseDirectory, id + File.separator + "config.json");
        if (configFile.exists()) {
            try {
                Path path = configFile.getParentFile().toPath();
                Files.walk(path)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (IOException ioe) {
                if (LOGGER.isLoggable(WARNING)) {
                    LOGGER.log(WARNING, "An error occurred while deleting the job", ioe);
                }
            }
        }
    }

    /**
     * Delete a job output.
     *
     * @param jobId the job id.
     * @param outputId the output id.
     */
    @Override
    public void deleteJobOutput(String jobId, String outputId) {
        File jobOutputFile = new File(baseDirectory, jobId + File.separator +
                outputId + "-output.json");
        if (jobOutputFile.exists()) {
            jobOutputFile.delete();
        }
    }

    /**
     * Load all the job start dates.
     *
     * @param id the id.
     * @return the list of job start dates.
     */
    @Override
    public List<Date> loadAllJobStartDates(String id) {
        ArrayList<Date> result = new ArrayList<>();
        File configFile = new File(baseDirectory, id + File.separator + "config.json");
        if (configFile.exists()) {
            File[] outputFiles = configFile.getParentFile().listFiles(
                    (File directory, String name) -> {
                        return name.endsWith("-output.json");
                    });
            if (outputFiles != null && outputFiles.length > 0) {
                for (File outputFile : outputFiles) {
                    result.add(new Date(Long.parseLong(outputFile.getName().
                            substring(0, outputFile.getName().indexOf("-")))));
                }
            }
        }
        return result;
    }

    /**
     * Load all the jobs.
     *
     * @return the jobs.
     */
    @Override
    public List<Job> loadAllJobs() {
        ArrayList<Job> result = new ArrayList<>();
        File[] files = baseDirectory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File configFile = new File(file, "config.json");
                    if (configFile.exists()) {
                        try {
                            Jsonb jsonb = JsonbBuilder.create();
                            String json = new String(Files.readAllBytes(configFile.toPath()));
                            result.add(jsonb.fromJson(json, Job.class));
                        } catch (IOException ioe) {
                            if (LOGGER.isLoggable(WARNING)) {
                                LOGGER.log(WARNING, "Unable to load job", ioe);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * Load the job.
     *
     * @param id the id.
     * @return the job, or null if not found.
     */
    @Override
    public Job loadJob(String id) {
        Job result = null;
        File configFile = new File(baseDirectory, id + File.separator + "config.json");
        if (configFile.exists()) {
            try {
                Jsonb jsonb = JsonbBuilder.create();
                String json = new String(Files.readAllBytes(configFile.toPath()));
                result = jsonb.fromJson(json, Job.class);
            } catch (IOException ioe) {
                if (LOGGER.isLoggable(WARNING)) {
                    LOGGER.log(WARNING, "Unable to load job", ioe);
                }
            }
        }
        return result;
    }

    /**
     * Load the job output.
     *
     * @param jobId the job id.
     * @param outputId the output id.
     * @return the job output.
     */
    @Override
    public JobOutput loadJobOutput(String jobId, String outputId) {
        JobOutput result = null;
        File jobOutputFile = new File(baseDirectory, jobId + File.separator + outputId + "-output.json");
        if (jobOutputFile.exists()) {
            try {
                Jsonb jsonb = JsonbBuilder.create();
                String json = new String(Files.readAllBytes(jobOutputFile.toPath()));
                result = jsonb.fromJson(json, JobOutput.class);
            } catch (IOException ioe) {
                if (LOGGER.isLoggable(WARNING)) {
                    LOGGER.log(WARNING, "Unable to load job output", ioe);
                }
            }
        }
        return result;
    }

    /**
     * Save the job.
     *
     * @param job the job.
     */
    @Override
    public void saveJob(Job job) {
        File jobFile;
        if (job.getId() == null) {
            Long id = 1L;
            jobFile = new File(baseDirectory, id.toString());
            while (jobFile.exists()) {
                id++;
                jobFile = new File(baseDirectory, id.toString());
            }
            job.setId(id.toString());
        }
        jobFile = new File(baseDirectory, job.getId() + File.separator + "config.json");
        if (!jobFile.getParentFile().exists()) {
            jobFile.getParentFile().mkdirs();
        }
        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(job);
        try (FileOutputStream fileOutput = new FileOutputStream(jobFile)) {
            fileOutput.write(json.getBytes("UTF-8"));
            fileOutput.flush();
        } catch (IOException ioe) {
            if (LOGGER.isLoggable(WARNING)) {
                LOGGER.log(WARNING, "Unable to save job", ioe);
            }
        }
    }

    /**
     * Save the job output.
     *
     * @param jobOutput the job output.
     */
    @Override
    public void saveJobOutput(JobOutput jobOutput) {
        File outputFile = new File(baseDirectory, jobOutput.getJobId()
                + File.separator + jobOutput.getStartDate().getTime() + "-output.json");
        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(jobOutput);
        try (FileOutputStream fileOutput = new FileOutputStream(outputFile)) {
            fileOutput.write(json.getBytes("UTF-8"));
            fileOutput.flush();
        } catch (IOException ioe) {
            if (LOGGER.isLoggable(WARNING)) {
                LOGGER.log(WARNING, "Unable to save job output", ioe);
            }
        }
    }
}
