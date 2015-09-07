package org.codejuicer.asynchronize.example.base;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize
public interface MyStringService {

  public void emptyMethod();

  public void simpleParameter(String simpleParameterParam);

  public String simpleReturn(String simpleReturnParam);

  public String simpleParamReturn(String simpleParamReturnParam);

  public void multipleParam(String multipleParam1, String multipleParam2);

  public void varargs(String... varargsParam);
}
