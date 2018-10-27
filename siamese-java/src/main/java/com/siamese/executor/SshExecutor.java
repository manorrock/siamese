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
package com.siamese.executor;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The SSH executor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class SshExecutor extends BaseExecutor {
    
    /**
     * Stores the hostname.
     */
    private String hostname;
    
    /**
     * Stores the password.
     */
    private String password;
    
    /**
     * Stores the username.
     */
    private String username;

    /**
     * Execute.
     *
     * @param arguments the arguments.
     * @return the output.
     */
    @Override
    public String execute(String[] arguments) {
        String output;
        StringBuilder builder = new StringBuilder();
        JSch jsch = new JSch();
        try {
            Session session = jsch.getSession(username, hostname, 22);
            session.setPassword(password);
            Properties sessionConfig = new Properties();
            sessionConfig.put("StrictHostKeyChecking", "no");
            sessionConfig.put("PreferredAuthentications", "password");
            session.setConfig(sessionConfig);
            session.connect();
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            String command = String.join(" ", arguments);
            channel.setCommand(command);
            channel.setInputStream(null);
            InputStream in = channel.getInputStream();
            channel.connect();
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
        return output;
    }
    
    /**
     * Set the hostname.
     * 
     * @param hostname the hostname.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
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
