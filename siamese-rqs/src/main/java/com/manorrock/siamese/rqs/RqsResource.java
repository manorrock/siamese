/*
 *  Copyright (c) 2002-2018, Manorrock.com. All Rights Reserved.
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
package com.manorrock.siamese.rqs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * A repo resource
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Path("")
public class RqsResource {

    /**
     * Stores the logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RqsResource.class.getName());

    /**
     * Stores the root directory.
     */
    private File rootDirectory;

    /**
     * Stores the root directory filename.
     */
    private String rootDirectoryFilename;

    /**
     * Constructor.
     */
    public RqsResource() {
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (rootDirectoryFilename == null || "".equals(rootDirectoryFilename.trim())) {
            rootDirectoryFilename = System.getenv("ROOT_DIRECTORY");
        }

        if (rootDirectoryFilename == null || "".equals(rootDirectoryFilename.trim())) {
            rootDirectoryFilename = System.getProperty("ROOT_DIRECTORY",
                    System.getProperty("user.home") + "/.siamese/rqs");
        }

        rootDirectory = new File(rootDirectoryFilename);
        if (!rootDirectory.exists()) {
            rootDirectory.mkdirs();
        }
    }

    /**
     * Get all the executions.
     * 
     * @return the list of executions.
     */
    @GET
    @Path("")
    public List<RqsExecution> getAll() {
        return new ArrayList<>();
    }
}
