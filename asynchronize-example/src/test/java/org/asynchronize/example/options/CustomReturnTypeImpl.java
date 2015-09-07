package org.asynchronize.example.options;

import org.asynchronize.processor.AsyncCallback;

public class CustomReturnTypeImpl implements CustomReturnTypeAsync {

  @Override
  public boolean sampleVoidMethod(AsyncCallback<Void> callback) {
    return false;
  }

  @Override
  public boolean sampleStringMethod(String str, AsyncCallback<String> callback) {
    return false;
  }

}
