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
package org.codejuicer.asynchronize.example.options;

import java.util.List;

import javax.security.auth.callback.Callback;

public class NestedCallbackClassImpl implements NestedCallbackClassAsync {

  @Override
  public void sampleStringMethod(String str, Callback<String> callback) {

  }

  @Override
  public void sampleIntMethod(int str, Callback<Integer> callback) {

  }

  @Override
  public void sampleCollectionMethod(List<String> str, Callback<List<String>> callback) {

  }

}
