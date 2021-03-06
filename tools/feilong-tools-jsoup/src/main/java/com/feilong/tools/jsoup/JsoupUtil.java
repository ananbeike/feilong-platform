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
package com.feilong.tools.jsoup;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.Validate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The Class JsoupUtil.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-4-11 下午11:17:42
 */
public final class JsoupUtil{

    // *******************************************************************************
    /**
     * 通过url 获得文档.
     * 
     * @param urlString
     *            the url string
     * @return the document
     * @throws JsoupUtilException
     *             if Exception
     */
    public static Document getDocument(String urlString) throws JsoupUtilException{
        try{
            URL url = new URL(urlString);
            int timeoutMillis = 10 * 1000;
            Document document = Jsoup.parse(url, timeoutMillis);
            return document;
        }catch (IOException e){
            throw new JsoupUtilException(e);
        }
    }

    /**
     * 通过url 获得文档.
     * 
     * @param url
     *            the url
     * @param userAgent
     *            the user agent
     * @return the document
     * @throws JsoupUtilException
     *             if Exception
     * @see org.jsoup.Jsoup#connect(String)
     * @see org.jsoup.Connection#userAgent(String)
     * @see org.jsoup.Connection#timeout(int)
     * @see org.jsoup.Connection#get()
     */
    public static Document getDocument(String url,String userAgent) throws JsoupUtilException{
        try{
            int millis = 10 * 1000;
            Document document = Jsoup.connect(url).userAgent(userAgent).timeout(millis).get();
            return document;
        }catch (IOException e){
            throw new JsoupUtilException(e);
        }

    }

    /**
     * Gets the elements by select.
     * 
     * @param url
     *            the url
     * @param selectQuery
     *            the select query
     * @return the elements by select
     * @throws JsoupUtilException
     *             the jsoup util exception
     * @see #getDocument(String)
     * @see org.jsoup.nodes.Element#select(String)
     */
    public static Elements getElementsBySelect(String url,String selectQuery) throws JsoupUtilException{
        Validate.notEmpty(url);
        Validate.notEmpty(selectQuery);
        Document document = getDocument(url);
        Elements elements = document.select(selectQuery);
        return elements;
    }

    /**
     * getElementById.
     * 
     * @param url
     *            the url
     * @param id
     *            the id
     * @return getElementById
     * @throws JsoupUtilException
     *             the jsoup util exception
     * @see #getDocument(String)
     * @see org.jsoup.nodes.Element#getElementById(String)
     */
    public static Element getElementById(String url,String id) throws JsoupUtilException{
        Validate.notEmpty(url);
        Validate.notEmpty(id);
        Document document = getDocument(url);
        Element element = document.getElementById(id);
        return element;
    }
}
