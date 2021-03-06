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
package com.feilong.test.webservice.cxf.client.nike;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.4.0
 * 2011-04-28T18:44:13.311+08:00
 * Generated source version: 2.4.0
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014-6-25 16:29:05
 */

@WebService(targetNamespace = "http://www.nikestore.com.cn/webService",name = "ExpressDeliveryService")
@XmlSeeAlso({ ObjectFactory.class })
public interface ExpressDeliveryService{

    /**
     * Transfer order sign.
     * 
     * @param arg0
     *            the arg0
     * @param arg1
     *            the arg1
     * @return the java.lang. string
     */
    @WebResult(name = "returnInfo",targetNamespace = "")
    @RequestWrapper(localName = "transferOrderSign",targetNamespace = "http://www.nikestore.com.cn/webService",className = "com.feilong.test.webservice.cxf.client.nike.TransferOrderSign")
    @WebMethod
    @ResponseWrapper(localName = "transferOrderSignResponse",targetNamespace = "http://www.nikestore.com.cn/webService",className = "com.feilong.test.webservice.cxf.client.nike.TransferOrderSignResponse")
    public java.lang.String transferOrderSign(
                    @WebParam(name = "arg0",targetNamespace = "") java.lang.String arg0,
                    @WebParam(name = "arg1",targetNamespace = "") java.lang.String arg1);
}
