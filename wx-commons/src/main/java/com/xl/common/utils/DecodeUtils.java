package com.xl.common.utils;


public class DecodeUtils {
	public static String decord(String str){
		if(StringUtils.isNotBlank(str)){
			str=str.replace("img 360chrome_form_autofill", "img autofill");
			return str.replace("&amp;","&");
			
		}else{
			return "";
		}
	}
}
