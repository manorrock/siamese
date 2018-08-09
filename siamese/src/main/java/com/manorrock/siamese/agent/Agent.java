/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.agent;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * An agent.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Entity
@Table(name = "agent")
public class Agent implements Serializable {

    /**
     * Stores the id.
     */
    @Id
    @SequenceGenerator(name = "agent_id_seq", sequenceName = "agent_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agent_id_seq")
    @Column(name = "id")
    private BigInteger id;

    /**
     * Stores the name.
     */
    private String name;

    /**
     * Get the id.
     *
     * @return the id.
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Get the name.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Set the name.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
