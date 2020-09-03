package com.cm.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class CreateValidationUtils {

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getMachineId()
	{
		String result=getRandomString(5)+getRandomNumber(4);
		return result;
		
	}
	public static String getRandomNumber(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String Encrypt(String strSrc) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public static boolean isChinese(String str) {
		boolean result = false;
		for (int i = 0; i < str.length(); i++) {
			int chr1 = (char) str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result = true;
			}
		}
		return result;
	}
	public static void main(String args[])
	{
		for(int j=0;j<10;j++)
		{
			ArrayList<String> a = new ArrayList<String>();
			
			for(int i=0;i<10000;i++)
			{
				String re=getMachineId();
				if(a.contains(re))
				{
					System.out.println(">>>>>"+re);
				}else
				{
					System.out.println(re);
					a.add(re);
				}
			}
			System.out.println("test times " + j);
			System.out.println("length is " + a.size());
			System.out.println("random is " + getMachineId());
			
		}
		
		

	}

	public static String getAppToken() {
		String result=getRandomString(15);
		return result;
	}
	
}
