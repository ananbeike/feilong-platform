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
package com.feilong.netpay.adaptor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.feilong.commons.core.awt.DesktopUtil;
import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;
import com.feilong.commons.core.enumeration.CharsetType;
import com.feilong.commons.core.io.IOWriteUtil;
import com.feilong.commons.core.util.JsonFormatUtil;
import com.feilong.netpay.command.PaySo;
import com.feilong.netpay.command.PaySoLine;
import com.feilong.netpay.command.PaymentFormEntity;
import com.feilong.tools.velocity.VelocityUtil;

/**
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Mar 22, 2014 4:31:38 PM
 */
@ContextConfiguration(locations = { "classpath*:spring/payment/spring-payment.xml" })
public class BasePaymentTest extends AbstractJUnit4SpringContextTests{

	private static final Logger	log					= LoggerFactory.getLogger(BasePaymentTest.class);

	private String				templateInClassPath	= "paymentChannel.vm";

	private String				encode				= CharsetType.UTF8;

	/**
	 * 文件路径
	 * 
	 * @param paymentAdaptor
	 * @param filePath
	 * @param specialSignMap
	 */
	protected void createPaymentForm(PaymentAdaptor paymentAdaptor,String filePath,Map<String, String> specialSignMap){
		String code = DateUtil.date2String(new Date(), DatePattern.timestamp);
		BigDecimal total_fee = new BigDecimal(60000.00f);

		PaySo paySo = new PaySo();
		paySo.setTradeNo(code);
		paySo.setTotalFee(total_fee);
		paySo.setBuyerEmail("venusdrogon@163.com");
		paySo.setBuyerName("jin xin");

		paySo.setTransferFee(new BigDecimal(10000.00f));

		List<PaySoLine> paySoLineList = paySo.getPaySoLineList();

		PaySoLine paySoLine1 = new PaySoLine();
		paySoLine1.setItemName("nike ;s free 5.0");
		paySoLine1.setUnitPrice(new BigDecimal(20000));
		paySoLine1.setQuantity(1);
		paySoLine1.setSubTotalPrice(new BigDecimal(20000));
		paySoLineList.add(paySoLine1);

		PaySoLine paySoLine2 = new PaySoLine();
		paySoLine2.setItemName("nike free 4.0");
		paySoLine2.setUnitPrice(new BigDecimal(15000));
		paySoLine2.setQuantity(2);
		paySoLine2.setSubTotalPrice(new BigDecimal(30000));
		paySoLineList.add(paySoLine2);

		// ******************************************************************
		String return_url = "http://www.esprit.cn/payment/redirect/klikPay";
		String notify_url = "/patment2url";

		PaymentFormEntity paymentFormEntity = paymentAdaptor.doBeginPayment(paySo, return_url, notify_url, specialSignMap);

		log.info(JsonFormatUtil.format(paymentFormEntity));

		String fullEncodedUrl = paymentFormEntity.getFullEncodedUrl();
		System.out.println(fullEncodedUrl);
		System.out.println();
		System.out.println();
		System.out.println();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paymentFormEntity", paymentFormEntity);

		String method = paymentFormEntity.getMethod();

		// if (method.toLowerCase().equals("get")){
		// DesktopUtil.browse(fullEncodedUrl);
		// }else{

		String html = VelocityUtil.parseTemplateWithClasspathResourceLoader(templateInClassPath, map);
		log.info(html);

		IOWriteUtil.write(filePath, html, encode);
		DesktopUtil.browse(filePath);
		// }
	}
}