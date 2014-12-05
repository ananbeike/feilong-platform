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
package com.feilong.commons.core.date;

import static com.feilong.commons.core.MessageConstants.DATE_DAY;
import static com.feilong.commons.core.MessageConstants.DATE_HOUR;
import static com.feilong.commons.core.MessageConstants.DATE_MILLISECOND;
import static com.feilong.commons.core.MessageConstants.DATE_MINUTE;
import static com.feilong.commons.core.MessageConstants.DATE_SECOND;
import static com.feilong.commons.core.MessageConstants.DATE_THEDAY_BEFORE_YESTERDAY;
import static com.feilong.commons.core.MessageConstants.DATE_YESTERDAY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.feilong.commons.core.util.Validator;

/**
 * 日期扩展工具类,和 DateUtil 的区别在于, DateUtil是纯 操作Date API的工具类,而DateExtensionUtil类 用于个性化 输出结果,针对业务个性化显示<br>
 * 
 * 获得两个日期间隔
 * <ul>
 * <li>{@link #getIntervalDayList(String, String, String)}</li>
 * <li>{@link #getIntervalForView(long)}</li>
 * <li>{@link #getIntervalForView(Date, Date)}</li>
 * </ul>
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.8 2014年7月31日 下午2:34:33
 * @since 1.0.8
 */
public final class DateExtensionUtil{

	/** Don't let anyone instantiate this class. */
	private DateExtensionUtil(){}

	// [start]extent 获得时间 /时间数组,可以用于sql查询
	/**
	 * 获得当天0:00:00及下一天0:00:00,一般用于统计当天数据,between ... and ...
	 * 
	 * <pre>
	 * 比如今天是 2012-10-16 22:18:34
	 * 
	 * return {2012-10-16 00:00:00.000,2012-10-17 00:00:00.000}
	 * </pre>
	 * 
	 * @return Date数组 第一个为today 第二个为tomorrow
	 * @since 1.0
	 * @deprecated 方法名在未来版本可能会更新
	 */
	public final static Date[] getExtentToday(){
		Calendar calendar = CalendarUtil.getResetTodayCalendar_byDay();
		Date today = calendar.getTime();
		// ***************************
		calendar.add(Calendar.DATE, 1);
		Date tomorrow = calendar.getTime();
		Date[] dates = { today, tomorrow };
		return dates;
	}

	/**
	 * 获得昨天的区间 [yestoday,today]<br>
	 * 第一个为昨天00:00 <br>
	 * 第二个为今天00:00 <br>
	 * 一般用于sql/hql统计昨天数据,between ... and ...
	 * 
	 * <pre>
	 * 比如现在 :2012-10-16 22:46:42
	 * 
	 * return  {2012-10-15 00:00:00.000,2012-10-16 00:00:00.000}
	 * </pre>
	 * 
	 * @return Date数组 <br>
	 *         第一个为昨天00:00 <br>
	 *         第二个为今天00:00
	 * @since 1.0
	 * @deprecated 方法名会更新
	 */
	public final static Date[] getExtentYesterday(){
		Calendar calendar = CalendarUtil.getResetTodayCalendar_byDay();
		Date today = calendar.getTime();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		Date[] dates = { yesterday, today };
		return dates;
	}

	// [end]
	/**
	 * 获得两个日期时间的日期间隔时间集合(包含最小和最大值),用于统计日报表<br>
	 * 每天的日期会被重置清零 <code>00:00:00.000</code>
	 * 
	 * <pre>
	 * getIntervalDayList("2011-03-5 23:31:25.456","2011-03-10 01:30:24.895", DatePattern.commonWithTime)
	 * 
	 * return
	 * 2011-03-05 00:00:00
	 * 2011-03-06 00:00:00
	 * 2011-03-07 00:00:00
	 * 2011-03-08 00:00:00
	 * 2011-03-09 00:00:00
	 * 2011-03-10 00:00:00
	 * 
	 * </pre>
	 * 
	 * @param fromDate
	 *            开始时间
	 * @param toDate
	 *            结束时间
	 * @param datePattern
	 *            时间模式 {@link DatePattern}
	 * @return the interval day list
	 * @see DateUtil#getIntervalDay(Date, Date)
	 */
	public final static List<Date> getIntervalDayList(String fromDate,String toDate,String datePattern){
		List<Date> dateList = new ArrayList<Date>();
		//***************************************************************/
		Date begin_Date = DateUtil.string2Date(fromDate, datePattern);
		Date end_Date = DateUtil.string2Date(toDate, datePattern);
		// ******重置时间********
		Date beginDateReset = CalendarUtil.getResetDate_byDay(begin_Date);
		Date endDateReset = CalendarUtil.getResetDate_byDay(end_Date);
		//***************************************************************/
		// 相隔的天数
		int intervalDay = DateUtil.getIntervalDay(beginDateReset, endDateReset);
		//***************************************************************/
		Date minDate = beginDateReset;
		if (beginDateReset.equals(endDateReset)){
			minDate = beginDateReset;
		}else if (beginDateReset.before(endDateReset)){
			minDate = beginDateReset;
		}else{
			minDate = endDateReset;
		}
		//***************************************************************/
		dateList.add(minDate);
		//***************************************************************/
		if (intervalDay > 0){
			for (int i = 0; i < intervalDay; ++i){
				dateList.add(DateUtil.addDay(minDate, i + 1));
			}
		}
		//***************************************************************/
		return dateList;
	}

