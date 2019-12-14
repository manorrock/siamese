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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The JUnit tests for the DockerExecutor.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
@Ignore
public class DockerExecutorTest {

    /**
     * Test execute method.
     */
    @Test
    public void testExecute() {
        List<String> arguments = new ArrayList<>();
        arguments.add("--arguments");
        arguments.add("echo 1234");
        DockerExecutor executor = new DockerExecutor();
        String result = executor.execute(arguments);
        assertEquals("1234", result);
    }

    /**
     * Test execute method.
     */
    @Test
    public void testExecute2() {
        List<String> arguments = new ArrayList<>();
        arguments.add("--image");
        arguments.add("manorrock/debian");
        arguments.add("--arguments");
        arguments.add("echo 1234");
        DockerExecutor executor = new DockerExecutor();
        String result = executor.execute(arguments);
        assertEquals("1234", result);
    }
    

    /**
     * Test execute method.
     */
    @Test
    public void testExecute3() {
        List<String> arguments = new ArrayList<>();
        arguments.add("--workingDirectory");
        arguments.add(new File("").getAbsolutePath());
        arguments.add("--arguments");
        arguments.add("echo 1234");
        DockerExecutor executor = new DockerExecutor();
        String result = executor.execute(arguments);
        assertEquals("1234", result);
    }
}
