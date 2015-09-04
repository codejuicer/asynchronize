/*
 * Copyright 2015, Cristiano Costantini
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.cristcost.asynchronize.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static javax.lang.model.element.Modifier.*;

/**
 * Builds Method Spec with asynchronous version of methods
 */
class AsynchronousMethodBuilder {

  /**
   * Creates the MetodSpec of Async method from the {@link ExecutableElement} of
   * the original method
   */
  static MethodSpec createAsyncMethod(ExecutableElement syncMethodElement,
      boolean fireAndForget, TypeName asyncReturnType, ClassName callbackType) {

    MethodSpec.Builder asyncMethodSpec =
        initializeMethodSpec(syncMethodElement, asyncReturnType);

    for (VariableElement arg : syncMethodElement.getParameters()) {
      addParameterToMethodSpec(asyncMethodSpec, arg.asType(), arg.getSimpleName().toString());
    }

    addCallbackParameterToMethodSpec(asyncMethodSpec, syncMethodElement, fireAndForget,
        callbackType);

    return asyncMethodSpec.build();
  }

  private static void addCallbackParameterToMethodSpec(MethodSpec.Builder asyncMethodSpec,
      ExecutableElement syncMethodElement, boolean fireAndForget, ClassName callbackType) {

    // add callback parameter, unless it is void and using fire and forget
    // option
    TypeName syncReturnType = TypeName.get(syncMethodElement.getReturnType());
    boolean returnIsVoid = syncReturnType.equals(TypeName.VOID);
    if (!fireAndForget || !returnIsVoid) {
      if (syncReturnType.isPrimitive() || returnIsVoid) {
        syncReturnType = syncReturnType.box();
      }
      asyncMethodSpec.addParameter(ParameterizedTypeName.get(callbackType, syncReturnType),
          "callback");
    }
  }

  private static void addParameterToMethodSpec(MethodSpec.Builder asyncMethodSpec,
      TypeMirror methodTypeMirror,
      String methodName) {
    asyncMethodSpec.addParameter(TypeName.get(methodTypeMirror), methodName);
  }

  private static MethodSpec.Builder initializeMethodSpec(ExecutableElement syncMethodElement,
      TypeName asyncReturnType) {
    MethodSpec.Builder asyncMethodSpec =
        MethodSpec.methodBuilder(syncMethodElement.getSimpleName().toString()).addModifiers(
            PUBLIC, ABSTRACT);
    if (asyncReturnType != null) {
      asyncMethodSpec.returns(asyncReturnType);
    }
    return asyncMethodSpec;
  }

  private AsynchronousMethodBuilder() {
  }

}
