/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.annotation.FacesConfig;
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * The bean used to show a list of pipelines.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named("pipelineListBean")
@Stateless
@FacesConfig(version = JSF_2_3)
public class PipelineListBean {
    
    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;
    
    /**
     * Get the pipelines.
     * 
     * @return the pipelines.
     */
    public List<Pipeline> getPipelines() {
        EntityManager em = application.getEntityManager();
        return em.createQuery("SELECT object(p) FROM Pipeline AS p", Pipeline.class).getResultList();
    }
}
