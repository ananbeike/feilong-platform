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
package com.feilong.framework.netpay.payment.sprintasia.creditcard.util;

import org.junit.Test;

import com.feilong.framework.bind.parse.XmlParse;
import com.feilong.framework.netpay.advance.adaptor.sprintasia.creditcard.command.CreditCardQueryResult;
import com.feilong.framework.netpay.advance.adaptor.sprintasia.creditcard.util.CreditCardQueryResultPaser;

/**
 * The Class CreditCardQueryResultPaserTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2014年5月5日 下午10:04:24
 */
public class CreditCardQueryResultPaserTest{

    /**
     * Parses the wddx packet.
     */
    @Test
    public void parseWddxPacket(){
        String wddxPacketXML = "<wddxPacket version='1.0'><header/><data><struct><var name='TRANSACTIONID'><string>868BBC35-A5D1-FCBF-0453F134C99B5553</string></var><var name='ACQUIRERRESPONSECODE'><string>000</string></var><var name='SCRUBMESSAGE'><string></string></var><var name='AMOUNT'><number>9011999.0</number></var><var name='SERVICEVERSION'><string>2.0</string></var><var name='TRANSACTIONSCRUBCODE'><string></string></var><var name='MERCHANTTRANSACTIONID'><string>010003170001</string></var><var name='CURRENCY'><string>IDR</string></var><var name='TRANSACTIONSTATUS'><string>APPROVED</string></var><var name='SITEID'><string>Blanja2</string></var><var name='TRANSACTIONDATE'><string>2014-04-23 15:19:27</string></var><var name='ACQUIRERCODE'><string>AUTH20140423152019PM</string></var><var name='SCRUBCODE'><string></string></var><var name='TRANSACTIONSCRUBMESSAGE'><string></string></var><var name='ACQUIRERAPPROVALCODE'><string>298883</string></var><var name='TRANSACTIONTYPE'><string>AUTHORIZATION</string></var></struct></data></wddxPacket>";
        wddxPacketXML = "<wddxPacket version='1.0'><header/><data><struct><var name='TRANSACTIONID'><string>806809A2-0485-9D54-222DDF9928ED7AEC</string></var><var name='ACQUIRERRESPONSECODE'><string>000</string></var><var name='SCRUBMESSAGE'><string></string></var><var name='AMOUNT'><number>9011999.0</number></var><var name='SERVICEVERSION'><string>2.0</string></var><var name='TRANSACTIONSCRUBCODE'><string></string></var><var name='MERCHANTTRANSACTIONID'><string>010003160001</string></var><var name='CURRENCY'><string>IDR</string></var><var name='TRANSACTIONSTATUS'><string>APPROVED</string></var><var name='SITEID'><string>Blanja2</string></var><var name='TRANSACTIONDATE'><string>2014-04-23 12:27:47</string></var><var name='ACQUIRERCODE'><string>AUTH20140423122829PM</string></var><var name='SCRUBCODE'><string></string></var><var name='TRANSACTIONSCRUBMESSAGE'><string></string></var><var name='ACQUIRERAPPROVALCODE'><string>932785</string></var><var name='TRANSACTIONTYPE'><string>AUTHORIZATION</string></var></struct></data></wddxPacket>";

        XmlParse<CreditCardQueryResult> queryResultXmlParse = new CreditCardQueryResultPaser();
        queryResultXmlParse.parseXML(wddxPacketXML);
    }
}
