/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.node.Node;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to delete a node.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named(value = "nodeDeleteBean")
@RequestScoped
public class NodeDeleteBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @Inject
    private PersistenceEjb<Node, BigInteger> ejb;

    /**
     * Stores the node.
     */
    private Node node;

    /**
     * Stores the pipeline.
     */
    private BigInteger nodeId;
    
    /**
     * Cancel deleting the pipeline.
     * 
     * @return "index"
     */
    public String cancel() {
        return "index";
    }

    /**
     * Delete the pipeline.
     *
     * @return "index"
     */
    public String delete() {
        ejb.remove(node);
        return "index";
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("nodeId")) {
            nodeId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nodeId"));
            node = ejb.find(Node.class, nodeId);
        }
    }

    /**
     * Get the node.
     *
     * @return the node.
     */
    public Node getNode() {
        return node;
    }
}
