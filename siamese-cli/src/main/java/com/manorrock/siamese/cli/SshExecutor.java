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
package com.manorrock.siamese.cli;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The 'SSH' executor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class SshExecutor implements Executor {

    /**
     * Stores our arguments.
     */
    private List<String> arguments;

    /**
     * Stores our hostname.
     */
    private String hostname;

    /**
     * Stores our password.
     */
    private String password;

    /**
     * Stores our username.
     */
    private String username;

    /**
     * Execute the executor.
     *
     * @param executeArguments the arguments.
     * @return the output.
     */
    public String execute(List<String> executeArguments) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < executeArguments.size(); i++) {
            if (executeArguments.get(i).equals("--arguments")) {
                arguments = Arrays.asList(executeArguments.get(i + 1).split(" "));
            }
            if (executeArguments.get(i).equals("--hostname")) {
                hostname = executeArguments.get(i + 1);
            }
            if (executeArguments.get(i).equals("--password")) {
                password = executeArguments.get(i + 1);
            }
            if (executeArguments.get(i).equals("--username")) {
                username = executeArguments.get(i + 1);
            }
        }
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, hostname, 22);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(String.join(" ", arguments));
            channelExec.setInputStream(null);
            InputStream inputStream = channelExec.getInputStream();
            channelExec.connect();
            byte[] buffer = new byte[8192];
            while (true) {
                while (inputStream.available() > 0) {
                    int i = inputStream.read(buffer, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    result.append(new String(buffer, 0, i));
                }
                if (channelExec.isClosed()) {
                    if (inputStream.available() > 0) {
                        continue;
                    }
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
            channelExec.disconnect();
            session.disconnect();
        } catch (JSchException | IOException e) {
            StringWriter eWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(eWriter));
            result.append(eWriter.toString());
        }
        return result.toString();
    }
}
