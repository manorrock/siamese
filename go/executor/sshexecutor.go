//
// Copyright (c) 2002-2019 Manorrock.com. All Rights Reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
//   1. Redistributions of source code must retain the above copyright notice,
//      this list of conditions and the following disclaimer.
//   2. Redistributions in binary form must reproduce the above copyright notice,
//      this list of conditions and the following disclaimer in the documentation
//      and/or other materials provided with the distribution.
//   3. Neither the name of the copyright holder nor the names of its
//      contributors may be used to endorse or promote products derived from this
//      software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
//

//
// Package executor - the executor package
//
package executor

import (
	"bytes"
	"fmt"
	"strings"

	"golang.org/x/crypto/ssh"
)

//
// SSHExecutor - an SSH executor
//
type SSHExecutor struct {
	Arguments        []string
	Hostname         string
	Output           string
	Password         string
	Username         string
	WorkingDirectory string
}

//
// Execute - perform the execution
//
func (executor *SSHExecutor) Execute(arguments []string) {
	for i, argument := range arguments {
		if argument == "--arguments" {
			executor.Arguments = strings.Split(arguments[i+1], " ")
		}
		if argument == "--hostname" {
			executor.Hostname = arguments[i+1]
		}
		if argument == "--password" {
			executor.Password = arguments[i+1]
		}
		if argument == "--username" {
			executor.Username = arguments[i+1]
		}
	}
	config := &ssh.ClientConfig{
		User: executor.Username,
		Auth: []ssh.AuthMethod{
			ssh.Password(executor.Password),
		},
		HostKeyCallback: ssh.InsecureIgnoreHostKey(),
	}
	client, err := ssh.Dial("tcp", executor.Hostname+":22", config)
	if err != nil {
		executor.Output = fmt.Sprintf("Unable to create SSH connection: %s", err)
	}
	session, err := client.NewSession()
	if err != nil {
		executor.Output = fmt.Sprintf("Unable to create SSH session: %s", err)
	}
	defer session.Close()
	var b bytes.Buffer
	session.Stdout = &b
	var runArguments = strings.Join(executor.Arguments, " ")
	if err := session.Run(runArguments); err != nil {
		executor.Output = fmt.Sprintf("Unable to run SSH command: %s", err)
	}
	if err == nil {
		executor.Output = fmt.Sprintf("%s", b.String())
	}
}
