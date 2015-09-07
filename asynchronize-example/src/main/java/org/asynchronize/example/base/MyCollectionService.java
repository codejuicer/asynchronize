package org.asynchronize.example.base;

import org.asynchronize.annotation.Asynchronize;

import java.util.Map;

@Asynchronize
public interface MyCollectionService {

  public void simpleParameter(Map<Integer, String> simpleParameterParam);

  public Map<Integer, String> simpleReturn(Map<Integer, String> simpleReturnParam);

  public Map<Integer, String> simpleParamReturn(Map<Integer, String> simpleParamReturnParam);

  public void multipleParam(Map<Integer, String> multipleParam1,
      Map<Integer, String> multipleParam2);

}
