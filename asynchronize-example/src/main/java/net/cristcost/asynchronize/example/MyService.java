package net.cristcost.asynchronize.example;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize
public interface MyService {

  public void emptyMethod();

  public void simpleParameter(String string);

  public String simpleReturn(String string);

  public String simpleParamReturn(String string);

  public void multipleParam(String string1, String string2);

  public void varargs(String... string);
}
