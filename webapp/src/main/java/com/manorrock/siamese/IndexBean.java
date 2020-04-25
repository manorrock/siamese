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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.oyena.action.ActionMapping;

/**
 * The bean behind the index page.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("indexBean")
@RequestScoped
public class IndexBean {

    /**
     * Stores the jobs.
     */
    private List<Job> jobs;

    /**
     * Get the jobs.
     *
     * @return the jobs.
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        jobs = new ArrayList<>();
        DataStore dataStore = DataStoreFactory.create();
        jobs = dataStore.loadAllJobs();
    }
    
    /**
     * Delete a job.
     *
     * @param request the HTTP servlet request.
     * @return the index page.
     */
    @ActionMapping("/delete/*")
    public String delete(HttpServletRequest request) {
        String id = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        DataStore dataStore = DataStoreFactory.create();
        dataStore.deleteJob(id);
        jobs = dataStore.loadAllJobs();
        return "/WEB-INF/ui/index.xhtml";
    }

    /**
     * Show the index page.
     * 
     * @return the index page.
     */
    @ActionMapping("/")
    public String index() {
        return "/WEB-INF/ui/index.xhtml";
    }
}
