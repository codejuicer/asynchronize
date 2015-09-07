package org.asynchronize.example.options;

import com.google.common.util.concurrent.FutureCallback;

import org.asynchronize.annotation.Asynchronize;

@Asynchronize(callback = FutureCallback.class)
public interface CustomCallbackClass {

  String sampleStringMethod(String str);

}
