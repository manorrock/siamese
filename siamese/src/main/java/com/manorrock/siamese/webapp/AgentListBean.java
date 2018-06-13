/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to show a list of agents.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("agentListBean")
@Stateless
public class AgentListBean {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Get the pipelines.
     *
     * @return the pipelines.
     */
    public List<Agent> getAgents() {
        EntityManager em = application.getEntityManager();
        return em.createQuery("SELECT object(a) FROM Agent AS a", Agent.class).getResultList();
    }
}
