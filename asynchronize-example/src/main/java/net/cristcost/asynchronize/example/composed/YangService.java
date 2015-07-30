package net.cristcost.asynchronize.example.composed;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize

public interface YangService extends CoreService {

  void yangMethod();
}
