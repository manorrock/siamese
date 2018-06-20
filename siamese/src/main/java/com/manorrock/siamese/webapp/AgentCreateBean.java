/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 * The bean used to create an agent.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "agentCreateBean")
public class AgentCreateBean implements Serializable {

    /**
     * Stores the agent type.
     */
    private String agentType;

    /**
     * Stores the agent types.
     */
    private final SelectItem[] agentTypes;

    /**
     * Constructor.
     */
    public AgentCreateBean() {
        agentTypes = new SelectItem[1];
        agentTypes[0] = new SelectItem("JavaSshAgent", "Java SSH agent");
    }

    /**
     * Get the agent type.
     *
     * @return the agent type.
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * Get the agent types.
     *
     * @return the agent types.
     */
    public SelectItem[] getAgentTypes() {
        return agentTypes;
    }

    /**
     * Set the agent type.
     *
     * @param agentType the agent type.
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * Create the pipeline.
     *
     * @return "index"
     */
    public String create() {
        String result;
        switch (agentType) {
            case "JavaSshAgent":
                result = "javaSsh/create";
                break;
            default:
                result = "index";
        }
        return result;
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
