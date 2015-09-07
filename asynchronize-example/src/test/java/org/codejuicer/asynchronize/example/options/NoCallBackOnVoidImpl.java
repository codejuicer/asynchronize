package org.codejuicer.asynchronize.example.options;

import org.codejuicer.asynchronize.annotation.AsyncCallback;

public class NoCallBackOnVoidImpl implements NoCallBackOnVoidAsync {

  @Override
  public void aMethodThatReturnsNothing() {

  }

  @Override
  public void aMethodThatReturnsSomething(AsyncCallback<String> callback) {

  }

}
