/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
     * Stores the application bean.
     */
    @EJB
    private NodeEjb ejb;

    /**
     * Get the pipelines.
     *
     * @return the pipelines.
     */
    public List<Node> getNodes() {
        return ejb.getNodes();
    }
}
