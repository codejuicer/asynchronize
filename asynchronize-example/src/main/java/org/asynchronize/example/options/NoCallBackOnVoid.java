package org.asynchronize.example.options;

import org.asynchronize.processor.Asynchronize;

@Asynchronize(fireAndForget = true)
public interface NoCallBackOnVoid {

  void aMethodThatReturnsNothing();

  String aMethodThatReturnsSomething();
}
