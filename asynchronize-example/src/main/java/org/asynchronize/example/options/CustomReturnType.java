package org.asynchronize.example.options;

import org.asynchronize.processor.Asynchronize;

@Asynchronize(returnType = boolean.class)
public interface CustomReturnType {

  void sampleVoidMethod();

  String sampleStringMethod(String str);

}
