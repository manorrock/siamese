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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The Kubernetes executor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class KubernetesExecutor implements Executor {

    /**
     * Stores our arguments.
     */
    private List<String> arguments;
    
    /**
     * Stores the command.
     */
    private String command;
    
    /**
     * Stores our image.
     */
    private String image;
    
    /**
     * Stores the job name.
     */
    private String jobName;

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
                this.arguments = Arrays.asList(arguments.get(i + 1).split(" "));
            }
            if (arguments.get(i).equals("--image")) {
                image = arguments.get(i + 1);
            }
        }
        if (image == null) {
            image = "manorrock/debian";
        }
        if (jobName == null || jobName.trim().equals("")) {
            jobName = "siamese-" + System.currentTimeMillis();
        }
        if (command == null || command.trim().equals("")) {
            if (this.arguments.size() > 0) {
                Iterator<String> iterator = this.arguments.iterator();
                command = "";
                while(iterator.hasNext()) {
                    command += "\"" + iterator.next() + "\"";
                    if (iterator.hasNext()) {
                        command += ",";
                    }
                }
            } else {
                command = "\"\"";
            }
        }

        ResourceBundle bundle = ResourceBundle.getBundle(KubernetesExecutor.class.getName());
        String jobDefinition = bundle.getString("jobDefinition");
        String jobString = MessageFormat.format(jobDefinition, jobName, image, command);

        System.out.println(jobString);

        // write job definition to a file
        // execute the job use the job definition
        // wait for the job to complete
        // get the logs for the job
        
        String filename = "filename";
        LocalExecutor localExecutor = new LocalExecutor();
        ArrayList<String> localArguments = new ArrayList<>();
        localArguments.add("--arguments");
        localArguments.add("kubectl delete -f " + filename);
        localExecutor.execute(localArguments);
        
        return result.toString();
    }
}
