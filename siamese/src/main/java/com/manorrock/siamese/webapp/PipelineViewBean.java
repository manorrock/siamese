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

/**
 * The bean used to view a pipeline.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "pipelineViewBean")
public class PipelineViewBean implements Serializable {

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
