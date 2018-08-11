/*
 * Copyright (c) 2002-2018 Manorrock.com. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *   1. Redistributions of source code must retain the above copyright notice, 
 *      this list of conditions and the following disclaimer.
 *   2. Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 *   3. Neither the name of the copyright holder nor the names of its 
 *      contributors may be used to endorse or promote products derived from this
 *      software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.manorrock.siamese.webapp;

import com.manorrock.siamese.agent.JavaSshAgent;
import com.manorrock.siamese.step.JShellStep;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * The bean used to execute a pipeline.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Stateless
@Named(value = "pipelineExecuteBean")
public class PipelineExecuteBean implements Serializable {

    /**
     * Stores the application bean.
     */
    @EJB
    private ApplicationBean application;
    
    /**
     * Stores the output.
     */
    private String output;

    /**
     * Stores the pipeline.
     */
    private Pipeline pipeline;

    /**
     * Stores the pipeline.
     */
    private BigInteger pipelineId;

    /**
     * Cancel deleting the pipeline.
     *
     * @return "index"
     */
    public String cancel() {
        return "index";
    }

    /**
     * Delete the pipeline.
     *
     * @return "index"
     */
    public String execute() {
        StringBuilder builder = new StringBuilder();
        JSch jsch = new JSch();
        try {
            EntityManager em = application.getEntityManager();
            TypedQuery<JavaSshAgent> query = em.createQuery("SELECT object(o) FROM JavaSshAgent AS o", JavaSshAgent.class);
            JavaSshAgent agent = query.getResultList().get(0);
            String username = agent.getSshUsername();
            String hostname = agent.getSshHostname();
            String password = agent.getSshPassword();
            TypedQuery<JShellStep> jshellQuery = em.createQuery(
                    "SELECT object(o) FROM JShellStep AS o WHERE o.pipeline.id = :pipelineId", JShellStep.class);
            jshellQuery.setParameter("pipelineId", pipeline.getId());
            JShellStep jshellStep = jshellQuery.getResultList().get(0);
            String script = jshellStep.getScript();
            Session session = jsch.getSession(username, hostname, 22);
            session.setPassword(password);
            Properties sessionConfig = new Properties();
            sessionConfig.put("StrictHostKeyChecking", "no");
            sessionConfig.put("PreferredAuthentications", "password");
            session.setConfig(sessionConfig);
            session.connect();
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand("jshell -");
            PrintStream printStream = new PrintStream(channel.getOutputStream(), true);
            InputStream in = channel.getInputStream();
            channel.connect();
            printStream.write(script.getBytes());
            printStream.close();
            byte[] buffer = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(buffer, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    builder.append(new String(buffer, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
            channel.disconnect();
            session.disconnect();
            output = builder.toString();
        } catch (JSchException | IOException e) {
            output = e.getMessage();
        }
        return "";
    }

    /**
     * Initialize the bean.
     */
    @PostConstruct
    public void initialize() {
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().containsKey("pipelineId")) {
            pipelineId = new BigInteger(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pipelineId"));
            pipeline = application.getEntityManager().find(Pipeline.class, pipelineId);
        }
    }
    
    /**
     * Get the output.
     * 
     * @return the output.
     */
    public String getOutput() {
        return output;
    }

    /**
     * Get the pipeline.
     *
     * @return the pipeline.
     */
    public Pipeline getPipeline() {
        return pipeline;
    }
}
