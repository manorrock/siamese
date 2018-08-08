/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.step.Step;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.TypedQuery;

/**
 * The bean used to show a list of steps.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named
@RequestScoped
public class StepListBean implements Serializable {
    
    /**
     * Stores the EJB.
     */
    @Inject
    private PersistenceEjb<Step, BigInteger> ejb;

    /**
     * Stores the pipeline id.
     */
    @Inject
    @ManagedProperty(value = "#{param['pipelineId']}")
    private String pipelineId;
    
    /**
     * Stores the steps.
     */
    private List<Step> steps;

    /**
     * Get the pipeline id.
     * 
     * @return the pipeline id.
     */
    public BigInteger getPipelineId() {
        return new BigInteger(pipelineId);
    }
    
    /**
     * Get the pipeline steps.
     * 
     * @return the pipeline steps.
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (pipelineId != null) {
            TypedQuery<Step> query = ejb.getEntityManager().createQuery(
                    "SELECT object(o) FROM Step AS o WHERE o.pipeline.id = :pipelineId", Step.class);
            query.setParameter("pipelineId", getPipelineId());
            steps = query.getResultList();
        }
    }
}
