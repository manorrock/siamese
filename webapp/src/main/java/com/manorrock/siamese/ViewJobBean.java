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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.oyena.action.ActionMapping;

/**
 * The bean for viewing a job.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("viewJobBean")
@RequestScoped
public class ViewJobBean {

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
     * Stores the job start dates.
     */
    private List<Date> startDates;

    /**
     * Get the job.
     *
     * @return the job.
     */
    public Job getJob() {
        return job;
    }

    /**
     * Get the start dates.
     *
     * @return the start dates.
     */
    public List<Date> getStartDates() {
        return startDates;
    }

    /**
     * Delete a job output.
     *
     * @param request the HTTP servlet request.
     * @return the job view page.
     */
    @ActionMapping("/output/delete/*")
    public String deleteJobOutput(HttpServletRequest request) {
        String outputId = request.getRequestURI().substring(
                request.getRequestURI().lastIndexOf("/") + 1);
        String jobId = request.getParameter("jobId");
        DataStore dataStore = DataStoreFactory.create();
        job = dataStore.loadJob(jobId);
        dataStore.deleteJobOutput(jobId, outputId);
        startDates = dataStore.loadAllJobStartDates(jobId);
        return "/WEB-INF/ui/view.xhtml";
    }

    /**
     * Manually submit a job.
     *
     * @param request the HTTP servlet request.
     * @return the job view page.
     */
    @ActionMapping("/submit/*")
    public String submit(HttpServletRequest request) {
        String id = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        DataStore dataStore = DataStoreFactory.create();
        job = dataStore.loadJob(id);
        JobOutput jobOutput = application.submitJob(id);
        if (jobOutput != null) {
            startDates = dataStore.loadAllJobStartDates(id);
        }
        return "/WEB-INF/ui/view.xhtml";
    }

    /**
     * Show the job.
     *
     * @param request the HTTP servlet request.
     * @return the job view page.
     */
    @ActionMapping("/view/*")
    public String view(HttpServletRequest request) {
        String id = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        DataStore dataStore = DataStoreFactory.create();
        job = dataStore.loadJob(id);
        startDates = dataStore.loadAllJobStartDates(id);
        return "/WEB-INF/ui/view.xhtml";
    }
}
