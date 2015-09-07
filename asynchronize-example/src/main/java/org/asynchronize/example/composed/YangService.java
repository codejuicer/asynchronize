package org.asynchronize.example.composed;

import org.asynchronize.annotation.Asynchronize;

@Asynchronize

public interface YangService extends CoreService {

  void yangMethod();
}
