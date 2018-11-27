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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * The Docker Java executor.
 *
 * <p>
 * Note this executor assumes you have the docker binary in your PATH.
 * </p>
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class DockerExecutor extends BaseExecutor implements Executor {

    /**
     * Stores the image.
     */
    private String image;

    /**
     * Execute.
     */
    @Override
    public void execute() {
        if (image == null || image.trim().equals("")) {
            image = "manorrock/debian";
        }
        Process process;
        ArrayList<String> dockerArguments = new ArrayList<>();
        dockerArguments.add("docker");
        dockerArguments.add("run");
        dockerArguments.add("--rm");
        dockerArguments.add("-i");
        dockerArguments.add(image);
        dockerArguments.addAll(Arrays.asList(arguments));
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            process = processBuilder.
                    command(dockerArguments).
                    redirectErrorStream(true).
                    start();
            try {
                process.waitFor(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException ie) {
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                output = reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException ioe) {
            output = ioe.getMessage();
        }
    }

    /**
     * Get the image.
     * 
     * @return the image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Set the image.
     *
     * @param image the image.
     */
    public void setImage(String image) {
        this.image = image;
    }
}
