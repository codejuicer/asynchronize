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
package org.codejuicer.asynchronize.example.base;

import org.codejuicer.asynchronize.annotation.Asynchronize;

import java.util.List;

@Asynchronize
public interface MyCollectionService {

  public void simpleParameter(List<String> simpleParameterParam);

  public List<String> simpleReturn(List<String> simpleReturnParam);

  public List<String> simpleParamReturn(List<String> simpleParamReturnParam);

  public void multipleParam(List<String> multipleParam1, List<String> multipleParam2);

}
