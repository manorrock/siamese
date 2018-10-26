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
package com.siamese.executor.java;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The local Java executor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class LocalJavaExecutor implements JavaExecutor {
    
    /**
     * Stores the timeout (in seconds).
     */
    private int timeout;

    /**
     * Execute.
     *
     * @param arguments the arguments.
     */
    @Override
    public void execute(String[] arguments) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            Process process = processBuilder.command(arguments).start();
            process.waitFor(timeout, TimeUnit.SECONDS);
        } catch (IOException ioe) {
            throw new RuntimeException("An I/O error occurred", ioe);
        } catch (InterruptedException ie) {
            throw new RuntimeException("Execution timed out", ie);
        }
    }

    /**
     * Get the timeout.
     * 
     * @return the timeout.
     */
    @Override
    public int getTimeout() {
        return timeout;
    }

    /**
     * Set the timeout.
     * 
     * @param timeout the timeout.
     */
    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
