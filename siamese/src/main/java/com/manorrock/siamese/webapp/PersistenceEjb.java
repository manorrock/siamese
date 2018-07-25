/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * The Persistence EJB.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 * @param <T> the type of the object.
 * @param <K> the type of the primary key.
 */
@Stateless
public class PersistenceEjb<T, K> implements Serializable {

    /**
     * Stores the application EJB.
     */
    @EJB
    private ApplicationBean application;
    
    /**
     * Find the object.
     *
     * @param clazz the object class.
     * @param primaryKey the primary key.
     * @return the object.
     */
    public T find(Class<T> clazz, K primaryKey) {
        EntityManager em = application.getEntityManager();
        return em.find(clazz, primaryKey);
    }

    /**
     * Get the result list.
     *
     * <p>
     * Be aware that this result list does not limit the number of results, so
     * it can exhaust the system if we are going after a table with too many
     * entries.
     * </p>
     *
     * @param clazz the class.
     * @return the list.
     */
    public List<T> getResultList(Class<T> clazz) {
        EntityManager em = application.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        return em.createQuery(criteria).getResultList();
    }

    /**
     * Persist the object.
     *
     * @param t the object.
     */
    public void persist(T t) {
        EntityManager em = application.getEntityManager();
        em.persist(t);
    }

    /**
     * Remove the object.
     *
     * @param t the object.
     */
    public void remove(T t) {
        EntityManager em = application.getEntityManager();
        t = em.merge(t);
        em.remove(t);
    }
}
