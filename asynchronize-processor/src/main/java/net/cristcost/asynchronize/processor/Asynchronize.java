package net.cristcost.asynchronize.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Asynchronize {

  boolean origin() default false;

  boolean fireAndForget() default false;

  Class<?>callback() default AsyncCallback.class;

  Class<?>returnType() default void.class;

}
