package net.cristcost.asynchronize.example.options;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize(callback = MyAsyncCallback.class)
public interface CustomCallbackClass {

  String sampleStringMethod(String str);

}
