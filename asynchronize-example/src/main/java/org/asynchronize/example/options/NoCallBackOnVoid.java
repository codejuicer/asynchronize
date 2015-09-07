package org.asynchronize.example.options;

import org.asynchronize.annotation.Asynchronize;

@Asynchronize(fireAndForget = true)
public interface NoCallBackOnVoid {

  void aMethodThatReturnsNothing();

  String aMethodThatReturnsSomething();
}
