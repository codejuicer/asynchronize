package org.codejuicer.asynchronize.example.composed;

import org.codejuicer.asynchronize.annotation.Asynchronize;

@Asynchronize

public interface YangService extends CoreService {

  void yangMethod();
}
