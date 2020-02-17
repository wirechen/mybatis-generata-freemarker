package com.jjshome.jjstool.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String getCurrentTimeStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");//设置日期格式
		return df.format(new Date());
	}
	


	public static void main(String[] args){
		System.out.println(getCurrentTimeStr());
	}

}
