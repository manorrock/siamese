/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
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
