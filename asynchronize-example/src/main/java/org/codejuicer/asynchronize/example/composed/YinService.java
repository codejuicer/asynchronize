package org.codejuicer.asynchronize.example.composed;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize
public interface YinService extends CoreService {

  void yinMethod();
}
