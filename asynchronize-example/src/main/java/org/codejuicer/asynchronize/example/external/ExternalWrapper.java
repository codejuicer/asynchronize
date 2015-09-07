package org.codejuicer.asynchronize.example.external;

import com.google.common.util.concurrent.Service;

import org.codejuicer.asynchronize.annotation.Asynchronize;

import java.util.concurrent.Executor;

@Asynchronize
public interface ExternalWrapper extends Executor, Service {

  String sampleMethod(String inputa);
}
