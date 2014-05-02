/**
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
package com.feilong.netpay.adaptor.bca.klikbca;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.util.StringUtil;
import com.feilong.commons.core.util.Validator;
import com.feilong.netpay.adaptor.AbstractPaymentAdaptor;
import com.feilong.netpay.command.PayRequest;
import com.feilong.netpay.command.PaymentFormEntity;
import com.feilong.netpay.command.TradeRole;
import com.feilong.tools.net.httpclient.HttpClientUtilException;

/**
 * The Class KlikBCAAdaptor.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Jan 15, 2013 8:41:39 PM
 */
public class KlikBCAAdaptor extends AbstractPaymentAdaptor{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(KlikBCAAdaptor.class);

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.adaptor.PaymentAdaptor#getPaymentFormEntity(com.feilong.netpay.command.PayRequest, java.util.Map)
	 */
	public PaymentFormEntity getPaymentFormEntity(PayRequest payRequest,Map<String, String> specialSignMap){
		// KlikBCA 的支付方式 是 网关主动在 inquery,和通用的不一样
		throw new UnsupportedOperationException("KlikBCAAdaptor not support getPaymentFormEntity");
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doNotifyVerify(javax.servlet.http.HttpServletRequest)
	 */
	public boolean verifyNotify(HttpServletRequest request){

		String userid = request.getParameter("userid");

		String transno = request.getParameter("transno");

		// 19 DD/mm/YYYY HH:MM:SS
		String transdate = request.getParameter("transdate");

		// 999999999999.99
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");

		String adddata = request.getParameter("adddata");

		if (Validator.isNullOrEmpty(userid)){
			return false;
		}
		if (Validator.isNullOrEmpty(transno)){
			return false;
		}
		if (Validator.isNullOrEmpty(transdate)){
			return false;
		}
		if (Validator.isNullOrEmpty(amount)){
			return false;
		}
		if (Validator.isNullOrEmpty(type)){
			return false;
		}

		// Value of type field always N
		if (!"N".equals(type)){
			return false;
		}

		// 这里未发现 参数有状态的判断

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doGetFeedbackSoCode(javax.servlet.http.HttpServletRequest)
	 */
	public String getFeedbackTradeNo(HttpServletRequest request){
		return request.getParameter("transno");
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doGetFeedbackTotalFee(javax.servlet.http.HttpServletRequest)
	 */
	public String getFeedbackTotalFee(HttpServletRequest request){
		// amount fields consist of 3 digits of currency and 11 digits of numeric format
		return StringUtil.substring(request.getParameter("amount"), 3);
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.adaptor.PaymentAdaptor#doRedirectVerify(javax.servlet.http.HttpServletRequest)
	 */
	public boolean verifyRedirect(HttpServletRequest request){
		throw new UnsupportedOperationException("KlikBCAAdaptor not support doRedirectVerify");
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doCloseTrade(java.lang.String, com.feilong.netpay.command.TradeRole)
	 */
	public boolean closeTrade(String orderNo,TradeRole tradeRole) throws HttpClientUtilException{
		throw new UnsupportedOperationException("KlikBCAAdaptor not support doCloseTrade");
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#isSupportCloseTrade()
	 */
	public boolean isSupportCloseTrade(){
		return false;
	}
}