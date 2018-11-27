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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main entry point.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Main {

    /**
     * Execute docker.
     *
     * @param arguments the arguments.
     */
    private static void executeDocker(List<String> arguments) {
        DockerExecutor dockerExecutor = new DockerExecutor();
        dockerExecutor.setArguments(arguments.toArray(new String[]{}));
        dockerExecutor.execute();
    }
            
    /**
     * Execute local.
     *
     * @param arguments the arguments.
     */
    private static void executeLocal(List<String> arguments) {
        LocalExecutor localExecutor = new LocalExecutor();
        ArrayList<String> commandArguments = new ArrayList<>();
        for(int i=0; i<arguments.size(); i++) {
            if (arguments.get(i).equals("--arguments")) {
                commandArguments.addAll(Arrays.asList(arguments.get(i+1).split(" ")));
            }
            
        }
        localExecutor.setArguments(commandArguments.toArray(new String[]{}));
        localExecutor.execute();
    }

    /**
     * Main method.
     *
     * @param arguments the arguments.
     */
    public static void main(String[] arguments) {
        ArrayList<String> executeArguments = new ArrayList<>();
        String action = "";
        if (arguments.length > 0) {
            for (int i = 0; i < arguments.length; i++) {
                if (arguments[i].equals("local")) {
                    action = arguments[i];
                } else if (arguments[i].equals("docker")) {
                    action = arguments[i];
                } else {
                    executeArguments.add(arguments[i]);
                }
            }
        }
        switch (action) {
            case "docker":
                executeDocker(executeArguments);
                break;
            case "local":
                executeLocal(executeArguments);
                break;
        }
    }
}
