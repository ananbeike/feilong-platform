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
package com.feilong.framework.code;

import java.util.Date;

import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;

/**
 * The Enum CodeType.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Apr 14, 2014 11:09:57 AM
 */
public enum CodeType{

    /** The create order. */
    CREATEORDER("4",DateUtil.string2Date("2014-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME),3,2,2),

    /** The return order. */
    RETURNORDER("1",DateUtil.string2Date("2014-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME),3,2,1);

    // *********************************************
    /** 前缀, 以示区分, 比如,1 表示退单号, 4代表订单号;后期视业务需要 可以将 b店 和C店 也区分出来. */
    private String prefix;

    /** 参照时间, 可以基于这个时间来生成时间段. */
    private Date   startDate;

    /** 截取用户id 后面位数. */
    private int    buyerIdLastLength  = 3;

    /** 截取shop id 后面位数. */
    private int    shopIdLastLength   = 3;

    /** 随机数位数. */
    private int    randomNumberLength = 2;

    /**
     * Instantiates a new code type.
     * 
     * @param prefix
     *            the prefix
     * @param startDate
     *            the start date
     * @param buyerIdLastLength
     *            the buyer id last length
     * @param shopIdLastLength
     *            the shop id last length
     * @param randomNumberLength
     *            the random number length
     */
    private CodeType(String prefix, Date startDate, int buyerIdLastLength, int shopIdLastLength, int randomNumberLength){
        this.prefix = prefix;
        this.startDate = startDate;
        this.buyerIdLastLength = buyerIdLastLength;
        this.shopIdLastLength = shopIdLastLength;
        this.randomNumberLength = randomNumberLength;
    }

    /**
     * Gets the 前缀, 以示区分, 比如,1 表示退单号, 4代表订单号;后期视业务需要 可以将 b店 和C店 也区分出来.
     * 
     * @return the 前缀, 以示区分, 比如,1 表示退单号, 4代表订单号;后期视业务需要 可以将 b店 和C店 也区分出来
     */
    public String getPrefix(){
        return prefix;
    }

    /**
     * Gets the 参照时间, 可以基于这个时间来生成时间段.
     * 
     * @return the 参照时间, 可以基于这个时间来生成时间段
     */
    public Date getStartDate(){
        return startDate;
    }

    /**
     * Gets the 截取用户id 后面位数.
     * 
     * @return the buyerIdLastLength
     */
    public int getBuyerIdLastLength(){
        return buyerIdLastLength;
    }

    /**
     * Gets the 截取shop id 后面位数.
     * 
     * @return the shopIdLastLength
     */
    public int getShopIdLastLength(){
        return shopIdLastLength;
    }

    /**
     * Gets the 随机数位数.
     * 
     * @return the randomNumberLength
     */
    public int getRandomNumberLength(){
        return randomNumberLength;
    }

    /**
     * Sets the 前缀, 以示区分, 比如,1 表示退单号, 4代表订单号;后期视业务需要 可以将 b店 和C店 也区分出来.
     * 
     * @param prefix
     *            the new 前缀, 以示区分, 比如,1 表示退单号, 4代表订单号;后期视业务需要 可以将 b店 和C店 也区分出来
     */
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    /**
     * Sets the 参照时间, 可以基于这个时间来生成时间段.
     * 
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    /**
     * Sets the 截取用户id 后面位数.
     * 
     * @param buyerIdLastLength
     *            the buyerIdLastLength to set
     */
    public void setBuyerIdLastLength(int buyerIdLastLength){
        this.buyerIdLastLength = buyerIdLastLength;
    }

    /**
     * Sets the 截取shop id 后面位数.
     * 
     * @param shopIdLastLength
     *            the shopIdLastLength to set
     */
    public void setShopIdLastLength(int shopIdLastLength){
        this.shopIdLastLength = shopIdLastLength;
    }

    /**
     * Sets the 随机数位数.
     * 
     * @param randomNumberLength
     *            the randomNumberLength to set
     */
    public void setRandomNumberLength(int randomNumberLength){
        this.randomNumberLength = randomNumberLength;
    }
}
