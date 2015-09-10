/*
 * Copyright 2015, Cristiano Costantini
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.codejuicer.asynchronize.example.external;

import com.google.common.util.concurrent.Service;

import org.codejuicer.asynchronize.annotation.Asynchronize;

import java.util.concurrent.Executor;

@Asynchronize
public interface ExternalWrapper extends Executor, Service {

  String sampleMethod(String inputa);
}
