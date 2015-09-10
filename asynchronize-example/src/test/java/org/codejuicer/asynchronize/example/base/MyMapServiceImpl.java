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

import org.codejuicer.asynchronize.annotation.AsyncCallback;

import java.util.List;

public class MyMapServiceImpl implements MyMapServiceAsync {

  @Override
  public void simpleParameter(List<String> simpleParameterParam,
      AsyncCallback<Void> callback) {

  }

  @Override
  public void simpleReturn(List<String> simpleReturnParam,
      AsyncCallback<List<String>> callback) {

  }

  @Override
  public void simpleParamReturn(List<String> simpleParamReturnParam,
      AsyncCallback<List<String>> callback) {

  }

  @Override
  public void multipleParam(List<String> multipleParam1, List<String> multipleParam2,
      AsyncCallback<Void> callback) {

  }

}
