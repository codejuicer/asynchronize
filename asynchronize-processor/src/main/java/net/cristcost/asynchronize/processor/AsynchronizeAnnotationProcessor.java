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

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

/**
 * This Processor generates asynchronous version of interfaces annotated with
 * {@link Asynchronize} annotation.
 */
@SupportedAnnotationTypes({
    "net.cristcost.asynchronize.processor.Asynchronize"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AsynchronizeAnnotationProcessor extends AbstractProcessor {

  public class Environment {

    public Elements elementUtils() {
      return processingEnv.getElementUtils();
    }

    public Messager messager() {
      return processingEnv.getMessager();
    }

    public Types typeUtils() {
      return processingEnv.getTypeUtils();
    }
  }

  private class Options {

    boolean addOriginAnnotation;
    TypeMirror callback;
    boolean fireAndForget;
    TypeMirror returnType;
  }

  /** Suffix used for the name of the generated interface. */
  private static final String SUFFIX = "Async";

  private static void addAsyncOfAnnotation(TypeElement inputInterfaceElement,
      Builder outputInterfaceBuilder) {
    CodeBlock codeBlock =
        CodeBlock.builder().add("$T.class", inputInterfaceElement.asType()).build();
    outputInterfaceBuilder.addAnnotation(
        AnnotationSpec.builder(AsyncOf.class).addMember("value", codeBlock).build());
  }

  private static List<ExecutableElement> extractAllSuperMethods(
      Environment env,
      TypeElement extendedInterface) {

    List<ExecutableElement> methods =
        new ArrayList(ElementFilter.methodsIn(extendedInterface.getEnclosedElements()));

    for (TypeMirror typeMirror : extendedInterface.getInterfaces()) {
      methods.addAll(
          extractAllSuperMethods(env,
              (TypeElement) env.typeUtils().asElement(typeMirror)));
    }

    return methods;
  }

  private static AnnotationMirror getAnnotationMirror(TypeElement typeElement,
      Class<?> clazz) {
    String clazzName = clazz.getName();
    for (AnnotationMirror m : typeElement.getAnnotationMirrors()) {
      if (m.getAnnotationType().toString().equals(clazzName)) {
        return m;
      }
    }
    return null;
  }

  private static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror,
      String key) {
    for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
      if (entry.getKey().getSimpleName().toString().equals(key)) {
        return entry.getValue();
      }
    }
    return null;
  }

  private static boolean isAnnotatedWithAsynchornize(TypeElement extendedInterface) {
    Asynchronize annotation = extendedInterface.getAnnotation(Asynchronize.class);
    return annotation != null ? true : false;
  }

  private Environment env = new Environment();

  @Override
  public boolean process(final Set<? extends TypeElement> annotations,
      final RoundEnvironment roundEnv) {

    if (!roundEnv.processingOver()) {
      final Set<? extends Element> elementsAnnotatedWithAsynchronize =
          roundEnv.getElementsAnnotatedWith(Asynchronize.class);

      for (final TypeElement asynchronizeElement : ElementFilter.typesIn(
          elementsAnnotatedWithAsynchronize)) {
        processElementAnnotatedWithAsynchronize(asynchronizeElement);
      }
    }
    return true;
  }

  protected void processElementAnnotatedWithAsynchronize(TypeElement inputInterfaceElement) {

    final List<ExecutableElement> methodsToAsyncronize = new ArrayList<ExecutableElement>(
        ElementFilter.methodsIn(inputInterfaceElement.getEnclosedElements()));

    String asyncInterfaceName = inputInterfaceElement.getSimpleName().toString() + SUFFIX;
    String packageName = extractPackageNameString(inputInterfaceElement);

    Options generationOptions = extractGenerationOptions(inputInterfaceElement);

    Builder outputInterfaceBuilder =
        initializeInterfaceTypeSpec(inputInterfaceElement, asyncInterfaceName);

    ClassName callbackClassName =
        getNameOrGenerateNestedCallback(inputInterfaceElement, outputInterfaceBuilder,
            generationOptions);

    TypeName asyncReturnTypeName = generationOptions.returnType != null
        ? TypeName.get(generationOptions.returnType) : null;

    if (generationOptions.addOriginAnnotation) {
      addAsyncOfAnnotation(inputInterfaceElement, outputInterfaceBuilder);
    }

    // management of exented asynchronize interfaces
    for (TypeMirror extendedInterfaceMirror : inputInterfaceElement.getInterfaces()) {
      manageExtendedInterface(methodsToAsyncronize, outputInterfaceBuilder,
          extendedInterfaceMirror);
    }

    // management of each method
    for (ExecutableElement methodElement : methodsToAsyncronize) {
      MethodSpec asyncMethod =
          AsynchronousMethodBuilder.createAsyncMethod(methodElement,
              generationOptions.fireAndForget,
              asyncReturnTypeName, callbackClassName);
      outputInterfaceBuilder.addMethod(asyncMethod);
    }

    // Write file
    try {
      JavaFile.builder(packageName, outputInterfaceBuilder.build()).build().writeTo(
          processingEnv.getFiler());
    } catch (IOException e) {
      env.messager().printMessage(Kind.ERROR, e.getMessage());
    }
  }

  private Options extractGenerationOptions(TypeElement inputInterfaceElement) {
    Asynchronize asynchronizeAnnotation =
        inputInterfaceElement.getAnnotation(Asynchronize.class);

    Options generationOptions = new Options();
    generationOptions.fireAndForget = asynchronizeAnnotation.fireAndForget();
    generationOptions.addOriginAnnotation = asynchronizeAnnotation.origin();

    AnnotationMirror am = getAnnotationMirror(inputInterfaceElement, Asynchronize.class);
    generationOptions.callback = extractTypeMirrorFromAnnotation(am, "callback");
    generationOptions.returnType = extractTypeMirrorFromAnnotation(am, "returnType");

    return generationOptions;
  }

  private String extractPackageNameString(TypeElement asynchronizeElement) {
    PackageElement pkg = env.elementUtils().getPackageOf(asynchronizeElement);
    String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();
    return packageName;
  }

  private TypeMirror extractTypeMirrorFromAnnotation(AnnotationMirror am, String key) {
    AnnotationValue value = getAnnotationValue(am, key);
    return value != null ? (TypeMirror) value.getValue() : null;
  }

  private ClassName getNameOrGenerateNestedCallback(TypeElement inputInterfaceElement,
      Builder outputInterfaceBuilder, Options generationOptions) {

    if (generationOptions.callback == null) {
      return ClassName.get(AsyncCallback.class);
    }
    Element element = env.typeUtils().asElement(generationOptions.callback);
    ClassName callbackClassName;
    if (element.equals(inputInterfaceElement)) {

      Builder innerCallbackBuilder =
          TypeSpec.interfaceBuilder("Callback").addModifiers(Modifier.PUBLIC,
              Modifier.STATIC);
      TypeVariableName typeVariableName = TypeVariableName.get("R");
      innerCallbackBuilder.addTypeVariable(typeVariableName);
      innerCallbackBuilder.addMethod(
          MethodSpec.methodBuilder("onFailure").addModifiers(Modifier.PUBLIC,
              Modifier.ABSTRACT).addParameter(
                  Throwable.class, "caught").build());
      innerCallbackBuilder.addMethod(
          MethodSpec.methodBuilder("onSuccess").addModifiers(Modifier.PUBLIC,
              Modifier.ABSTRACT).addParameter(
                  typeVariableName, "result").build());
      outputInterfaceBuilder.addType(innerCallbackBuilder.build());

      callbackClassName = ClassName.bestGuess("Callback");
    } else {
      callbackClassName = ClassName.get((TypeElement) element);
    }
    return callbackClassName;
  }

  private Builder initializeInterfaceTypeSpec(TypeElement asynchronizeElement,
      String asyncClassSimpleName) {
    Builder interfaceBuilder = TypeSpec.interfaceBuilder(asyncClassSimpleName);

    // warn if input element is not public
    if (!asynchronizeElement.getModifiers().contains(Modifier.PUBLIC)) {
      env.messager().printMessage(Kind.MANDATORY_WARNING,
          "Warning: @Asynchronize annotated interface should be public",
          asynchronizeElement);
    }
    interfaceBuilder.addModifiers(Modifier.PUBLIC); // async will be public
                                                    // anyway
    return interfaceBuilder;
  }

  private void manageExtendedInterface(final List<ExecutableElement> methodsToAsyncronize,
      Builder outputInterfaceBuilder, TypeMirror extendedInterfaceMirror) {
    TypeElement extendedInterface =
        (TypeElement) env.typeUtils().asElement(extendedInterfaceMirror);

    if (isAnnotatedWithAsynchornize(extendedInterface)) {
      // TODO: the extended interface must be compatible, add a check here and
      // throw error
      processExtendedAsyncInterface(outputInterfaceBuilder, extendedInterface);
    } else {
      methodsToAsyncronize.addAll(
          extractAllSuperMethods(env, extendedInterface));
    }
  }

  private void processExtendedAsyncInterface(Builder interfaceBuilder,
      TypeElement extendedInterface) {
    TypeName extendedInterfaceName =
        ClassName.get(extractPackageNameString(extendedInterface),
            extendedInterface.getSimpleName().toString() + SUFFIX);

    interfaceBuilder.addSuperinterface(extendedInterfaceName);
  }

}
