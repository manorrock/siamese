/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.node.SshNode;
import java.io.Serializable;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The bean used to view a SSH node.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named(value = "sshNodeViewBean")
@RequestScoped
public class SshNodeViewBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @Inject
    private PersistenceEjb<SshNode, BigInteger> ejb;

    /**
     * Stores the SSH node.
     */
    private SshNode node;

    /**
     * Stores the pipeline.
     */
    private BigInteger nodeId;
    
    /**
     * Handle back.
     * 
     * @return "/node/index"
     */
    public String back() {
        return "/node/index";
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("nodeId")) {
            nodeId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nodeId"));
            node = ejb.find(SshNode.class, nodeId);
        }
    }

    /**
     * Get the pipeline.
     *
     * @return the pipeline.
     */
    public SshNode getNode() {
        return node;
    }
}
