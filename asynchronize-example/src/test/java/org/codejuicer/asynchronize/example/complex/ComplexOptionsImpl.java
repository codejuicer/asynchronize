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

import java.util.List;
import java.util.Map;

import javax.security.auth.callback.Callback;

public class ComplexOptionsImpl implements ComplexOptionsAsync {

  @Override
  public Object simpleParameter(Map<Integer, String> simpleParameterParam) {
    return null;
  }

  @Override
  public Object simpleReturn(Map<Integer, String> simpleReturnParam,
      Callback<Map<Integer, String>> callback) {
    return null;
  }

  @Override
  public Object simpleParamReturn(Map<Integer, String> simpleParamReturnParam,
      Callback<Map<Integer, String>> callback) {
    return null;
  }

  @Override
  public Object multipleParam(Map<Integer, String> multipleParam1,
      Map<Integer, String> multipleParam2) {
    return null;
  }

  @Override
  public Object simpleParameter(Integer simpleParameterParam) {
    return null;
  }

  @Override
  public Object simpleReturn(Integer simpleReturnParam, Callback<Integer> callback) {
    return null;
  }

  @Override
  public Object simpleParamReturn(Integer simpleParamReturnParam, Callback<Integer> callback) {
    return null;
  }

  @Override
  public Object multipleParam(Integer multipleParam1, Integer multipleParam2) {
    return null;
  }

  @Override
  public Object varargs(Integer[] varargsParam) {
    return null;
  }

  @Override
  public Object simpleParameter(int simpleParameterParam) {
    return null;
  }

  @Override
  public Object simpleReturn(int simpleReturnParam, Callback<Integer> callback) {
    return null;
  }

  @Override
  public Object simpleParamReturn(int simpleParamReturnParam, Callback<Integer> callback) {
    return null;
  }

  @Override
  public Object multipleParam(int multipleParam1, int multipleParam2) {
    return null;
  }

  @Override
  public Object varargs(int[] varargsParam) {
    return null;
  }

  @Override
  public Object simpleParameter(List<String> simpleParameterParam) {
    return null;
  }

  @Override
  public Object simpleReturn(List<String> simpleReturnParam, Callback<List<String>> callback) {
    return null;
  }

  @Override
  public Object simpleParamReturn(List<String> simpleParamReturnParam,
      Callback<List<String>> callback) {
    return null;
  }

  @Override
  public Object multipleParam(List<String> multipleParam1, List<String> multipleParam2) {
    return null;
  }

  @Override
  public Object emptyMethod() {
    return null;
  }

  @Override
  public Object simpleParameter(String simpleParameterParam) {
    return null;
  }

  @Override
  public Object simpleReturn(String simpleReturnParam, Callback<String> callback) {
    return null;
  }

  @Override
  public Object simpleParamReturn(String simpleParamReturnParam, Callback<String> callback) {
    return null;
  }

  @Override
  public Object multipleParam(String multipleParam1, String multipleParam2) {
    return null;
  }

  @Override
  public Object varargs(String[] varargsParam) {
    return null;
  }

  @Override
  public Object goToNorth() {
    return null;
  }

  @Override
  public Object goToWest() {
    return null;
  }

  @Override
  public Object goToEst() {
    return null;
  }

  @Override
  public Object goToSouth() {
    return null;
  }

}