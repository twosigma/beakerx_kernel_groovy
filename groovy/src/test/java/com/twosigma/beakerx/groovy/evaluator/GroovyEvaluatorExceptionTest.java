/*
 *  Copyright 2014 TWO SIGMA OPEN SOURCE, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.twosigma.beakerx.groovy.evaluator;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.MissingPropertyException;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.runtime.typehandling.GroovyCastException;
import org.junit.jupiter.api.Test;

public class GroovyEvaluatorExceptionTest extends GroovyEvaluatorTest {

  @Test
  public void parseNotExistingClassScript_throwMultipleCompilationErrorsException() {
    org.junit.jupiter.api.Assertions.assertThrows(MultipleCompilationErrorsException.class,
        () -> parseClassFromScript("def plot = new NotExistPlot()"));
  }

  @Test
  public void parseNotExistingPropertyScript_throwMissingPropertyException() {
    org.junit.jupiter.api.Assertions.assertThrows(MissingPropertyException.class,
        () -> parseClassFromScript("def plot = new Plot() \n " + "plot << line"));
  }

  @Test
  public void parseNullObjectScript_throwNullPointerException() {
    org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class,
        () -> parseClassFromScript("def plot = new Plot() \n" + "Line line = null \n " + "plot << line"));
  }

  @Test
  public void parseCastWrongClassScript_throwGroovyCastException() {
    org.junit.jupiter.api.Assertions.assertThrows(GroovyCastException.class,
        () -> parseClassFromScript("def plot = new Plot() \n" + "Line line = (Line) Plot "));
  }

  @Test
  public void parseIllegalArgumentScript_throwIllegalArgumentException() {
    org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
        () -> parseClassFromScript("def plot = new Plot() \n" + "plot.setYBound(Arrays.asList(1, 2, 3)) "));
  }

  @Test
  public void parseWrongNumberFormatScript_throwNumberFormatException() {
    org.junit.jupiter.api.Assertions.assertThrows(NumberFormatException.class,
        () -> parseClassFromScript("def intval = Color.decode(\"FF00FF\"); "));
  }

  @Test
  public void parseIndexOutOfBoundsScript_throwIndexOutOfBoundsException() {
    org.junit.jupiter.api.Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> parseClassFromScript("def arr = new ArrayList() \n" + "arr.get(5) "));
  }

  @Test
  public void parseNotExistingConstructorScript_throwGroovyRuntimeException() {
    org.junit.jupiter.api.Assertions.assertThrows(GroovyRuntimeException.class,
        () -> parseClassFromScript("def plot = new Plot(123)"));
  }

  @Test
  public void parseDivisionByZeroScript_throwArithmeticException() {
    org.junit.jupiter.api.Assertions.assertThrows(ArithmeticException.class,
        () -> parseClassFromScript("1/0 "));
  }
}
