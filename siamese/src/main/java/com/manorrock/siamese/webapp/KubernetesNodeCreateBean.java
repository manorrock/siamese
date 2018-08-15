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

import com.manorrock.siamese.node.DockerNode;
import com.manorrock.siamese.node.KubernetesNode;
import java.io.Serializable;
import java.math.BigInteger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The bean used to create a Kubernetes node.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named
@RequestScoped
public class KubernetesNodeCreateBean implements Serializable {

    /**
     * Stores the storage EJB.
     */
    @Inject
    private PersistenceEjb<KubernetesNode, BigInteger> ejb;

    /**
     * Stores the description.
     */
    private String description;
    
    /**
     * Stores the hostname.
     */
    private String hostname;

    /**
     * Stores the password.
     */
    private String password;

    /**
     * Stores the username.
     */
    private String username;

    /**
     * Create the Docker node.
     *
     * @return "/node/index"
     */
    public String create() {
        KubernetesNode kubernetes = new KubernetesNode();
        kubernetes.setDescription(description);
        kubernetes.setHostname(hostname);
        kubernetes.setUsername(username);
        kubernetes.setPassword(password);
        ejb.persist(kubernetes);
        return "/node/index";
    }

    /**
     * Cancel creating the Docker node.
     *
     * @return "/node/index"
     */
    public String cancel() {
        return "/node/index";
    }

    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
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
     * Set the description.
     *
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
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
