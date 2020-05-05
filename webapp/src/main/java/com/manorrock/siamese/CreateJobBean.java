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
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.omnifaces.oyena.action.ActionMapping;

/**
 * The bean for creating a job.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("createJobBean")
@RequestScoped
public class CreateJobBean {
    
    /**
     * Stores the job.
     */
    private Job job;

    /**
     * Stores the job types.
     */
    private List<SelectItem> jobTypes;
    
    /**
     * Initialize bean.
     */
    @PostConstruct
    public void initialize() {
        job = new Job();
        job.setArguments("Please enter your arguments");
        job.setType("local");
        jobTypes = new ArrayList<>();
        jobTypes.add(new SelectItem("docker", "Docker"));
        jobTypes.add(new SelectItem("kubernetes", "Kubernetes"));
        jobTypes.add(new SelectItem("local", "Local"));
        jobTypes.add(new SelectItem("ssh", "SSH"));
        jobTypes.add(new SelectItem("url", "URL", "URL", true));
    }

    /**
     * Show the index page.
     *
     * @param request the HTTP servlet request.
     * @return the index page.
     */
    @ActionMapping("/create")
    public String create(HttpServletRequest request) {
        String result = "/WEB-INF/ui/create.xhtml";
        if (request.getParameter("create") != null) {
            job.setName(request.getParameter("name"));
            String arguments = request.getParameter("arguments");
            if (arguments != null && !arguments.trim().equals("")) {
                job.setArguments(arguments);
            }
            String hostname = request.getParameter("hostname");
            if (hostname != null && !hostname.trim().equals("")) {
                job.setHostname(hostname);
            }
            String image = request.getParameter("image");
            if (image != null && !image.trim().equals("")) {
                job.setImage(image);
            }
            String dockerHost = request.getParameter("dockerHost");
            if (dockerHost != null && !dockerHost.trim().equals("")) {
                job.setDockerHost(dockerHost);
            }
            String knownHosts = request.getParameter("knownHosts");
            if (knownHosts != null && !knownHosts.trim().equals("")) {
                job.setKnownHosts(knownHosts);
            }
            String password = request.getParameter("password");
            if (password != null && !password.trim().equals("")) {
                job.setPassword(password);
            }
            String schedule = request.getParameter("schedule");
            if (schedule != null && !schedule.trim().equals("")) {
                job.setSchedule(schedule);
            }
            job.setType(request.getParameter("type"));
            String username = request.getParameter("username");
            if (username != null && !username.trim().equals("")) {
                job.setUsername(username);
            }
            DataStore dataStore = DataStoreFactory.create();
            dataStore.saveJob(job);
            result = "/WEB-INF/ui/index.xhtml";
        } else {
            job.setArguments(request.getParameter("arguments"));
            job.setName(request.getParameter("name"));
            job.setSchedule(request.getParameter("schedule"));
            job.setType(request.getParameter("type"));
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
     * Get the job types.
     *
     * @return the job types.
     */
    public List<SelectItem> getJobTypes() {
        return jobTypes;
    }
}
