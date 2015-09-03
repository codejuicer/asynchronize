package net.cristcost.asynchronize.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
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

  private MethodSpec createAsyncMethod(ExecutableElement methodElement) {
    MethodSpec.Builder method =
        MethodSpec.methodBuilder(methodElement.getSimpleName().toString());
    method.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);

    for (VariableElement variableElement : methodElement.getParameters()) {

      method.addParameter(TypeName.get(variableElement.asType()),
          variableElement.getSimpleName().toString());
    }

    TypeName returnType = TypeName.get(methodElement.getReturnType());
    if (returnType.isPrimitive() || returnType == TypeName.VOID) {
      returnType = returnType.box();
    }
    method.addParameter(ParameterizedTypeName.get(ClassName.get(AsyncCallback.class),
        returnType), "callback");

    // method.returns(TypeName.get(superClassName.asType()));
    return method.build();
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

    try {

      String asyncClassSimpleName = asynchronizeElement.getSimpleName().toString() + SUFFIX;

      String packageName = extractPackageNameString(asynchronizeElement);
      Builder interfaceBuilder = TypeSpec.interfaceBuilder(asyncClassSimpleName);

      // warn if input element is not public
      if (!asynchronizeElement.getModifiers().contains(Modifier.PUBLIC)) {
        processingEnv.getMessager().printMessage(Kind.MANDATORY_WARNING,
            "Warning: @Asynchronize annotated interface should be public",
            asynchronizeElement);
      }
      interfaceBuilder.addModifiers(Modifier.PUBLIC); // async will be public
                                                      // anyway

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
        interfaceBuilder.addMethod(createAsyncMethod(methodElement));
      }

      // Write file
      JavaFile.builder(packageName, interfaceBuilder.build()).build().writeTo(
          processingEnv.getFiler());

      // JavaWriter jw = new JavaWriter(writer);
      //
      // PackageElement pkg =
      // processingEnv.getElementUtils().getPackageOf(processedTypeElement);
      // if (!pkg.isUnnamed()) {
      // jw.emitPackage(pkg.getQualifiedName().toString());
      // jw.emitEmptyLine();
      // } else {
      // jw.emitPackage("");
      // }
      //
      // jw.emitAnnotation(AsyncOf.class,
      // processedTypeElement.getQualifiedName().toString() + ".class");
      //
      // jw.beginType(classFileName, "interface", EnumSet.of(Modifier.PUBLIC));
      // jw.emitEmptyLine();
      //
      // for (ExecutableElement methodElement : methodsToAsyncronize) {
      //
      // // String functionName = methodElement.getSimpleName().toString();
      // // writer.append("// " + functionName + System.lineSeparator());
      // // writer.append(System.lineSeparator());
      //
      // String methodName = methodElement.getSimpleName().toString();
      //
      // jw.beginMethod("void", methodName, EnumSet.of(Modifier.PUBLIC));
      // jw.endMethod();
      // }
      //
      // jw.endType();
      //
      // jw.close();

      // writer.append("/* todo generation */");
      // writer.append(System.lineSeparator());
      //
      // for (ExecutableElement methodElement : methodsToAsyncronize) {
      // String functionName = methodElement.getSimpleName().toString();
      // writer.append("// " + functionName + System.lineSeparator());
      // writer.append(System.lineSeparator());
      // }
      //
      // writer.append(System.lineSeparator());
      // writer.flush();
      // writer.close();
    } catch (IOException e) {
      processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
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
