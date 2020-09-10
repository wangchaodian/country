package com.qa.util;


import java.security.MessageDigest;
import java.util.*;

public class SignUtils {
   public  static final String API_KEY="812f22b0b11976d0e15ccc05e4b355d09a5f6d59";

    public static void main(String[] args) {
        SortedMap<String,String> params=new TreeMap<String, String>();
        params.put("device","88ed4fe6-1310-42de-a99e-5eed17e0920a");
        params.put("timestamp","1562575291971");
//        long time=System.currentTimeMillis();
        String result=getSign(params);
        System.out.println(result);
        //60F314ACD76D38E29BD376DA9B13278D
        //60F314ACD76D38E29BD376DA9B13278D
    }


    /**
     * byteArrayToHexString锛氬瓧鑺傛暟缁勫埌鍗佸叚杩涘埗瀛楃涓�
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    /**
     * byteToHexString锛氬瓧鑺傚埌鍗佸叚杩涘埗瀛楃涓�
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * hexDigits锛氬崄鍏繘鍒舵暟瀛楁暟缁�
     */
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        try {
            byte[] btInput = s.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            byte[] md = mdInst.digest(btInput);
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSign(SortedMap<String, String> map) {
    	return getSign(map,API_KEY);
    }
    
    
    public static String getSign(SortedMap<String, String> map,String key) {
        StringBuffer sb = new StringBuffer();
        Set<Map.Entry<String,String>> es = map.entrySet();
        Iterator<Map.Entry<String,String>> it = es.iterator();
        while (it.hasNext()) {
            Map.Entry<String,String> entry =  it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        sb.append("key=" + key);
        return MD5(sb.toString()).toUpperCase();
    }
}
