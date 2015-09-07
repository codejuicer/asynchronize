package org.asynchronize.example.composed;

import org.asynchronize.annotation.Asynchronize;

@Asynchronize

public interface UnifiedService extends YangService, YinService {

}
