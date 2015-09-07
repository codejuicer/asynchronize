package org.asynchronize.example.options;

import org.asynchronize.annotation.AsyncCallback;

public class NoCallBackOnVoidImpl implements NoCallBackOnVoidAsync {

  @Override
  public void aMethodThatReturnsNothing() {

  }

  @Override
  public void aMethodThatReturnsSomething(AsyncCallback<String> callback) {

  }

}
