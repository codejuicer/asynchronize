package org.asynchronize.example.base;

import org.asynchronize.annotation.Asynchronize;

import java.util.List;

@Asynchronize
public interface MyMapService {

  public void simpleParameter(List<String> simpleParameterParam);

  public List<String> simpleReturn(List<String> simpleReturnParam);

  public List<String> simpleParamReturn(List<String> simpleParamReturnParam);

  public void multipleParam(List<String> multipleParam1, List<String> multipleParam2);

}
