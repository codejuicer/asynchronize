package org.asynchronize.example.composed;

import org.asynchronize.processor.Asynchronize;

@Asynchronize
public interface YinService extends CoreService {

  void yinMethod();
}
