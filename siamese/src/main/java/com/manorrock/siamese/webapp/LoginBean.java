/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import javax.security.enterprise.SecurityContext;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The login bean.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {
    
    /**
     * Stores the External context.
     */
    @Inject
    private ExternalContext externalContext;

    /**
     * Stores the Faces context.
     */
    @Inject
    private FacesContext facesContext;

    /**
     * Stores the Security context.
     */
    @Inject
    private SecurityContext securityContext;

    /**
     * Stores the password.
     */
    private String password;

    /**
     * Stores the username.
     */
    private String username;

    /**
     * Get the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Execute the login.
     */
    public void login() {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                withParams().credential(credential));

        if (status.equals(SEND_CONTINUE)) {
            facesContext.responseComplete();
        } else if (status.equals(SEND_FAILURE)) {
            facesContext.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Authentication failed", null));
        }
    }

    /**
     * Set the password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the username.
     *
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
