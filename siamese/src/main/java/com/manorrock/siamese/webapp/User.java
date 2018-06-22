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
 * A User.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Entity
@Table(name = "siamese_user")
public class User implements Serializable {

    /**
     * Stores the id.
     */
    @SequenceGenerator(name = "siamese_user_id_seq", sequenceName = "siamese_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "siamese_user_id_seq")
    @Column(name = "id")
    @Id
    private BigInteger id;
    
    /**
     * Stores the password.
     */
    @Column(name = "password")
    private String password;
    
    /**
     * Stores the username.
     */
    @Column(name = "username", unique = true)
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
     * Set the id.
     * 
     * @param id the id.
     */
    public void setId(BigInteger id) {
        this.id = id;
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
