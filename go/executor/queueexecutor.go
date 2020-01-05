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
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

//
// QueueExecutor - a queue executor
//
type QueueExecutor struct {
	Action    string
	Arguments []string
	Output    string
	URL       string
}

//
// Execute - perform the execution
//
func (executor *QueueExecutor) Execute(arguments []string) {
	for i, argument := range arguments {
		if argument == "--action" {
			executor.Action = arguments[i+1]
		}
		if argument == "--arguments" {
			executor.Arguments = strings.Split(arguments[i+1], " ")
		}
		if argument == "--url" {
			executor.URL = arguments[i+1]
		}
	}
	if executor.Action == "list" {
		response, error := http.Get(executor.URL)
		if error != nil {
			executor.Output = fmt.Sprintf("%s", error)
		} else {
			body, _ := ioutil.ReadAll(response.Body)
			executor.Output = string(body)
		}
	}
	if executor.Action == "submit" {
		execution := &QueueExecution{}
		json, _ := json.Marshal(execution)
		response, error := http.Post(executor.URL, "application/json", bytes.NewBuffer(json))
		if error != nil {
			executor.Output = fmt.Sprintf("%s", error)
		} else {
			body, _ := ioutil.ReadAll(response.Body)
			executor.Output = string(body)
		}
	}
	if executor.Action == "poll" {
		for {
			response, error := http.Get(executor.URL)
			if error != nil {
				fmt.Printf("%s", error)
			} else {
				var executions []QueueExecution
				body, _ := ioutil.ReadAll(response.Body)
				json.Unmarshal(body, &executions)
				fmt.Println("Read all executions")
			}
			// 0. For each id in the list.
			// 1. Get the execution associated with the id.
			// 2. If the execution is "WAITING" then, else continue on in the list.
			// 3. claim it by setting status to "CLAIMING" and updated date
			// 4. wait for 30 seconds and if status is "CLAIMING" with our updated date
			//    then set status to "IN PROGRESS"
			// 5. execute the execution
			// 6. set the status to "SUCCESS" or "FAILED" depending on the result of the
			//    execution and set the output field to contain the output and set the
			//    updated date.
			time.Sleep(5 * time.Second)
		}
	}
}
