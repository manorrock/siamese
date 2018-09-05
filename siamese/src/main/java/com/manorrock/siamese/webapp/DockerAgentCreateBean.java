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

import com.manorrock.siamese.capability.DockerCapability;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The bean used to create a Docker agent.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named(value = "dockerAgentCreateBean")
@RequestScoped
public class DockerAgentCreateBean implements Serializable {

    /**
     * Stores the EJB.
     */
    @Inject
    private PersistenceEjb<DockerCapability, BigInteger> ejb;

    /**
     * Stores the name.
     */
    private String name;

    /**
     * Stores the node id.
     */
    private BigInteger nodeId;

    /**
     * Create the Docker agent.
     *
     * @return "/agent/index"
     */
    public String create() {
        String result = "/agent/index";
        DockerCapability agent = new DockerCapability();
        agent.setName(name);
        agent.setNodeId(nodeId);
        ejb.persist(agent);
        if (nodeId != null) {
            result = "/agent/index?nodeId=" + nodeId;
        }
        return result;
    }

    /**
     * Cancel creating the Docker agent.
     *
     * @return "/agent/index"
     */
    public String cancel() {
        String result = "/agent/index";
        if (nodeId != null) {
            result = "/agent/index?nodeId=" + nodeId;
        }
        return result;
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("nodeId")) {
            nodeId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nodeId"));
        }
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
     * Get the node id.
     * 
     * @return the node id.
     */
    public BigInteger getNodeId() {
        return nodeId;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
