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
package com.feilong.taglib.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

import com.feilong.commons.core.util.StringUtil;
import com.feilong.commons.core.util.Validator;
import com.feilong.servlet.http.RequestUtil;
import com.feilong.taglib.base.AbstractCommonTag;
import com.feilong.tools.reference.ip.IpUtil;

/**
 * 异常处理标签.
 * 
 * @author 金鑫 2010-3-13 下午05:32:00
 */
@Deprecated
public class ExceptionTag extends AbstractCommonTag{

	/** The logger. */
	public static Logger		logger				= Logger.getLogger(ExceptionTag.class);

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/** 方式 mail邮件发送 stdout系统控制台. */
	private String				method				= "mail";

	/**
	 * 是不是pojo类.
	 * 
	 * @author 金鑫
	 * @version 1.0 2010-4-19 下午02:55:37
	 * @param className
	 *            类名
	 * @return true, if is po jo class
	 */
	private boolean isPoJoClass(String className){
		return StringUtil.isContain(className, "pojo");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.feilong.taglib.FeiLongBaseTag#writeContent()
	 */
	@Override
	public String writeContent(){
		StringBuilder stringBuilder = new StringBuilder();
		HttpServletRequest request = getHttpServletRequest();
		Enumeration<String> attributeNames = request.getAttributeNames();
		String attributeName = "";
		Object attributeValue = "";
		String className = "";
		String ip = RequestUtil.getClientIp(request);
		stringBuilder.append(ip);
		stringBuilder.append(IpUtil.ipToAddress(ip));
		stringBuilder.append("<br/>" + SystemUtils.LINE_SEPARATOR);
		while (attributeNames.hasMoreElements()){
			attributeName = attributeNames.nextElement();
			attributeValue = request.getAttribute(attributeName);
			if (null == attributeValue){
				attributeValue = "";
			}else{
				className = attributeValue.getClass().getName();
				if (isPoJoClass(className)){
					attributeValue = className + "(类----jinxin)";
				}
			}
			stringBuilder.append("request.attribute['" + attributeName + "'] = " + attributeValue);
			stringBuilder.append("<br/>");
			stringBuilder.append(SystemUtils.LINE_SEPARATOR);
		}
		/** ************session***************** */
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames_session = session.getAttributeNames();
		String attributeName_session = "";
		Object attributeValue_session = "";
		String className_session = "";
		while (attributeNames_session.hasMoreElements()){
			attributeName_session = attributeNames_session.nextElement();
			attributeValue_session = session.getAttribute(attributeName_session);
			if (null == attributeValue_session){
				attributeValue_session = "";
			}else{
				className_session = attributeValue_session.getClass().getName();
				if (isPoJoClass(className)){
					attributeValue_session = className_session + "(类----jinxin)";
				}
			}
			stringBuilder.append("session.attribute['" + attributeName_session + "'] = " + attributeValue_session);
			stringBuilder.append("<br/>");
			stringBuilder.append(SystemUtils.LINE_SEPARATOR);
		}
		// 错误代码
		Object status_code = request.getAttribute("javax.servlet.error.status_code");
		if (null != status_code){
			if ("mail".equals(method)){
				// String emailTitle = FeiLongEmail.getErrorEmailTitle(pageContext.getServletContext(), null, request);
				// MailEntity mailInfo = new MailEntity();
				// mailInfo.setToAddress(FeiLongPropertiesConfigure.getPropertiesValueWithResourceBundle("feilong.user", "tdEmail"));
				// mailInfo.setSubject(emailTitle);
				// mailInfo.setContent(stringBuilder.toString());
				// SimpleMailSender simpleMailSender = new SimpleMailSender();
				// simpleMailSender.sendHtmlMail(mailInfo);
			}else if ("stdout".equals(method)){
				logger.error(stringBuilder.toString());
			}
		}else{
			logger.info("========================================");
			logger.info("不是404-----500");
			String url = request.getParameter("url");
			if (Validator.isNotNullOrEmpty(url)){
				//logger.info(FeiLongSecurity.getDecryptURL(url));
			}
			logger.error(stringBuilder.toString());
			logger.info("========================================");
		}
		return "";
	}

	/**
	 * 设置 方式 mail邮件发送 stdout系统控制台.
	 * 
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method){
		this.method = method;
	}
}