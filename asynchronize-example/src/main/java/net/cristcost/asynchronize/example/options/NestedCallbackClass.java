package net.cristcost.asynchronize.example.options;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize(callback = NestedCallbackClass.class)
public interface NestedCallbackClass {

  String sampleStringMethod(String str);

}
