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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.oyena.action.ActionMapping;

/**
 * The bean for editing a job.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("editJobBean")
@RequestScoped
public class EditJobBean {

    /**
     * Stores the application.
     */
    @Inject
    private ApplicationBean application;

    /**
     * Stores the job.
     */
    private Job job;

    /**
     * Stores the job id.
     */
    private String jobId;

    /**
     * Edit the job.
     *
     * @param request the HTTP servlet request.
     * @return the job edit page.
     */
    @ActionMapping("/edit/*")
    public String edit(HttpServletRequest request) {
        String result = "/WEB-INF/ui/edit.xhtml";
        jobId = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        DataStore dataStore = DataStoreFactory.create();
        job = dataStore.loadJob(jobId);
        if (request.getParameter("submit") != null) {
            result = "/WEB-INF/ui/view.xhtml";
        }
        return result;
    }
    
    /**
     * Get the job.
     *
     * @return the job.
     */
    public Job getJob() {
        return job;
    }
    
    /**
     * Get the job id.
     * 
     * @return the job id.
     */
    public String getJobId() {
        return jobId;
    }

    /**
     * Set the job.
     * 
     * @param job the job.
     */
    public void setJob(Job job) {
        this.job = job;
    }
    
    /**
     * Set the job id.
     * 
     * @param jobId the job id.
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
