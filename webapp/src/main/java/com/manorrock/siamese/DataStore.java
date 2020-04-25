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

import java.util.Date;
import java.util.List;

/**
 * A data store.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public interface DataStore {

    /**
     * Delete a job.
     *
     * @param id the id.
     */
    void deleteJob(String id);

    /**
     * Delete a job output.
     * 
     * @param jobId the job id.
     * @param outputId the output id.
     */
    void deleteJobOutput(String jobId, String outputId);

    /**
     * Load all the job start dates.
     *
     * @param id the job id.
     * @return the list of start date.
     */
    List<Date> loadAllJobStartDates(String id);

    /**
     * Load all the jobs.
     *
     * @return the list of jobs.
     */
    List<Job> loadAllJobs();

    /**
     * Load a job.
     *
     * @param id the id.
     * @return the job, or null if not found.
     */
    Job loadJob(String id);

    /**
     * Load the job output.
     * 
     * @param jobId the job id.
     * @param outputId the output id.
     * @return the job output.
     */
    JobOutput loadJobOutput(String jobId, String outputId);

    /**
     * Save the job.
     *
     * @param job the job.
     */
    void saveJob(Job job);

    /**
     * Save the job run.
     * 
     * @param jobRun the job run.
     */
    void saveJobOutput(JobOutput jobRun);
}
