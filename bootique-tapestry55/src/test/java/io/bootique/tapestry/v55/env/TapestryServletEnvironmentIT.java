/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.bootique.tapestry.v55.env;

import io.bootique.BQRuntime;
import io.bootique.jetty.JettyModule;
import io.bootique.tapestry.v55.TapestryModule;
import io.bootique.tapestry.v55.env.TapestryEnvironment;
import io.bootique.test.junit.BQTestFactory;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;

public class TapestryServletEnvironmentIT {

    @Rule
    public BQTestFactory app = new BQTestFactory();

    @Test
    public void testGetRegistry_BeforeStart() {
        BQRuntime runtime = app.app()
                .module(JettyModule.class)
                .module(TapestryModule.class)
                .property("bq.tapestry.appPackage", "no.such.package")
                .createRuntime();

        // create runtime, but do not run .. as a result there should be no T5 registry

        Optional<Registry> registry = runtime.getInstance(TapestryEnvironment.class).getRegistry();
        Assert.assertFalse(registry.isPresent());
    }

    @Test
    public void testGetRegistry() {
        BQRuntime runtime = app.app("-s")
                .module(JettyModule.class)
                .module(TapestryModule.class)
                .property("bq.tapestry.appPackage", "no.such.package")
                .createRuntime();

        runtime.run();

        Optional<Registry> registry = runtime.getInstance(TapestryEnvironment.class).getRegistry();
        Assert.assertTrue(registry.isPresent());
        assertNotNull(registry.get().getService(ComponentClassResolver.class));
    }
}
