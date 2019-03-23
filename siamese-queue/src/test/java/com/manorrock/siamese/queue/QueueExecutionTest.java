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

import java.net.URL;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * The JUnit tests for the QueueExecution class.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@RunWith(Arquillian.class)
public class QueueExecutionTest {

    /**
     * Stores the base URL.
     */
    @ArquillianResource
    private URL baseUrl;

    /**
     * Stores the client.
     */
    private Client client;

    /**
     * Setup before testing.
     */
    @Before
    public void before() {
        client = ClientBuilder.newClient();
    }

    /**
     * Create the deployment web archive.
     *
     * @return the deployment web archive.
     */
    @Deployment
    public static WebArchive createDeployment() {
        return create(WebArchive.class).
                addClasses(QueueApplication.class, QueueResource.class);
    }

    /**
     * Tear down after testing.
     */
    @After
    public void after() {
        client.close();
    }

    /**
     * Test creating a queue execution.
     *
     * @throws Exception when a serious error occurs.
     */
    @RunAsClient
    @Test
    public void testCreate() throws Exception {
        WebTarget target = client.target(baseUrl.toURI());
        QueueExecution execution = new QueueExecution();
        target.path("api").request("application/json").post(Entity.json(execution));
    }

    /**
     * Test getting a list of queue executions.
     *
     * @throws Exception when a serious error occurs.
     */
    @RunAsClient
    @Test
    public void testGetAll() throws Exception {
        WebTarget target = client.target(baseUrl.toURI());
        QueueExecution execution = new QueueExecution();
        target.path("api").request("application/json").post(Entity.json(execution));
        target = client.target(baseUrl.toURI());
        List<QueueExecution> executions = target.path("api").request().
                get(new GenericType<List<QueueExecution>>(){});
        assertFalse(executions.isEmpty());
    }

    /**
     * Test deleting an execution..
     *
     * @throws Exception when a serious error occurs.
     */
    @RunAsClient
    @Test
    public void testDelete() throws Exception {
        WebTarget target = client.target(baseUrl.toURI());
        QueueExecution execution = new QueueExecution();
        target.path("api").request("application/json").post(Entity.json(execution));
        target = client.target(baseUrl.toURI());
        List<QueueExecution> executions = target.path("api").request().
                get(new GenericType<List<QueueExecution>>(){});
        assertFalse(executions.isEmpty());
        execution = executions.get(0);
        target.path("api").path(execution.getId()).request("application/json").delete();
    }
}
