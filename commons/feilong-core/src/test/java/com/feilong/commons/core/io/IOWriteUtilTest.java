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
package com.feilong.commons.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.date.DateExtensionUtil;
import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;

/**
 * The Class IOWriteUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Dec 23, 2013 10:29:11 PM
 */
public class IOWriteUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(IOWriteUtilTest.class);

    /**
     * Unescape html2.
     *
     * @throws NoSuchMethodException
     *             the no such method exception
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     */
    @Test
    public void unescapeHtml2() throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        String a = "第572章 三十年后（大结局） *局";
        String result = (String) MethodUtils.invokeExactStaticMethod(IOWriteUtil.class, "getFormatFilePath", a);
        log.info(result);
    }

    /**
     * Write1.
     *
     * @throws IOException
     *             the IO exception
     */
    @Test
    public void write1() throws IOException{

        Date beginDate = new Date();

        InputStream inputStream = FileUtil.getFileInputStream("K:\\Movie 电影\\1993 超级战警 马可·布拉姆贝拉 史泰龙.rmvb");
        OutputStream outputStream = FileUtil.getFileOutputStream("D:\\1993 超级战警 马可·布拉姆贝拉 史泰龙.rmvb");
        IOWriteUtil.write(inputStream, outputStream);

        Date endDate = new Date();
        log.info("time:{}", DateExtensionUtil.getIntervalForView(beginDate, endDate));
    }

    /**
     * Write.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void write() throws IOException{
        String url = "F:\\test.txt";
        String directoryName = SpecialFolder.getDesktop();
        IOWriteUtil.write(url, directoryName);
    }

    /**
     * Test write.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testWrite() throws IOException{
        String path = "/home/webuser/nike_int/expressdelivery/${yearMonth}/${expressDeliveryType}/vipQuery_${fileName}.log";
        Date date = new Date();
        path = path.replace("${yearMonth}", DateUtil.date2String(date, DatePattern.YEAR_AND_MONTH));
        path = path.replace("${expressDeliveryType}", "sf");
        path = path.replace("${fileName}", DateUtil.date2String(date, DatePattern.TIMESTAMP));
        // **************************************************************
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("****************************************************" + SystemUtils.LINE_SEPARATOR);
        stringBuilder.append("2011-05-13 22:24:37调用,系统顺丰在途订单597件" + SystemUtils.LINE_SEPARATOR);
        stringBuilder.append("耗时:429020" + SystemUtils.LINE_SEPARATOR);
        stringBuilder.append("****************************************************" + SystemUtils.LINE_SEPARATOR);
        stringBuilder.append("派送成功订单495条" + SystemUtils.LINE_SEPARATOR);
        for (int i = 0; i < 1000; i++){
            stringBuilder.append("订单号:20850010运单号:102085592089\t寄件时间:2011-05-09 19:00:00\t签收人:张寄件时间:2011-05-10 14:49:00\t回单类型:1\n");
        }
        IOWriteUtil.write(path, stringBuilder.toString());
    }
}
