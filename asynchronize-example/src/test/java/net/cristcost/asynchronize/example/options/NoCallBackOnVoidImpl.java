package net.cristcost.asynchronize.example.options;

import net.cristcost.asynchronize.processor.AsyncCallback;

public class NoCallBackOnVoidImpl implements NoCallBackOnVoidAsync {

  @Override
  public void aMethodThatReturnsNothing() {

  }

  @Override
  public void aMethodThatReturnsSomething(AsyncCallback<String> callback) {

  }

}
