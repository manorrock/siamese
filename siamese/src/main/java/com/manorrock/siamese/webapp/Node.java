/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 */
package com.manorrock.siamese.webapp;

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
 * A node.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Entity
@Table(name = "node")
public class Node implements Serializable {

    /**
     * Stores the description.
     */
    private String description;

    /**
     * Stores the id.
     */
    @Id
    @SequenceGenerator(name = "node_id_seq", sequenceName = "node_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node_id_seq")
    @Column(name = "id")
    private BigInteger id;

    /**
     * Get the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the id.
     *
     * @return the id.
     */
    public BigInteger getId() {
        return id;
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
     * Set the id.
     *
     * @param id the id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    }
}