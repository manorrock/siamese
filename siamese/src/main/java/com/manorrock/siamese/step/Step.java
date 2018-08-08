/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.step;

import com.manorrock.siamese.webapp.Pipeline;
import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * A step.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Entity
@Table(name = "step")
public class Step implements Serializable {

    /**
     * Stores the id.
     */
    @Id
    @SequenceGenerator(name = "step_id_seq", sequenceName = "step_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_id_seq")
    @Column(name = "id")
    private BigInteger id;
    
    /**
     * Stores the pipeline.
     */
    @JoinColumn(name = "pipeline_id")
    @ManyToOne
    private Pipeline pipeline;
    
    /**
     * Stores the step id.
     */
    @Column(name = "step_id")
    private BigInteger stepId;

    /**
     * Get the id.
     *
     * @return the id.
     */
    public BigInteger getId() {
        return id;
    }
    
    /**
     * Get the pipeline.
     * 
     * @return the pipeline.
     */
    public Pipeline getPipeline() {
        return pipeline;
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
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Set the pipeline.
     * 
     * @param pipeline the pipeline.
     */
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * Set the step id.
     * 
     * @param stepId the step id. 
     */
    public void setStepId(BigInteger stepId) {
        this.stepId = stepId;
    }
}
