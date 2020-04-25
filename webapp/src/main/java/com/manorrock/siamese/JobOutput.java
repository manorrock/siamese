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

/**
 * The output of a job.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class JobOutput {
    
    /**
     * Stores the end date.
     */
    private String endDate;
    
    /**
     * Stores the job id.
     */
    private String jobId;
    
    /**
     * Stores the output.
     */
    private String output;
    
    /**
     * Stores the start timestamp.
     */
    private Date startDate;
    
    /**
     * Stores the status.
     */
    private JobStatus status;
    
    /**
     * Get the job id.
     * 
     * @return the job id.
     */
    public String getJobId() {
        return jobId;
    }
    
    /**
     * Get the output.
     * 
     * @return the output.
     */
    public String getOutput() {
        return output;
    }
    
    /**
     * Get the start date.
     * 
     * @return the start date.
     */
    public Date getStartDate() {
        return startDate;
    }
    
    /**
     * Get the status.
     * 
     * @return the status.
     */
    public JobStatus getStatus() {
        return status;
    }

    /**
     * Set the job id.
     * 
     * @param jobId the job id.
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    
    /**
     * Set the output.
     * 
     * @param output the output.
     */
    public void setOutput(String output) {
        this.output = output;
    }
    
    /**
     * Set the start date.
     * 
     * @param startDate the start dates.
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Set the status.
     * 
     * @param status the status. 
     */
    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
