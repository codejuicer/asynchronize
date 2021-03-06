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
package org.codejuicer.asynchronize.example.complex;

import org.codejuicer.asynchronize.annotation.Asynchronize;
import org.codejuicer.asynchronize.example.notmodifiable.ThirdPartyCompassService;

import java.util.List;
import java.util.Map;

@Asynchronize(fireAndForget = true, origin = true, callback = ComplexOptions.class,
    returnType = Object.class)
public interface ComplexOptions extends ThirdPartyCompassService {

  public void simpleParameter(Map<Integer, String> simpleParameterParam);

  public Map<Integer, String> simpleReturn(Map<Integer, String> simpleReturnParam);

  public Map<Integer, String> simpleParamReturn(Map<Integer, String> simpleParamReturnParam);

  public void multipleParam(Map<Integer, String> multipleParam1,
      Map<Integer, String> multipleParam2);

  public void simpleParameter(Integer simpleParameterParam);

  public Integer simpleReturn(Integer simpleReturnParam);

  public Integer simpleParamReturn(Integer simpleParamReturnParam);

  public void multipleParam(Integer multipleParam1, Integer multipleParam2);

  public void varargs(Integer... varargsParam);

  public void simpleParameter(int simpleParameterParam);

  public int simpleReturn(int simpleReturnParam);

  public int simpleParamReturn(int simpleParamReturnParam);

  public void multipleParam(int multipleParam1, int multipleParam2);

  public void varargs(int... varargsParam);

  public void simpleParameter(List<String> simpleParameterParam);

  public List<String> simpleReturn(List<String> simpleReturnParam);

  public List<String> simpleParamReturn(List<String> simpleParamReturnParam);

  public void multipleParam(List<String> multipleParam1, List<String> multipleParam2);

  public void emptyMethod();

  public void simpleParameter(String simpleParameterParam);

  public String simpleReturn(String simpleReturnParam);

  public String simpleParamReturn(String simpleParamReturnParam);

  public void multipleParam(String multipleParam1, String multipleParam2);

  public void varargs(String... varargsParam);
}
