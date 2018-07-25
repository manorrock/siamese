/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.math.BigInteger;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The bean used to show a list of nodes.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("nodeListBean")
@RequestScoped
public class NodeListBean {

    /**
     * Stores the persistence EJB.
     */
    @Inject
    private PersistenceEjb<Node, BigInteger> ejb;

    /**
     * Get the nodes.
     *
     * @return the nodes.
     */
    public List<Node> getNodes() {
        return ejb.getResultList(Node.class);
    }
}
