/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to create a JShell step.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "jShellStepCreateBean")
public class JShellStepCreateBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Stores the pipeline id.
     */
    private BigInteger pipelineId;

    /**
     * Stores the script.
     */
    private String script;

    /**
     * Create the JShell step.
     *
     * @return "/pipeline/index"
     */
    public String create() {
        EntityManager em = application.getEntityManager();
        Pipeline pipeline = em.find(Pipeline.class, pipelineId);
        JShellStep step = new JShellStep();
        step.setPipeline(pipeline);
        step.setScript(script);
        step.setStepId(BigInteger.ONE);
        em.persist(step);
        return "/pipeline/index";
    }

    /**
     * Cancel creating the JShell step.
     *
     * @return "/pipeline/index"
     */
    public String cancel() {
        return "/pipeline/index";
    }
    
    /**
     * Get the pipeline id.
     * 
     * @return the pipeline id.
     */
    public BigInteger getPipelineId() {
        return pipelineId;
    }

    /**
     * Get the script.
     *
     * @return the script.
     */
    public String getScript() {
        return script;
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("pipelineId")) {
            pipelineId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pipelineId"));
        }
    }

    /**
     * Set the script.
     *
     * @param script the script.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
