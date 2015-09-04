package net.cristcost.asynchronize.example.external;

import com.google.common.util.concurrent.Service;

import net.cristcost.asynchronize.processor.Asynchronize;

import java.util.concurrent.Executor;

@Asynchronize
public interface ExternalWrapper extends Executor, Service {

  String sampleMethod(String inputa);
}