	// [start]转换成特色时间 toHumanizationDateString
	/**
	 * 人性化显示date时间,依据是和现在的时间间隔
	 * <p>
	 * 转换规则,将传入的inDate和 new Date()当前时间比较<br>
	 * 当两者的时间差,(一般inDate小于当前时间 ,暂时不支持大于当前时间)
	 * <ul>
	 * <li>如果时间差为0天,<br>
	 * 如果小时间隔等于0,如果分钟间隔为0,则显示间隔秒 + "秒钟前"<br>
	 * 如果小时间隔等于0,如果分钟间隔不为0,则显示间隔分钟 + "分钟前"<br>
	 * </li>
	 * <li>如果时间差为0天,<br>
	 * 如果小时间隔不等于0,如果inDate的day 和current的day 相等,则显示space_hour + "小时前"<br>
	 * 如果小时间隔不等于0,如果inDate的day 和current的day不相等,则显示"昨天 " + date2String(inDate, "HH:mm")<br>
	 * </li>
	 * <li>如果时间差为1天,且inDate的day+1和currentDate的day 相等,则显示"昨天 HH:mm"</li>
	 * <li>如果时间差为1天,且inDate的day+1和currentDate的day 不相等,则显示"前天 HH:mm"</li>
	 * <li>如果时间差为2天,且inDate的day+2和currentDate的day 相等,则显示"前天 HH:mm"</li>
	 * <li>如果时间差为2天,且inDate的day+2和currentDate的day 不相等,<br>
	 * 1).如果inDate的year和currentDate的year相等,则显示"MM-dd HH:mm"<br>
	 * 2).如果inDate的year和currentDate的year不相等,则显示"yyyy-MM-dd HH:mm"</li>
	 * <li>如果时间差大于2天<br>
	 * 1).如果inDate的year和currentDate的year相等,则显示"MM-dd HH:mm"<br>
	 * 2).如果inDate的year和currentDate的year不相等,则显示"yyyy-MM-dd HH:mm"</li>
	 * </ul>
	 * 
	 * @param inDate
	 *            任意日期<br>
	 *            warn:{@code 一般inDate<=当前时间} ,暂时不支持大于当前时间
	 * @return 人性化显示date时间
	 * @see DateUtil#date2String(Date, String)
	 * @see DateUtil#getYear(Date)
	 * @see DateUtil#getDayOfMonth(Date)
	 * @see DateUtil#getYear(Date)
	 * @see DateUtil#getIntervalTime(Date, Date)
	 * @see DateUtil#getIntervalDay(long)
	 * @see DateUtil#getIntervalHour(long)
	 * @see DateUtil#getIntervalMinute(long)
	 * @see DateUtil#getIntervalSecond(long)
	 * @deprecated 未来名称可能会更改
	 */
	public final static String toHumanizationDateString(Date inDate){
		Date nowDate = new Date();

		//**************************************************************************************/
		String returnValue = null;
		// 传过来的日期的年份
		int inYear = DateUtil.getYear(inDate);
		// 传过来的日期的月份
		// int inMonth = getMonth(inDate);
		// 传过来的日期的日
		int inDay = DateUtil.getDayOfMonth(inDate);

		//**************************************************************************************/
		// 当前时间的年
		int year = DateUtil.getYear(nowDate);
		// 当前时间的余额
		// int month = getMonth();
		// 当前时间的日
		int day = DateUtil.getDayOfMonth(nowDate);

		//**************************************************************************************/
		// 任意日期和现在相差的毫秒数
		long space_time = DateUtil.getIntervalTime(inDate, nowDate);
		// 相差天数
		int space_day = DateUtil.getIntervalDay(space_time);
		// 相差小时数
		int space_hour = DateUtil.getIntervalHour(space_time);
		// 相差分数
		int space_minute = DateUtil.getIntervalMinute(space_time);
		// 相差秒数
		int space_second = DateUtil.getIntervalSecond(space_time);
		//**************************************************************************************/
		// 间隔一天
		if (space_day == 1){
			if (DateUtil.isEquals(DateUtil.addDay(inDate, 1), nowDate, DatePattern.onlyDate)){
				returnValue = DATE_YESTERDAY + " ";
			}else{
				returnValue = DATE_THEDAY_BEFORE_YESTERDAY + " ";
			}
			returnValue += DateUtil.date2String(inDate, DatePattern.onlyTime_withoutSecond);
		}
		// 间隔2天
		else if (space_day == 2){
			if (DateUtil.isEquals(DateUtil.addDay(inDate, 2), nowDate, DatePattern.onlyDate)){
				returnValue = DATE_THEDAY_BEFORE_YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.onlyTime_withoutSecond);
			}else{
				// 今年
				if (year == inYear){
					returnValue = DateUtil.date2String(inDate, DatePattern.commonWithoutAndYearSecond);
				}else{
					returnValue = DateUtil.date2String(inDate, DatePattern.commonWithoutSecond);
				}
			}
		}
		// 间隔大于2天
		else if (space_day > 2){
			// 今年
			if (year == inYear){
				returnValue = DateUtil.date2String(inDate, DatePattern.commonWithoutAndYearSecond);
			}else{
				returnValue = DateUtil.date2String(inDate, DatePattern.commonWithoutSecond);
			}
		}
		// 间隔0天
		else if (space_day == 0){
			// 小时间隔
			if (space_hour != 0){
				if (inDay == day){
					returnValue = space_hour + DATE_HOUR + "前";
				}else{
					returnValue = DATE_YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.onlyTime_withoutSecond);
				}
			}else{
				// 分钟间隔
				if (space_minute == 0){
					returnValue = space_second + DATE_SECOND + "前";
				}else{
					returnValue = space_minute + DATE_MINUTE + "前";
				}
			}
		}
		return returnValue;
	}

	// [end]

	/**
	 * 将日期集合装成特定pattern的字符串集合.
	 * 
	 * @param dateList
	 *            日期集合
	 * @param datePattern
	 *            模式 {@link DatePattern}
	 * 
	 * @return 如果 Validator.isNotNullOrEmpty(dateList) return null;<br>
	 *         否则循环date转成string,返回{@code List<String>}
	 */
	public final static List<String> toStringList(List<Date> dateList,String datePattern){
		if (Validator.isNotNullOrEmpty(dateList)){
			List<String> stringList = new ArrayList<String>();
			for (Date date : dateList){
				stringList.add(DateUtil.date2String(date, datePattern));
			}
			return stringList;
		}
		return null;
	}

	/**
	 * 将时间(单位毫秒),并且转换成直观的表示方式.
	 * 
	 * <pre>
	 * getIntervalForView(13516)
	 * return 13秒516毫秒
	 * 
	 * getIntervalForView(0)
	 * return 0
	 * 
	 * 自动增加 天,小时,分钟,秒,毫秒中文文字
	 * </pre>
	 * 
	 * @param space_time
	 *            单位毫秒
	 * @return 将时间(单位毫秒),并且转换成直观的表示方式<br>
	 *         如果 space_time 是0 直接返回0
	 * @see DateUtil#getIntervalDay(long)
	 * @see DateUtil#getIntervalHour(long)
	 * @see DateUtil#getIntervalMinute(long)
	 * @see DateUtil#getIntervalSecond(long)
	 */
	public final static String getIntervalForView(long space_time){
		if (0 == space_time){
			return "0";
		}
		// **************************************************************************************
		// 间隔天数
		long space_day = DateUtil.getIntervalDay(space_time);
		// 间隔小时 减去间隔天数后,
		long space_hour = DateUtil.getIntervalHour(space_time) - space_day * 24;
		// 间隔分钟 减去间隔天数及间隔小时后,
		long space_minute = DateUtil.getIntervalMinute(space_time) - (space_day * 24 + space_hour) * 60;
		// 间隔秒 减去间隔天数及间隔小时,间隔分钟后,
		long space_second = DateUtil.getIntervalSecond(space_time) - ((space_day * 24 + space_hour) * 60 + space_minute) * 60;
		// 间隔秒 减去间隔天数及间隔小时,间隔分钟后,
		long space_millisecond = space_time - (((space_day * 24 + space_hour) * 60 + space_minute) * 60 + space_second) * 1000;
		// **************************************************************************************
		StringBuilder stringBuilder = new StringBuilder();
		if (0 != space_day){
			stringBuilder.append(space_day + DATE_DAY);
		}
		if (0 != space_hour){
			stringBuilder.append(space_hour + DATE_HOUR);
		}
		if (0 != space_minute){
			stringBuilder.append(space_minute + DATE_MINUTE);
		}
		if (0 != space_second){
			stringBuilder.append(space_second + DATE_SECOND);
		}
		if (0 != space_millisecond){
			stringBuilder.append(space_millisecond + DATE_MILLISECOND);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获得两日期之间的间隔,并且转换成直观的表示方式.
	 * 
	 * <pre>
	 * getIntervalForView(2011-05-19 8:30:40,2011-05-19 11:30:24) 
	 * return 转换成2小时59分44秒
	 * 
	 * getIntervalForView(2011-05-19 11:31:25.456,2011-05-19 11:30:24.895)
	 * return 1分钟1秒 
	 * 
	 * 自动增加 天,小时,分钟,秒,毫秒中文文字
	 * </pre>
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 获得两日期之间的间隔,并且转换成直观的表示方式
	 * @see #getIntervalForView(long)
	 * @see DateUtil#getIntervalTime(Date, Date)
	 */
	public final static String getIntervalForView(Date date1,Date date2){
		long space_time = DateUtil.getIntervalTime(date1, date2);
		return getIntervalForView(space_time);
	}
}