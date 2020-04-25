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
package com.manorrock.siamese;

import java.net.URI;
import java.net.URISyntaxException;
import static java.util.logging.Level.WARNING;
import java.util.logging.Logger;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * The /job API endpoint.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Path("job")
@RequestScoped
public class JobResource {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(JobResource.class.getPackage().getName());

    /**
     * Stores the application (bean).
     */
    @Inject
    private ApplicationBean application;

    /**
     * Submit a job.
     * 
     * @param id the id.
     * @return the response.
     */
    private Response submitJob(String id) {
        Response response = Response.status(404).build();
        JobOutput jobOutput = application.submitJob(id);
        if (jobOutput != null) {
            try {
                URI uri = new URI("job/" + id + "/output/" + jobOutput.getStartDate().getTime());
                response = Response.created(uri).build();
            } catch (URISyntaxException use) {
                if (LOGGER.isLoggable(WARNING)) {
                    LOGGER.log(WARNING, "URI Syntax error while triggering job execution", use);
                }
            }
        }
        return response;
    }

    /**
     * Submit a job.
     *
     * @param id the id.
     * @return 201 if created, 404 if not found.
     */
    @GET
    @Path("{id}/submit")
    public Response submitJobAsGet(@PathParam("id") String id) {
        return submitJob(id);
    }

    /**
     * Submit a job.
     *
     * @param id the id.
     * @return 201 if created, 404 if not found.
     */
    @POST
    @Path("{id}/submit")
    public Response submitJobAsPost(@PathParam("id") String id) {
        return submitJob(id);
    }
}
