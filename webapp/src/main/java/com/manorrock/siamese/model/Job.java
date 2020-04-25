/*
 *  Copyright (c) 2002-2020, Manorrock.com. All Rights Reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *      1. Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *
 *      2. Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *
 *      3. Neither the name of the copyright holder nor the names of its 
 *         contributors may be used to endorse or promote products derived from
 *         this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.siamese.model;

/**
 * A job.
 * 
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Job {
    
    /**
     * Stores the arguments.
     */
    private String arguments;
    
    /**
     * Stores the id.
     */
    private String id;
    
    /**
     * Stores the name.
     */
    private String name;
    
    /**
     * Stores the schedule (in cron syntax).
     */
    private String schedule;
    
    /**
     * Stores the type.
     */
    private String type;

    /**
     * Get the arguments.
     * 
     * @return the arguments.
     */
    public String getArguments() {
        return arguments;
    }
    
    /**
     * Get the id.
     * 
     * @return the id.
     */
    public String getId() {
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
     * Get the schedule.
     * 
     * @return the schedule.
     */
    public String getSchedule() {
        return schedule;
    }
    
    /**
     * Get the type.
     * 
     * @return the type.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Set the arguments.
     * 
     * @param arguments the arguments.
     */
    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
    
    /**
     * Set the id.
     * 
     * @param id the id.
     */
    public void setId(String id) {
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
    
    /**
     * Set the schedule.
     * 
     * @param schedule the schedule.
     */
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    /**
     * Set the job type.
     * 
     * @param type the job type. 
     */
    public void setType(String type) {
        this.type = type;
    }
}
