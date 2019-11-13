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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * The 'local' executor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class LocalExecutor implements Executor {

    /**
     * Stores our process arguments.
     */
    private List<String> processArguments;
    
    /**
     * Stores the verbose flag.
     */
    private boolean verbose = false;

    /**
     * Stores the working directory.
     */
    private String workingDirectory;

    /**
     * Execute the executor.
     *
     * @param arguments the arguments.
     * @return the output.
     */
    @Override
    public String execute(List<String> arguments) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i).equals("--arguments")) {
                processArguments = Arrays.asList(arguments.get(i + 1).split(" "));
            }
            if (arguments.get(i).equals("--verbose")) {
                verbose = true;
            }
            if (arguments.get(i).equals("--workingDirectory")) {
                workingDirectory = arguments.get(i + 1);
            }
        }
        ProcessBuilder processBuilder = new ProcessBuilder(processArguments);
        if (workingDirectory != null) {
            processBuilder.directory(new File(workingDirectory));
        }
        try {
            if (verbose) {
                result.append("Executing - ").append(String.join(" ", processArguments)).append("\n");
            }
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                    if (line != null) {
                        result.append("\n");
                    }
                }
            }
            if (verbose) {
                result.append("Executed - ").append(String.join(" ", processArguments)).append("\n");
            }
        } catch (IOException ioe) {
            StringWriter ioeWriter = new StringWriter();
            ioe.printStackTrace(new PrintWriter(ioeWriter));
            result.append(ioeWriter.toString());
            if (verbose) {
                result.append("Error executing - ").append(String.join(" ", processArguments)).append("\n");
            }
        }
        return result.toString();
    }
}
