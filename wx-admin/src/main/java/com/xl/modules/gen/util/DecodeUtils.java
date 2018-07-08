package com.xl.modules.gen.util;

import com.soecode.wxtools.util.StringUtils;

public class DecodeUtils {
	public static String decord(String str){
		if(StringUtils.isNotBlank(str)){
			return str.replace("&amp;","&");
		}else{
			return "";
		}
	}
}
