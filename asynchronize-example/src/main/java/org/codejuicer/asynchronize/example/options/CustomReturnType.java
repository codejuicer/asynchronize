package org.codejuicer.asynchronize.example.options;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize(returnType = boolean.class)
public interface CustomReturnType {

  void sampleVoidMethod();

  String sampleStringMethod(String str);

}
