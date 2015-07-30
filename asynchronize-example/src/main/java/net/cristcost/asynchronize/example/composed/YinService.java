package net.cristcost.asynchronize.example.composed;

import net.cristcost.asynchronize.processor.Asynchronize;

@Asynchronize
public interface YinService extends CoreService {

  void yinMethod();
}
