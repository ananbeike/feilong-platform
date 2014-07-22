/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.context;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * The Class ApplicationContextTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-3-29 下午4:14:43
 */
@ContextConfiguration(locations = { "classpath:spring.xml" })
public class ApplicationContextTest extends AbstractJUnit4SpringContextTests{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(ApplicationContextTest.class);

	/**
	 * Test.
	 */
	@Test
	public void test(){
		log.debug("applicationContext.getBeanDefinitionCount():{}", applicationContext.getBeanDefinitionCount());

		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		log.debug("applicationContext.getBeanDefinitionNames():{}", beanDefinitionNames);
		for (String beanDefinitionName : beanDefinitionNames){
			Object bean = applicationContext.getBean(beanDefinitionName);

			String scope = applicationContext.isPrototype(beanDefinitionName) ? "Prototype" : (applicationContext.isSingleton(beanDefinitionName) ? "Singleton"
					: "");
			//applicationContext.FACTORY_BEAN_PREFIX;
			Object[] objects = { beanDefinitionName, bean.getClass().getName(), scope };
			log.debug("[beanDefinitionName]:{},[bean]:{},scope:[{}]", objects);
		}

		log.debug("applicationContext.getDisplayName():{}", applicationContext.getDisplayName());

		log.debug("applicationContext.getId():{}", applicationContext.getId());
		//3.1.0
		//log.debug("applicationContext.getEnvironment():{}", applicationContext.getEnvironment());
		log.debug("applicationContext.getStartupDate():{}", applicationContext.getStartupDate());

	}
}