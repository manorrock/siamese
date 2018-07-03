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
 * The bean used to edit a pipeline.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "pipelineEditBean")
public class PipelineEditBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Stores the pipeline.
     */
    private Pipeline pipeline;

    /**
     * Stores the pipeline.
     */
    private BigInteger pipelineId;
    
    /**
     * Cancel deleting the pipeline.
     * 
     * @return "index"
     */
    public String cancel() {
        return "index";
    }

    /**
     * Save the pipeline.
     *
     * @return "index"
     */
    public String save() {
        EntityManager em = application.getEntityManager();
        pipeline = em.merge(pipeline);
        em.persist(pipeline);
        return "index";
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("pipelineId")) {
            pipelineId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pipelineId"));
            pipeline = application.getEntityManager().find(Pipeline.class, pipelineId);
        }
    }

    /**
     * Get the pipeline.
     *
     * @return the pipeline.
     */
    public Pipeline getPipeline() {
        return pipeline;
    }
}
