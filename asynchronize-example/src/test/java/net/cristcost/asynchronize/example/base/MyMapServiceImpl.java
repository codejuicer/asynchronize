package net.cristcost.asynchronize.example.base;

import net.cristcost.asynchronize.example.base.MyMapServiceAsync;
import net.cristcost.asynchronize.processor.AsyncCallback;

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
