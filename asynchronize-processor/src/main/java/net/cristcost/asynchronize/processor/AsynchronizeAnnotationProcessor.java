package net.cristcost.asynchronize.processor;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({
    "net.cristcost.asynchronize.processor.Asynchronize"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AsynchronizeAnnotationProcessor extends AbstractProcessor {

  private static final String SUFFIX = "Async";

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

  private MethodSpec createAsyncMethod(ExecutableElement methodElement,
      boolean fireAndForget, TypeName asyncReturnType, ClassName callbackType) {
    MethodSpec.Builder method =
        MethodSpec.methodBuilder(methodElement.getSimpleName().toString());
    method.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

    method.returns(asyncReturnType);

    for (VariableElement variableElement : methodElement.getParameters()) {

      method.addParameter(TypeName.get(variableElement.asType()),
          variableElement.getSimpleName().toString());
    }

    TypeName returnType = TypeName.get(methodElement.getReturnType());
    if (returnType.isPrimitive() || returnType == TypeName.VOID) {
      returnType = returnType.box();
    }

    // if fire and forget don't add callback on void
    // System.out.println("---------------------");
    // System.out.println("---------------------");
    // System.out.println(methodElement.getSimpleName().toString());

    if (!fireAndForget || returnType != TypeName.VOID) {
      method.addParameter(ParameterizedTypeName.get(callbackType, returnType), "callback");
      // System.out.println("------ CALLBACK ---------------");
    } else {
      // System.out.println("------ FORGET ---------------");
    }
    // System.out.println("---------------------");
    // System.out.println("---------------------");

    return method.build();
  }

  private Builder createInterfaceBuilder(TypeElement asynchronizeElement,
      String asyncClassSimpleName) {
    Builder interfaceBuilder = TypeSpec.interfaceBuilder(asyncClassSimpleName);

    // warn if input element is not public
    if (!asynchronizeElement.getModifiers().contains(Modifier.PUBLIC)) {
      processingEnv.getMessager().printMessage(Kind.MANDATORY_WARNING,
          "Warning: @Asynchronize annotated interface should be public",
          asynchronizeElement);
    }
    interfaceBuilder.addModifiers(Modifier.PUBLIC); // async will be public
                                                    // anyway
    return interfaceBuilder;
  }

  private List<ExecutableElement> extractAllSuperMethods(
      TypeElement extendedInterface) {

    List<ExecutableElement> methods =
        new ArrayList(ElementFilter.methodsIn(extendedInterface.getEnclosedElements()));

    for (TypeMirror typeMirror : extendedInterface.getInterfaces()) {
      methods.addAll(
          extractAllSuperMethods(
              (TypeElement) processingEnv.getTypeUtils().asElement(typeMirror)));
    }

    return methods;
  }

  private String extractPackageNameString(TypeElement asynchronizeElement) {
    PackageElement pkg = processingEnv.getElementUtils().getPackageOf(asynchronizeElement);
    String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();
    return packageName;
  }

  private boolean isAnnotatedWithAsynchornize(TypeElement extendedInterface) {
    Asynchronize annotation = extendedInterface.getAnnotation(Asynchronize.class);
    return annotation != null ? true : false;
  }

  private void processElementAnnotatedWithAsynchronize(TypeElement asynchronizeElement) {

    final List<ExecutableElement> methodsToAsyncronize = new ArrayList<ExecutableElement>(
        ElementFilter.methodsIn(asynchronizeElement.getEnclosedElements()));

    Asynchronize generationOptions = asynchronizeElement.getAnnotation(Asynchronize.class);
    boolean fireAndForget = generationOptions.fireAndForget();

    try {

      String asyncClassSimpleName = asynchronizeElement.getSimpleName().toString() + SUFFIX;
      String packageName = extractPackageNameString(asynchronizeElement);

      Builder interfaceBuilder =
          createInterfaceBuilder(asynchronizeElement, asyncClassSimpleName);

      boolean addOriginAnnotation = generationOptions.origin();
      Element element = processingEnv.getTypeUtils().asElement(tmpCallback(generationOptions));
      ClassName callbackType;
      if (element.equals(asynchronizeElement)) {

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
        interfaceBuilder.addType(innerCallbackBuilder.build());

        callbackType = ClassName.bestGuess("Callback");
      } else {
        callbackType = ClassName.get((TypeElement) element);
      }

      TypeName asyncReturnType = TypeName.get(tmpReturnType(generationOptions));

      // add annotation to trace back to original interface via reflection
      if (addOriginAnnotation) {
        CodeBlock codeBlock =
            CodeBlock.builder().add("$T.class", asynchronizeElement.asType()).build();
        interfaceBuilder.addAnnotation(
            AnnotationSpec.builder(AsyncOf.class).addMember("value", codeBlock).build());
      }

      // management of exented asynchronize interfaces
      for (TypeMirror extendedInterfaceMirror : asynchronizeElement.getInterfaces()) {
        TypeElement extendedInterface =
            (TypeElement) processingEnv.getTypeUtils().asElement(extendedInterfaceMirror);

        if (isAnnotatedWithAsynchornize(extendedInterface)) {
          processExtendedAsyncInterface(interfaceBuilder, extendedInterface);
        } else {
          methodsToAsyncronize.addAll(
              extractAllSuperMethods(extendedInterface));
        }
      }

      // management of each method
      for (ExecutableElement methodElement : methodsToAsyncronize) {
        interfaceBuilder.addMethod(
            createAsyncMethod(methodElement, fireAndForget, asyncReturnType, callbackType));
      }

      // Write file
      JavaFile.builder(packageName, interfaceBuilder.build()).build().writeTo(
          processingEnv.getFiler());

    } catch (IOException e) {
      processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
    }
  }

  @Deprecated
  private TypeMirror tmpCallback(Asynchronize generationOptions) {
    try {
      Class<?> a = generationOptions.callback();
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
    throw new RuntimeException("This method is only valid in annotation processing");
  }

  @Deprecated
  private TypeMirror tmpReturnType(Asynchronize generationOptions) {
    try {
      Class<?> a = generationOptions.returnType();
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
    throw new RuntimeException("This method is only valid in annotation processing");
  }

  private void processExtendedAsyncInterface(Builder interfaceBuilder,
      TypeElement extendedInterface) {
    TypeName extendedInterfaceName =
        ClassName.get(extractPackageNameString(extendedInterface),
            extendedInterface.getSimpleName().toString() + SUFFIX);

    interfaceBuilder.addSuperinterface(extendedInterfaceName);
  }

}
