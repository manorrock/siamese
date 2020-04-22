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
import java.util.Date;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

/**
 * The one and only application (bean).
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@ApplicationScoped
public class ApplicationBean {
    
    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = 
            Logger.getLogger(ApplicationBean.class.getPackage().getName());

    /**
     * Execute a job.
     *
     * @param jobId the job id.
     * @return the job output, or null if the job was not found.
     */
    public JobOutput executeJob(String jobId) {
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
                LOGGER.log(INFO, "Starting new execution of job ''{0}''", job.getName());
            }

        }
        return jobOutput;
    }
}
