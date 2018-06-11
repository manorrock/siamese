/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to create a pipeline.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "pipelineCreateBean")
public class PipelineCreateBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;

    /**
     * Stores the name.
     */
    private String name;
    
    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Create the pipeline.
     *
     * @return "index"
     */
    public String create() {
        EntityManager em = application.getEntityManager();
        Pipeline pipeline = new Pipeline();
        pipeline.setName(name);
        em.persist(pipeline);
        return "index";
    }

    /**
     * Cancel creating the Docker host.
     *
     * @return "index"
     */
    public String cancel() {
        return "index";
    }
}
