package net.cristcost.asynchronize.example.options;

import com.google.common.util.concurrent.FutureCallback;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize(callback = FutureCallback.class)
public interface CustomCallbackClass {

  String sampleStringMethod(String str);

}
