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
