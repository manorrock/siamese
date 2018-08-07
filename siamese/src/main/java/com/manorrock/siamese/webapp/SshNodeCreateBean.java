/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.node.SshNode;
import java.io.Serializable;
import java.math.BigInteger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * The bean used to create a SSH node.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Named(value = "sshNodeCreateBean")
@RequestScoped
public class SshNodeCreateBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @Inject
    private PersistenceEjb<SshNode, BigInteger> ejb;

    /**
     * Stores the description.
     */
    private String description;
    /**
     * Stores the hostname.
     */
    private String hostname;

    /**
     * Stores the password.
     */
    private String password;

    /**
     * Stores the username.
     */
    private String username;

    /**
     * Create the SSH node.
     *
     * @return "/node/index"
     */
    public String create() {
        SshNode sshNode = new SshNode();
        sshNode.setDescription(description);
        sshNode.setHostname(hostname);
        sshNode.setUsername(username);
        sshNode.setPassword(password);
        ejb.persist(sshNode);
        return "/node/index";
    }

    /**
     * Cancel creating the SSH node.
     *
     * @return "/node/index"
     */
    public String cancel() {
        return "/node/index";
    }

    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the hostname.
     *
     * @return the hostname.
     */
    public String getHostname() {
        return hostname;
    }

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
     * Set the description.
     *
     * @param description the description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the hostname.
     *
     * @param hostname the hostname.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
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
