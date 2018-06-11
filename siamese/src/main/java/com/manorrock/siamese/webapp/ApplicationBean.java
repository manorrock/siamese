/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The one and only application bean.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
public class ApplicationBean {
    
    /**
     * Stores the entity manager factory.
     */
    @PersistenceContext(unitName = "siamese")
    private EntityManager em;
    
    /**
     * Get the entity manager.
     * 
     * @return the entity manager.
     */
    public EntityManager getEntityManager() {
        return em;
    }
}
