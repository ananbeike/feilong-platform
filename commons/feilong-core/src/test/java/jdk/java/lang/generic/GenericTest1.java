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
package jdk.java.lang.generic;

import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GenericTest1.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-5-30 0:25:53
 */
public class GenericTest1{

	private static final Logger	log		= LoggerFactory.getLogger(GenericTest1.class);

	/** The list. */
	public List<String>			list	= new LinkedList<String>();

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchFieldException
	 *             the no such field exception
	 */
	public static void main(String[] args) throws SecurityException,NoSuchFieldException{
		ParameterizedType pt = (ParameterizedType) GenericTest1.class.getField("list").getGenericType();
		log.info("" + pt.getActualTypeArguments().length);
		log.info("" + pt.getActualTypeArguments()[0]);
	}
}