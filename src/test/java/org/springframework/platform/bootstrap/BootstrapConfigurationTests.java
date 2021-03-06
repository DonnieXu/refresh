/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.platform.bootstrap;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.After;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.platform.bootstrap.config.PropertySourceLocator;

/**
 * @author Dave Syer
 *
 */
public class BootstrapConfigurationTests {

	private ConfigurableApplicationContext context;

	@After
	public void close() {
		if (context != null) {
			context.close();
		}
	}

	@Test
	public void picksUpAdditionalPropertySource() {
		context = new SpringApplicationBuilder().web(false).sources(
				BareConfiguration.class).run();
		assertEquals("bar", context.getEnvironment().getProperty("bootstrap.foo"));
	}

	@Configuration
	protected static class BareConfiguration {
	}

	@Configuration
	protected static class PropertySourceConfiguration implements PropertySourceLocator {

		@Override
		public PropertySource<?> locate() {
			return new MapPropertySource("testBootstrap",
					Collections.<String, Object> singletonMap("bootstrap.foo", "bar"));
		}
	}

}
