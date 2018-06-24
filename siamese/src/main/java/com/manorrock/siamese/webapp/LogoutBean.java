/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The logout bean.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named(value = "logoutBean")
@RequestScoped
public class LogoutBean {
    
    /**
     * Stores the external context.
     */
    @Inject
    private ExternalContext externalContext;

    /**
     * Cancel the logout.
     * 
     * @return "index"
     */
    public String cancel() {
        return "index";
    }
    
    /**
     * Execute the logout.
     * 
     * @return "index"
     */
    public String logout() {
        externalContext.invalidateSession();
        return "index";
    }
}
