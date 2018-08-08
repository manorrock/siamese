/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.step;

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
@Table(name = "jshell_step")
public class JShellStep extends Step {

    /**
     * Stores the script.
     */
    private String script;

    /**
     * Get the script.
     * 
     * @return the script.
     */
    public String getScript() {
        return script;
    }

    /**
     * Set the script.
     * 
     * @param script the script.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
