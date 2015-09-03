package net.cristcost.asynchronize.example.options;

public interface MyAsyncCallback<T> {

  void onFailure(Throwable caught);

  void onSuccess(T result);
}