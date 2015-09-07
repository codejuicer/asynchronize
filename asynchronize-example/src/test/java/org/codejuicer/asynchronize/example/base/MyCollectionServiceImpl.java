package org.codejuicer.asynchronize.example.base;

import org.codejuicer.asynchronize.annotation.AsyncCallback;

import java.util.Map;

public class MyCollectionServiceImpl implements MyCollectionServiceAsync {

  @Override
  public void simpleParameter(Map<Integer, String> simpleParameterParam,
      AsyncCallback<Void> callback) {
  }

  @Override
  public void simpleReturn(Map<Integer, String> simpleReturnParam,
      AsyncCallback<Map<Integer, String>> callback) {
  }

  @Override
  public void simpleParamReturn(Map<Integer, String> simpleParamReturnParam,
      AsyncCallback<Map<Integer, String>> callback) {
  }

  @Override
  public void multipleParam(Map<Integer, String> multipleParam1,
      Map<Integer, String> multipleParam2, AsyncCallback<Void> callback) {
  }
}
