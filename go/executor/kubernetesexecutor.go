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
	"bufio"
	"fmt"
	"io/ioutil"
	"os"
	"strings"
	"text/template"
	"time"

	"github.com/gobuffalo/packr"
)

//
// KubernetesExecutor - a Kubernetes executor
//
type KubernetesExecutor struct {
	Arguments []string
	Command   string
	Image     string
	JobName   string
	Output    string
}

//
// Execute - perform the execution
//
func (executor *KubernetesExecutor) Execute(arguments []string) {
	for i, argument := range arguments {
		if argument == "--arguments" {
			if strings.HasPrefix(arguments[i+1], "\"") ||
				strings.HasPrefix(arguments[i+1], "'") {
				arguments[i+1] = arguments[i+1][1:]
				arguments[i+1] = arguments[i+1][:len(arguments[i+1])-1]
			}
			executor.Arguments = strings.Split(arguments[i+1], " ")
		}
		if argument == "--image" {
			executor.Image = arguments[i+1]
		}
	}

	// Set the image if not set
	if executor.Image == "" {
		executor.Image = "manorrock/debian"
	}

	// Set the job name if not set
	if executor.JobName == "" {
		executor.JobName = "siamese-" + fmt.Sprint(time.Now().UnixNano())
	}

	// Set the command
	if executor.Command == "" {
		if len(executor.Arguments) > 0 {
			executor.Command = "\"" + executor.Arguments[0] + "\""
			for _, argument := range executor.Arguments[1:] {
				executor.Command = executor.Command + ",\"" + argument + "\""
			}
		} else {
			executor.Command = "\"\""
		}
	}

	// Get the YAML template we use for the job definition
	box := packr.NewBox("./templates")
	templateString, error := box.FindString("job.yaml")
	if error != nil {
		executor.Output = fmt.Sprintf("%s", error)
	} else {
		executor.Output = fmt.Sprintf("%s", templateString)
	}

	// Create the template
	template, error := template.New("template").Parse(templateString)
	if error != nil {
		executor.Output = fmt.Sprintf("%s", error)
	}

	// Create the YAML file needed for the job definition
	templateFile, error := ioutil.TempFile("", "siamese-template-file-")
	if error != nil {
		executor.Output = fmt.Sprintf("%s", error)
	}
	defer os.Remove(templateFile.Name())
	writer := bufio.NewWriter(templateFile)
	error = template.Execute(writer, executor)
	writer.Flush()
	if error != nil {
		templateFile.Close()
		executor.Output = fmt.Sprintf("%s", error)
	}
	if error := templateFile.Close(); error != nil {
		executor.Output = fmt.Sprintf("%s", error)
	}

	// Use kubectl apply -f filename to provision the job
	localExecutor := LocalExecutor{}
	localExecutor.Execute([]string{
		"--arguments",
		"kubectl apply -f" + templateFile.Name(),
	})
	executor.Output = localExecutor.Output

	// Wait for job to be ready.
	localExecutor = LocalExecutor{}
	localExecutor.Execute([]string{
		"--arguments",
		"kubectl wait --for=condition=complete --timeout=300s jobs/" + executor.JobName,
	})
	executor.Output = localExecutor.Output

	// Use kubectl logs to get the job output
	localExecutor = LocalExecutor{}
	localExecutor.Execute([]string{
		"--arguments",
		"kubectl logs job/" + executor.JobName,
	})
	executor.Output = localExecutor.Output

	// Use kubectl delete -f filename to remove the job
	localExecutor = LocalExecutor{}
	localExecutor.Execute([]string{
		"--arguments",
		"kubectl delete -f" + templateFile.Name(),
	})
}
