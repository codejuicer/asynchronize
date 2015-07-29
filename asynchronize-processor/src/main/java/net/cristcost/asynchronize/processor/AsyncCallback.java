package net.cristcost.asynchronize.processor;

public interface AsyncCallback<T> {

  void onFailure(Throwable caught);

  void onSuccess(T result);
}