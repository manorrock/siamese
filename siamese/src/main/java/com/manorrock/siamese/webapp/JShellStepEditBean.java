/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.step.JShellStep;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to edit a JShell step.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "jShellStepEditBean")
public class JShellStepEditBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Stores the step id.
     */
    private BigInteger stepId;

    /**
     * Stores the step.
     */
    private JShellStep step;

    /**
     * Save the step.
     *
     * @return "/pipeline/view?pipelineId=..pipelineId.."
     */
    public String save() {
        EntityManager em = application.getEntityManager();
        step = em.merge(step);
        em.persist(step);
        return "/pipeline/view?pipelineId=" + step.getPipeline().getId();
    }

    /**
     * Cancel saving the step.
     *
     * @return "/pipeline/view?pipelineId=..pipelineId.."
     */
    public String cancel() {
        return "/pipeline/view?pipelineId=" + step.getPipeline().getId();
    }

    /**
     * Get the step.
     *
     * @return the step.
     */
    public JShellStep getStep() {
        return step;
    }

    /**
     * Get the step id.
     *
     * @return the step id.
     */
    public BigInteger getStepId() {
        return stepId;
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("stepId")) {
            stepId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("stepId"));
            EntityManager em = application.getEntityManager();
            step = em.find(JShellStep.class, stepId);
        }
    }

    /**
     * Set the step.
     *
     * @param step the step.
     */
    public void setStep(JShellStep step) {
        this.step = step;
    }
}
