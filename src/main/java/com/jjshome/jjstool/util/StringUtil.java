package com.jjshome.jjstool.util;

/**
 * 字符串工具类
 * 
 * @author Administrator
 * 
 */
public class StringUtil {

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String toLowerForFirstChar(String str) {
		if (isEmpty(str)) {
			return str;
		}
		str = str.substring(0, 1).toLowerCase() + str.substring(1);
		return str;
	}
	
	public static String toUpperForFirstChar(String str) {
		if (isEmpty(str)) {
			return str;
		}
		str = str.substring(0, 1).toUpperCase() + str.substring(1);
		return str;
	}
	
	public static void main(String[] args){
		System.out.println(toLowerForFirstChar("TdfkdkI"));
		System.out.println(toUpperForFirstChar("idfkdkI"));
		
		String a = "D:/java/test.java";
		System.out.println(a.substring(a.lastIndexOf("/")+1));
		System.out.println(a.substring(0,a.lastIndexOf("/")+1));
	}

}
