/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 * The EJB for Nodes.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
public class NodeEjb {
    
    /**
     * Get the list of nodes.
     * 
     * @return the nodes.
     */
    public List<Node> getNodes() {
        ArrayList<Node> result = new ArrayList<>();
        Node node = new Node();
        node.setDescription("my node");
        result.add(node);
        return result;
    }
}
