/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * A Java SSH agent.
 *
 * @author Nanfred Riem (mriem@manorrock.com)
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Table(name = "java_ssh_agent")
public class JavaSshAgent extends Agent {

    /**
     * Stores the Java home.
     */
    private String javaHome;

    /**
     * Stores the SSH hostname.
     */
    private String sshHostname;

    /**
     * Stores the SSH password.
     */
    private String sshPassword;

    /**
     * Stores the SSH username.
     */
    private String sshUsername;

    /**
     * Get the Java home.
     * 
     * @return the Java home.
     */
    public String getJavaHome() {
        return javaHome;
    }

    /**
     * Get the SSH hostname.
     * 
     * @return the SSH hostname.
     */
    public String getSshHostname() {
        return sshHostname;
    }

    /**
     * Get the SSH password.
     * 
     * @return the SSH password.
     */
    public String getSshPassword() {
        return sshPassword;
    }

    /**
     * Get the SSH username.
     * 
     * @return the SSH username.
     */
    public String getSshUsername() {
        return sshUsername;
    }
    
    /**
     * Set the Java home.
     * 
     * @param javaHome the Java home.
     */
    public void setJavaHome(String javaHome) {
        this.javaHome = javaHome;
    }

    /**
     * Set the SSH hostname.
     * 
     * @param sshHostname the SSH hostname.
     */
    public void setSshHostname(String sshHostname) {
        this.sshHostname = sshHostname;
    }

    /**
     * Set the SSH password.
     * 
     * @param sshPassword the SSH password.
     */
    public void setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
    }

    /**
     * Set the SSH username.
     * 
     * @param sshUsername the SSH username.
     */
    public void setSshUsername(String sshUsername) {
        this.sshUsername = sshUsername;
    }
}
