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
package com.feilong.framework.netpay.payment.adaptor.sprintasia.klikpay.sprintutil;

import java.security.MessageDigest;

/**
 * The Class BCAKeyGenerator.
 * 
 * @author Brian
 */
public final class BCAKeyGenerator{

    /**
     * Signature.
     * 
     * @param klikPayCode
     *            the klik pay code
     * @param transactionDate
     *            the transaction date
     * @param transactionNo
     *            the transaction no
     * @param totalAmount
     *            the total amount
     * @param currency
     *            the currency
     * @param secretKey
     *            the secret key
     * @return the string
     * @throws Exception
     *             the exception
     */
    public static String signature(
                    String klikPayCode,
                    String transactionDate,
                    String transactionNo,
                    String totalAmount,
                    String currency,
                    String secretKey) throws Exception{
        if (secretKey.length() == 16){
            secretKey = AuthKey.KeyId(secretKey);
        }else if (secretKey.length() != 32){
            throw new Exception("Invalid keyId or clearKey (" + secretKey + ")");
        }
        if (!transactionDate.matches("^\\d\\d/\\d\\d/\\d{4} \\d\\d:\\d\\d:\\d\\d$")){
            throw new Exception("Invalid transactionDate Format (" + transactionDate + ")");
        }

        // First Value. Using klikPayCode, transactionNo, currency, secretKey.
        String tmpkey1 = "" + klikPayCode + transactionNo + currency + secretKey;

        // Second Value. Using transactionDate, totalAmount.
        String tmpkey2 = Integer.toString(Integer.parseInt(transactionDate.substring(0, 10).replaceAll("/", ""))
                        + new Double(totalAmount).intValue());

        // log.debug("tmpkey1: {}", tmpkey1);
        // log.debug("tmpkey2: {}", tmpkey2);
        return AuthKey.doHash(tmpkey1, tmpkey2);
    }

    /**
     * Auth key.
     * 
     * @param klikPayCode
     *            the klik pay code
     * @param transactionNo
     *            the transaction no
     * @param currency
     *            the currency
     * @param transactionDate
     *            the transaction date
     * @param secretKey
     *            the secret key
     * @return the string
     * @throws Exception
     *             the exception
     */
    public static String authKey(String klikPayCode,String transactionNo,String currency,String transactionDate,String secretKey)
                    throws Exception{
        if (secretKey.length() == 16){
            secretKey = AuthKey.KeyId(secretKey);
        }else if (secretKey.length() != 32){
            throw new Exception("Invalid keyId or clearKey (" + secretKey + ")");
        }
        if (!transactionDate.matches("^\\d\\d/\\d\\d/\\d{4} \\d\\d:\\d\\d:\\d\\d$")){
            throw new Exception("Invalid transactionDate Format (" + transactionDate + ")");
        }

        String padded = strpad(klikPayCode, 10, '0', false) + strpad(transactionNo, 18, 'A', false) + strpad(currency, 5, '1', false)
                        + strpad(transactionDate, 19, 'C', true) + secretKey;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(padded.getBytes());
        byte[] hbyte = md.digest();
        String md5 = AuthKey.toHexString(hbyte);
        return AuthKey.doAuthKey(md5, secretKey).toUpperCase();
    }

    /**
     * Key id.
     * 
     * @param clearKey
     *            the clear key
     * @return the string
     */
    public static String KeyId(String clearKey){
        char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        byte[] bytes = clearKey.getBytes();
        String keyId = "";
        for (int cntr = 0; cntr < clearKey.length(); cntr++){
            keyId = keyId + hexArray[(bytes[cntr] & 0xFF) / 16] + hexArray[(bytes[cntr] & 0xFF) % 16];
        }
        return keyId;
    }

    /**
     * Strpad.
     * 
     * @param sstring
     *            the sstring
     * @param len
     *            the len
     * @param padder
     *            the padder
     * @param PadRight
     *            the pad right
     * @return the string
     */
    public static String strpad(String sstring,Integer len,char padder,boolean PadRight){
        if (sstring.length() < 1){
            return "";
        }
        char[] c = sstring.toCharArray();
        if (c.length > len){
            return sstring;
        }
        String ret = "";
        if (PadRight){
            int indexlen = len - c.length;
            for (int i = 0; i < len; i++){
                ret += i < indexlen ? padder : c[i - indexlen];
            }
        }else{
            for (int i = 0; i < len; i++){
                ret += i < c.length ? c[i] : padder;
            }
        }
        return ret;
    }
}
