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
 * A group.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Entity
@Table(name = "siamese_group")
public class Group implements Serializable {

    /**
     * Stores the id.
     */
    @SequenceGenerator(name = "siamese_group_id_seq", sequenceName = "siamese_group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "siamese_group_id_seq")
    @Column(name = "id")
    @Id
    private BigInteger id;
    
    /**
     * Stores the group name.
     */
    @Column(name = "groupname")
    private String groupName;
    
    /**
     * Stores the username.
     */
    @Column(name = "username")
    private String username;

    /**
     * Get the id.
     * 
     * @return the id.
     */
    public BigInteger getId() {
        return id;
    }
    
    /**
     * Get the groupName.
     * 
     * @return the groupName.
     */
    public String getGroupName() {
        return groupName;
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
     * Set the id.
     * 
     * @param id the id.
     */
    public void setId(BigInteger id) {
        this.id = id;
    }
    
    /**
     * Set the groupName.
     * 
     * @param groupName the groupName.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
