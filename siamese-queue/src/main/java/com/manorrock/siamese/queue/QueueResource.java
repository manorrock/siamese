/*
 *  Copyright (c) 2002-2019, Manorrock.com. All Rights Reserved.
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
package com.manorrock.siamese.queue;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * The Queue JAX-RS resource.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Path("")
@Singleton
public class QueueResource {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(QueueResource.class.getName());

    /**
     * Stores the executions.
     */
    private final HashMap<String, QueueExecution> executions = new HashMap<>();

    /**
     * Get all the executions.
     *
     * @return the list of executions.
     */
    @GET
    @Produces("application/json")
    public Collection<QueueExecution> getAll() {
        LOGGER.log(Level.FINEST, "Get all: {0}", executions.values());
        return executions.values();
    }

    /**
     * Create an executions.
     *
     * @param execution the execution to create.
     * @return the execution.
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public QueueExecution create(QueueExecution execution) {
        LOGGER.log(Level.FINEST, "Create queue execution: {0}", execution);
        execution.setId(UUID.randomUUID().toString());
        while (executions.containsKey(execution.getId())) {
            execution.setId(UUID.randomUUID().toString());
        }
        executions.put(execution.getId(), execution);
        LOGGER.log(Level.FINEST, "Create queue execution with id: {0}", execution.getId());
        return execution;
    }

    /**
     * Delete an execution.
     *
     * @param id the id of the execution to delete.
     */
    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    public void delete(@PathParam("id") String id) {
        LOGGER.log(Level.FINEST, "Delete execution with id: {0}", id);
        executions.remove(id);
    }
    
    
    /**
     * Get an execution.
     *
     * @param id the id of the execution.
     * @return the execution.
     */
    @GET
    @Produces("application/json")
    @Path("{id}")
    public QueueExecution get(@PathParam("id") String id) {
        LOGGER.log(Level.FINEST, "Get queue execution: {0}", id);
        QueueExecution execution = executions.get(id);
        LOGGER.log(Level.FINEST, "Got queue execution: {0}", execution);
        return execution;
    }
}
