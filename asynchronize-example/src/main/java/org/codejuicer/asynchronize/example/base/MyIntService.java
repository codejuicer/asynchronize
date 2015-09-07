package org.codejuicer.asynchronize.example.base;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize
public interface MyIntService {

  public void simpleParameter(int simpleParameterParam);

  public int simpleReturn(int simpleReturnParam);

  public int simpleParamReturn(int simpleParamReturnParam);

  public void multipleParam(int multipleParam1, int multipleParam2);

  public void varargs(int... varargsParam);
}
