/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package jakarta.json.bind.tck.api.jsonbadapter;

import static org.junit.Assert.fail;

import java.lang.invoke.MethodHandles;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.tck.api.model.SimpleContainer;
import jakarta.json.bind.tck.api.model.SimpleContainerContainer;
import jakarta.json.bind.tck.api.model.SimpleStringAdapter;

/**
 * @test
 * @sources JsonbAdapterTest.java
 * @executeClass com.sun.ts.tests.jsonb.api.JsonbAdapterTest
 **/
@RunWith(Arquillian.class)
public class JsonbAdapterTest {
    
    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, MethodHandles.lookup().lookupClass().getPackage().getName());
    }

  /*
   * @testName: testAdaptFromJson
   *
   * @assertion_ids: JSONB:JAVADOC:53
   *
   * @test_Strategy: Assert that JsonbAdapter.adaptFromJson method can be
   * configured during object deserialization to provide conversion logic from
   * adapted object to original
   */
  @Test
  public void testAdaptFromJson() {
    Jsonb jsonb = JsonbBuilder
        .create(new JsonbConfig().withAdapters(new SimpleStringAdapter()));
    SimpleContainerContainer unmarshalledObject = jsonb.fromJson(
        "{ \"instance\" : { \"instance\" : \"Test String Adapted\" } }",
        SimpleContainerContainer.class);
    if (!"Test String".equals(unmarshalledObject.getInstance().getInstance())) {
      fail(
          "Failed to use JsonbAdapter.adaptFromJson method to provide conversion logic from adapted object to original during object deserialization.");
    }

    return; // passed
  }

  /*
   * @testName: testAdaptToJson
   *
   * @assertion_ids: JSONB:JAVADOC:55
   *
   * @test_Strategy: Assert that JsonbAdapter.adaptToJson method can be
   * configured during object serialization to provide conversion logic from
   * original object to adapted
   */
  @Test
  public void testAdaptToJson() {
    Jsonb jsonb = JsonbBuilder
        .create(new JsonbConfig().withAdapters(new SimpleStringAdapter()));
    String jsonString = jsonb.toJson(new SimpleContainerContainer() {
      {
        setInstance(new SimpleContainer() {
          {
            setInstance("Test String");
          }
        });
      }
    });
    if (!jsonString.matches(
        "\\{\\s*\"instance\"\\s*:\\s*\\{\\s*\"instance\"\\s*:\\s*\"Test String Adapted\"\\s*}\\s*}")) {
      fail(
          "Failed to use JsonbAdapter.adaptToJson method to provide conversion logic from original object to adapted during object serialization.");
    }

    return; // passed
  }
}
