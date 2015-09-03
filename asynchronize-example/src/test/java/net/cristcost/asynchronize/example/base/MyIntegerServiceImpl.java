package net.cristcost.asynchronize.example.base;

import net.cristcost.asynchronize.example.base.MyIntegerServiceAsync;
import net.cristcost.asynchronize.processor.AsyncCallback;

public class MyIntegerServiceImpl implements MyIntegerServiceAsync {

  @Override
  public void simpleParameter(Integer simpleParameterParam, AsyncCallback<Void> callback) {

  }

  @Override
  public void simpleReturn(Integer simpleReturnParam, AsyncCallback<Integer> callback) {

  }

  @Override
  public void simpleParamReturn(Integer simpleParamReturnParam,
      AsyncCallback<Integer> callback) {

  }

  @Override
  public void multipleParam(Integer multipleParam1, Integer multipleParam2,
      AsyncCallback<Void> callback) {

  }

  @Override
  public void varargs(Integer[] varargsParam, AsyncCallback<Void> callback) {

  }

}
