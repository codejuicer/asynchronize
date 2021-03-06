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

import java.util.Map;

@Asynchronize
public interface MyMapService {

  public void simpleParameter(Map<Integer, String> simpleParameterParam);

  public Map<Integer, String> simpleReturn(Map<Integer, String> simpleReturnParam);

  public Map<Integer, String> simpleParamReturn(Map<Integer, String> simpleParamReturnParam);

  public void multipleParam(Map<Integer, String> multipleParam1,
      Map<Integer, String> multipleParam2);

}
