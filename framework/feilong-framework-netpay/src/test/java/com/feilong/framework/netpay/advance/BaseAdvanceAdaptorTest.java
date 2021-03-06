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
package com.feilong.framework.netpay.advance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.feilong.commons.core.tools.json.JsonUtil;
import com.feilong.framework.netpay.advance.command.QueryRequest;
import com.feilong.framework.netpay.advance.command.QueryResult;

/**
 * The Class BaseAdvanceAdaptorTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.6 2014-5-9 3:17:20
 */
@ContextConfiguration(locations = { "classpath*:spring/payment/advance/spring-payment-advanceAdaptor.xml" })
public class BaseAdvanceAdaptorTest extends AbstractJUnit4SpringContextTests{

    /** The Constant log. */
    private static final Logger  log = LoggerFactory.getLogger(BaseAdvanceAdaptorTest.class);

    // private String code = "44";

    /** The application context. */
    @SuppressWarnings("hiding")
    @Autowired
    protected ApplicationContext applicationContext;

    /**
     * Gets the query result.
     * 
     * @param paymentAdvanceAdaptor
     *            the payment advance adaptor
     * @param queryRequest
     *            the query request
     * @throws Exception
     *             the exception
     */
    protected void getQueryResult(PaymentAdvanceAdaptor paymentAdvanceAdaptor,QueryRequest queryRequest) throws Exception{
        QueryResult queryResult = paymentAdvanceAdaptor.getQueryResult(queryRequest);
        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(queryResult));
        }
    }

}
