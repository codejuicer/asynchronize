package org.asynchronize.example.base;

import org.asynchronize.annotation.Asynchronize;

@Asynchronize
public interface MyIntegerService {

  public void simpleParameter(Integer simpleParameterParam);

  public Integer simpleReturn(Integer simpleReturnParam);

  public Integer simpleParamReturn(Integer simpleParamReturnParam);

  public void multipleParam(Integer multipleParam1, Integer multipleParam2);

  public void varargs(Integer... varargsParam);
}
