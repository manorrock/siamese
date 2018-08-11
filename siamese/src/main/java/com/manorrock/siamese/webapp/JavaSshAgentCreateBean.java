/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice, 
 *      this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *   3. Neither the name of the copyright holder nor the names of its 
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.agent.JavaSshAgent;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to create a Java SSH agent.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "javaSshAgentCreateBean")
public class JavaSshAgentCreateBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;
    
    /**
     * Stores the hostname.
     */
    private String hostname;
    
    /**
     * Stores the Java home.
     */
    private String javaHome;

    /**
     * Stores the name.
     */
    private String name;
    
    /**
     * Stores the password.
     */
    private String password;
    
    /**
     * Stores the username.
     */
    private String username;

    /**
     * Create the Java SSH agent.
     *
     * @return "/agent/index"
     */
    public String create() {
        EntityManager em = application.getEntityManager();
        JavaSshAgent agent = new JavaSshAgent();
        agent.setName(name);
        agent.setSshHostname(hostname);
        agent.setSshUsername(username);
        agent.setSshPassword(password);
        agent.setJavaHome(javaHome);
        em.persist(agent);
        return "/agent/index";
    }

    /**
     * Cancel creating the Java SSH agent.
     *
     * @return "/agent/index"
     */
    public String cancel() {
        return "/agent/index";
    }

    /**
     * Get the Java home.
     * 
     * @return the Java home.
     */
    public String getJavaHome() {
        return javaHome;
    }

    /**
     * Get the hostname.
     *
     * @return the hostname.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the password.
     * 
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the username.
     * 
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the Java home.
     * 
     * @param javaHome the Java home.
     */
    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }
    
    /**
     * Set the hostname.
     *
     * @param hostname the hostname.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the password.
     * 
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the username.
     * 
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
