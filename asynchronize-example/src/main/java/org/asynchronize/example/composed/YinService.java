package org.asynchronize.example.composed;

import org.asynchronize.annotation.Asynchronize;

@Asynchronize
public interface YinService extends CoreService {

  void yinMethod();
}
