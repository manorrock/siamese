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
