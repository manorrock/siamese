/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.step.Step;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to delete a step.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "stepDeleteBean")
public class StepDeleteBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Stores the step.
     */
    private Step step;

    /**
     * Stores the pipeline.
     */
    private BigInteger stepId;
    
    /**
     * Cancel deleting the step.
     * 
     * @return "index"
     */
    public String cancel() {
        BigInteger pipelineId = step.getPipeline().getId();
        return "/pipeline/index?pipelineId=" + pipelineId;
    }

    /**
     * Delete the step.
     *
     * @return "index"
     */
    public String delete() {
        BigInteger pipelineId = step.getPipeline().getId();
        EntityManager em = application.getEntityManager();
        step = em.merge(step);
        em.remove(step);
        return "/pipeline/index?pipelineId=" + pipelineId;
    }

    /**
     * Get the step.
     *
     * @return the step.
     */
    public Step getStep() {
        return step;
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("stepId")) {
            stepId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("stepId"));
            step = application.getEntityManager().find(Step.class, stepId);
        }
    }
}
