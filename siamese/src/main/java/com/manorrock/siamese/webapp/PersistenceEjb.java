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
     * Get the entity manager.
     * 
     * @return the entity manager.
     */
    public EntityManager getEntityManager() {
        return application.getEntityManager();
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
