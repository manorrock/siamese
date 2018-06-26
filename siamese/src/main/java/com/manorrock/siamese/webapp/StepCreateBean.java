/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
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
