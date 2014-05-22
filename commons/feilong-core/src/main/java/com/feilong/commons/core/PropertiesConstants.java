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
package com.feilong.commons.core;

import static com.feilong.commons.core.configure.ResourceBundleUtil.getValue;

/**
 * 配置文件<br>
 * This class defines the common PropertiesConstants ,so that they can be referenced as a constant within Java code. <br>
 * 参考了 velocity RuntimeConstants.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-19 下午05:58:59
 * @since 1.0
 * @deprecated 每个类自己实现
 */
@Deprecated
public interface PropertiesConstants{

	/**
	 * feilong-core-message 路径,<br>
	 * 包名+名称,并去除后缀,默认classpath下面 <code>{@value}</code>
	 */
	String	$FEILONG_CORE_MESSAGE						= "messages/feilong-core-message";

	// ******************************************************************************************************************************************************************************************
	/** 星期. */
	String	CONFIG_DATE_WEEK							= getValue($FEILONG_CORE_MESSAGE, "config_date_week");

	/** 昨天. */
	String	CONFIG_DATE_YESTERDAY						= getValue($FEILONG_CORE_MESSAGE, "config_date_yesterday");

	/** 前天. */
	String	CONFIG_DATE_THEDAY_BEFORE_YESTERDAY			= getValue($FEILONG_CORE_MESSAGE, "config_date_theDayBeforeYesterday");

	/** 天. */
	String	CONFIG_DATE_DAY								= getValue($FEILONG_CORE_MESSAGE, "config_date_day");

	/** 小时. */
	String	CONFIG_DATE_HOUR							= getValue($FEILONG_CORE_MESSAGE, "config_date_hour");

	/** 分钟. */
	String	CONFIG_DATE_MINUTE							= getValue($FEILONG_CORE_MESSAGE, "config_date_minute");

	/** 秒. */
	String	CONFIG_DATE_SECOND							= getValue($FEILONG_CORE_MESSAGE, "config_date_second");

	/** 毫秒. */
	String	CONFIG_DATE_MILLISECOND						= getValue($FEILONG_CORE_MESSAGE, "config_date_millisecond");

	// **************************************************************************************************************************************************************
	/** 数字和小写的字母. */
	String	CONFIG_VALIDATECODE_NUMBERSANDLITTLELETTERS	= getValue($FEILONG_CORE_MESSAGE, "config_validateCode_numbersAndLittleLetters");

	/** 数字和大小写字母. */
	String	CONFIG_NUMBERSANDALLLETTERS					= getValue($FEILONG_CORE_MESSAGE, "config_numbersAndAllLetters");					;

	/** 所有的数字. */
	String	CONFIG_NUMBERS								= getValue($FEILONG_CORE_MESSAGE, "config_numbers");
}