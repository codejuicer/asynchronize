package net.cristcost.asynchronize.example.options;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize(returnType = boolean.class)
public interface CustomReturnType {

  void sampleVoidMethod();

  String sampleStringMethod(String str);

}