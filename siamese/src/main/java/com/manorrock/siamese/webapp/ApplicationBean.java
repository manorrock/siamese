/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.PasswordHash;

/**
 * The one and only application bean.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Singleton
@Startup
public class ApplicationBean {
    
    /**
     * Stores the entity manager factory.
     */
    @PersistenceContext(unitName = "siamese")
    private EntityManager em;
    
    /**
     * Stores the password hash.
     */
    @Inject
    private PasswordHash passwordHash;
    
    /**
     * Get the entity manager.
     * 
     * @return the entity manager.
     */
    public EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Initialize.
     */
    @PostConstruct
    public void initialize() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
        if (em.createQuery("SELECT object(o) FROM User AS o").getResultList().isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordHash.generate("siamese".toCharArray()));
            em.persist(user);
            Group group = new Group();
            group.setGroupName("user");
            group.setUsername("admin");
            em.persist(group);
        }
    }
}
