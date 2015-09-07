package org.asynchronize.example.options;

import org.asynchronize.annotation.Asynchronize;

import java.util.List;

@Asynchronize(callback = NestedCallbackClass.class)
public interface NestedCallbackClass {

  String sampleStringMethod(String str);

  int sampleIntMethod(int str);

  List<String> sampleCollectionMethod(List<String> str);

}
