package org.asynchronize.example.base;

import org.asynchronize.annotation.AsyncCallback;

public class MyStringServiceImpl implements MyStringServiceAsync {

  @Override
  public void emptyMethod(AsyncCallback<Void> callback) {

  }

  @Override
  public void simpleParameter(String simpleParameterParam, AsyncCallback<Void> callback) {

  }

  @Override
  public void simpleReturn(String simpleReturnParam, AsyncCallback<String> callback) {

  }

  @Override
  public void simpleParamReturn(String simpleParamReturnParam,
      AsyncCallback<String> callback) {

  }

  @Override
  public void multipleParam(String multipleParam1, String multipleParam2,
      AsyncCallback<Void> callback) {
    // TODO Auto-generated method stub

  }

  @Override
  public void varargs(String[] varargsParam, AsyncCallback<Void> callback) {

  }
}
