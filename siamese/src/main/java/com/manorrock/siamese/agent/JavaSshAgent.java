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
package com.manorrock.siamese.agent;

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
