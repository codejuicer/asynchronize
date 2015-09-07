package org.asynchronize.example.composed;

import org.asynchronize.processor.Asynchronize;

@Asynchronize

public interface YangService extends CoreService {

  void yangMethod();
}
