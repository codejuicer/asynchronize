package net.cristcost.asynchronize.example.options;

import net.cristcost.asynchronize.processor.Asynchronize;

import java.util.List;

@Asynchronize(callback = NestedCallbackClass.class)
public interface NestedCallbackClass {

  String sampleStringMethod(String str);

  int sampleIntMethod(int str);

  List<String> sampleCollectionMethod(List<String> str);

}
