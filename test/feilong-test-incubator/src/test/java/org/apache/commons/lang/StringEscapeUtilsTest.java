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
package org.apache.commons.lang;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class StringEscapeUtilsTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-6-25 15:38:17
 */
public class StringEscapeUtilsTest{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(StringEscapeUtilsTest.class);

	/**
	 * Unescape html.
	 */
	@Test
	public void unescapeHtml(){
		String a = "m&eacute;n&nbsp;";
		String result = null;
		result = StringEscapeUtils.unescapeHtml3(a);
		// result = HtmlUtils.htmlUnescape(a);
		log.info(result);
	}

	/**
	 * Unescape html2.
	 */
	@Test
	public void unescapeHtml2(){
		String a = "第572章 三十年后（大结局） *局";
		String result = null;
		result = org.apache.commons.lang.StringEscapeUtils.escapeHtml(a);
		// result = HtmlUtils.htmlUnescape(a);
		log.info(result);
	}
}