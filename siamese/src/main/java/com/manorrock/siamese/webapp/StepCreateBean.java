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

import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 * The bean used to create a pipeline step.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "stepCreateBean")
public class StepCreateBean implements Serializable {

    /**
     * Stores the pipeline id.
     */
    private BigInteger pipelineId;

    /**
     * Stores the step type.
     */
    private String stepType;

    /**
     * Stores the step types.
     */
    private final SelectItem[] stepTypes;

    /**
     * Constructor.
     */
    public StepCreateBean() {
        stepTypes = new SelectItem[1];
        stepTypes[0] = new SelectItem("jshell", "JShell Step");
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
     * Get the step type.
     *
     * @return the step type.
     */
    public String getStepType() {
        return stepType;
    }

    /**
     * Get the step types.
     *
     * @return the step types.
     */
    public SelectItem[] getStepTypes() {
        return stepTypes;
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
     * Set the step type.
     *
     * @param stepType the step type.
     */
    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    /**
     * Create the step.
     *
     * @return "index"
     */
    public String create() {
        String result;
        switch (stepType) {
            case "jshell":
                result = "/pipeline/step/jshell/create?pipelineId=" + pipelineId;
                break;
            default:
                result = "/pipeline/step/create";
        }
        return result;
    }

    /**
     * Cancel creating the step.
     *
     * @return "index"
     */
    public String cancel() {
        return "index";
    }
}
