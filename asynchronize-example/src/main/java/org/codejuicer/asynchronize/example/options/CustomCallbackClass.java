package org.codejuicer.asynchronize.example.options;

import com.google.common.util.concurrent.FutureCallback;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize(callback = FutureCallback.class)
public interface CustomCallbackClass {

  String sampleStringMethod(String str);

}
