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

package org.codejuicer.asynchronize.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generates Async version of interfaces annotate with this.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Asynchronize {

  /**
   * Class to be used for Callback parameter. Target class must accept a generic
   * parameter, which will be used with the original return type.
   */
  Class<?>callback() default AsyncCallback.class;

  /**
   * If true, the callback parameter is not generated in methods returning
   * void
   */
  boolean fireAndForget() default false;

  /**
   * If true, AsyncOf annotation is added to the generated interface.
   */
  boolean origin() default false;

  /**
   * Return type of the generated methods in async interface. Generated
   * asynchronous methods return void by default.
   */
  Class<?>returnType() default void.class;

}
