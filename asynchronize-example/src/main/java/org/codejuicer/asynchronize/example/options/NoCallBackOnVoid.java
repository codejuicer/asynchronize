package org.codejuicer.asynchronize.example.options;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize(fireAndForget = true)
public interface NoCallBackOnVoid {

  void aMethodThatReturnsNothing();

  String aMethodThatReturnsSomething();
}
