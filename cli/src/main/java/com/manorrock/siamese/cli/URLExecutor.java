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
package com.manorrock.siamese.cli;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpClient.Version.HTTP_1_1;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.List;

/**
 * The 'URL' executor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class URLExecutor implements Executor {

    /**
     * Stores our arguments.
     */
    private String arguments;

    /**
     * Stores our URL.
     */
    private String url;
    
    /**
     * Constructor.
     */
    public URLExecutor() {
        arguments = "";
    }

    /**
     * Execute the executor.
     *
     * @param executeArguments the arguments.
     * @return the output.
     */
    @Override
    public String execute(List<String> executeArguments) {
        String result;
        for (int i = 0; i < executeArguments.size(); i++) {
            if (executeArguments.get(i).equals("--arguments")) {
                arguments = executeArguments.get(i + 1);
            }
            if (executeArguments.get(i).equals("--url")) {
                url = executeArguments.get(i + 1);
            }
        }
        try {
            HttpClient client = HttpClient
                    .newBuilder()
                    .version(HTTP_1_1)
                    .followRedirects(ALWAYS)
                    .connectTimeout(Duration.ofSeconds(20))
                    .build();
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .POST(BodyPublishers.ofString(arguments))
                    .build();
            result = client.send(request, BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            result = e.getMessage();
        }
        return result;
    }
}
