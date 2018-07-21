/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 * The bean used to create a node.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "nodeCreateBean")
public class NodeCreateBean implements Serializable {

    /**
     * Stores the node type.
     */
    private String nodeType;

    /**
     * Stores the agent types.
     */
    private final SelectItem[] nodeTypes;

    /**
     * Constructor.
     */
    public NodeCreateBean() {
        nodeTypes = new SelectItem[1];
        nodeTypes[0] = new SelectItem("SshNode", "SSH Node");
    }

    /**
     * Get the agent type.
     *
     * @return the agent type.
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * Get the agent types.
     *
     * @return the agent types.
     */
    public SelectItem[] getNodeTypes() {
        return nodeTypes;
    }

    /**
     * Set the agent type.
     *
     * @param nodeType the agent type.
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * Create the node.
     *
     * @return "index"
     */
    public String create() {
        String result;
        switch (nodeType) {
            case "SshNode":
                result = "ssh/create";
                break;
            default:
                result = "index";
        }
        return result;
    }

    /**
     * Cancel creating the node.
     *
     * @return "index"
     */
    public String cancel() {
        return "index";
    }
}
