package com.cjy.WechatHome.util;

import java.util.Arrays;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
public class CheckUtil {
	private static String token = "cjy";
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		//排序
		if(signature==null||timestamp==null|| nonce==null){
			return false;
		}
		String[] arr = new String[]{token,timestamp,nonce};
		Arrays.sort(arr);
		//生成新字符串
		StringBuffer content = new StringBuffer();
		for (String a : arr) {
			content.append(a);
		}
		String temp = getSha1((content.toString()));
		return temp.equals(signature);
	}
	public static String getSha1(String str){
	    if (null == str || 0 == str.length()){
	        return null;
	    }
	    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
	            'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	        MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
	        mdTemp.update(str.getBytes("UTF-8"));
	         
	        byte[] md = mdTemp.digest();
	        int j = md.length;
	        char[] buf = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = md[i];
	            buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
	            buf[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(buf);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
