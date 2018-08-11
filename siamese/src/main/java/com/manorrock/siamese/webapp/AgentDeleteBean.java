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

import com.manorrock.siamese.agent.Agent;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to delete an agent.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "agentDeleteBean")
public class AgentDeleteBean implements Serializable {

    /**
     * Stores the agent.
     */
    private Agent agent;

    /**
     * Stores the agent id.
     */
    private BigInteger agentId;

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Cancel deleting the agent.
     *
     * @return "index"
     */
    public String cancel() {
        return "index";
    }

    /**
     * Delete the agent.
     *
     * @return "index"
     */
    public String delete() {
        EntityManager em = application.getEntityManager();
        agent = em.merge(agent);
        em.remove(agent);
        return "index";
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("agentId")) {
            agentId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("agentId"));
            agent = application.getEntityManager().find(Agent.class, agentId);
        }
    }

    /**
     * Get the pipeline.
     *
     * @return the pipeline.
     */
    public Agent getAgent() {
        return agent;
    }
}
