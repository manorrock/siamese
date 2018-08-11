/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice, 
 *      this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *   3. Neither the name of the copyright holder nor the names of its 
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
