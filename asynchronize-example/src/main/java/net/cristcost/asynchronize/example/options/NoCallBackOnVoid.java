package net.cristcost.asynchronize.example.options;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize(fireAndForget = true)
public interface NoCallBackOnVoid {

  void aMethodThatReturnsNothing();

  String aMethodThatReturnsSomething();
}
