/*
 *  Copyright 2017 TWO SIGMA OPEN SOURCE, LLC
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

import com.twosigma.beakerx.KernelTest;
import com.twosigma.beakerx.TryResult;
import com.twosigma.beakerx.evaluator.BaseEvaluator;
import com.twosigma.beakerx.groovy.TestGroovyEvaluator;
import com.twosigma.beakerx.jvm.object.EvaluationObject;
import com.twosigma.beakerx.jvm.object.SimpleEvaluationObject;
import com.twosigma.beakerx.message.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.twosigma.beakerx.MessageFactorTest.commMsg;
import static org.assertj.core.api.Assertions.assertThat;

public class GroovyEvaluatorPassingSimpleEvaluationObjectTest {

  private BaseEvaluator groovyEvaluator;
  private EvaluationObjectFactoryMock evaluationObjectFactory;

  @Before
  public void setUp() throws Exception {
    groovyEvaluator = TestGroovyEvaluator.groovyEvaluator();
    evaluationObjectFactory = new EvaluationObjectFactoryMock();
  }

  @After
  public void tearDown() throws Exception {
    groovyEvaluator.exit();
  }

  @Test
  public void shouldPassSimpleEvaluationObjectToShell() throws Exception {
    //given
    String code = "" +
            "import com.twosigma.beakerx.evaluator.InternalVariable\n" +
            "InternalVariable.getParentHeader()";
    Message message = commMsg();
    EvaluationObject seo = evaluationObjectFactory.createSeo(code, message);
    //when
    TryResult evaluate = groovyEvaluator.evaluate(seo, code);
    //then
    assertThat(evaluate.result()).isEqualTo(message);
  }
}
