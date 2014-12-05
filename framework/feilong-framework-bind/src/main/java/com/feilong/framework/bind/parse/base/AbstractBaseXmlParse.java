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
package com.feilong.framework.bind.parse.base;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.bean.BeanUtil;
import com.feilong.commons.core.lang.reflect.ConstructorUtil;
import com.feilong.commons.core.tools.json.JsonUtil;
import com.feilong.framework.bind.exception.BuildCommandException;
import com.feilong.framework.bind.parse.AbstractXmlParse;

/**
 * 基本转换,使用反射 {@link BeanUtil}将属性一一设置,不需要配置varCommand.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.8 2014年7月18日 下午5:23:08
 * @param <T>
 *            任意的标准javaBean
 * @since 1.0.8
 */
public abstract class AbstractBaseXmlParse<T> extends AbstractXmlParse<T>{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(AbstractBaseXmlParse.class);

	/**
	 * Builds the command.
	 * 
	 * @param modelClass
	 *            the model class
	 * @param varNameAndValueMap
	 *            the var name and value map
	 * @return the t
	 * @throws BuildCommandException
	 *             the build command exception
	 */
	protected T buildCommand(Class<T> modelClass,Map<String, String> varNameAndValueMap) throws BuildCommandException{

		try{
			T t = ConstructorUtil.newInstance(modelClass);

			BeanUtil.populate(t, varNameAndValueMap);

			if (log.isInfoEnabled()){
				log.info("[{}]:{}", modelClass.getName(), JsonUtil.format(t));
			}
			return t;
		}catch (Exception e){
			log.error(e.getClass().getName(), e);
			throw new BuildCommandException(e);
		}
	}
}