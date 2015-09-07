package org.asynchronize.example.options;

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
