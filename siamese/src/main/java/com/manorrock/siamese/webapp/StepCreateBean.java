/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 * The bean used to create a pipeline.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "stepCreateBean")
public class StepCreateBean implements Serializable {

    /**
     * Stores the description.
     */
    private String description;
    
    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     *
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Create the pipeline.
     *
     * @return "index"
     */
    public String create() {
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
