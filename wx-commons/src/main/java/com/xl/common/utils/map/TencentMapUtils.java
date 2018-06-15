package com.xl.common.utils.map;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.common.utils.HttpUtils;
import com.xl.common.utils.IpAdressUtil;

/**
 * 腾讯地图工具类
 * @author rishi
 *
 */
public class TencentMapUtils {
	private static final String ADRESS_URL="http://apis.map.qq.com/ws/geocoder/v1/?address=";  //地址转换接口
	private static final String TRANSLATE_URL="http://apis.map.qq.com/ws/coord/v1/translate";  //坐标转换接口
	private static final String IP_URL="http://apis.map.qq.com/ws/location/v1/ip";//ip 定位
	private static final String DRIVING_URL="http://apis.map.qq.com/ws/direction/v1/driving/";//路线规划地址
	private static final String KEY="TXFBZ-OD6KD-PFL4C-HIWU4-45PV2-NAFKX";
	private static final String OUTPUT="json";
	
	private static final String SUCCESS="0";
	
	/**
	 * IP定位方法
	 * @param ip
	 */
	public static String ipLocation(String ip){
		String url=IP_URL+"?ip="+ip+"&key="+KEY;
		String out=HttpUtils.doGet(url);
		return stringToloc(out);
		
	}
	public static String addressToLoc(String address){
		if(address==null||address.equals(""))
			address="成都市";
		String url=ADRESS_URL+address+"&key="+KEY;
		String out=HttpUtils.doGet(url);
		
		return stringToloc(out);
		
	}
	private static String stringToloc(String str){
		
		JSONObject obj=JSON.parseObject(str);
		String outstr="";
		String status=obj.getString("status");
		if(status!=null&&status.equals("0")){
			JSONObject result=obj.getJSONObject("result");
			JSONObject loc=result.getJSONObject("location");
			
			outstr=loc.getString("lng")+","+loc.getString("lat");;
		}else{
			System.out.println(status+obj.get("message"));
		}
		
		return outstr;
	}
	/**
	 * 
	 * 104.066541,30.572269
	 * 104.06476,30.5702
	 * @param args
	 */
	/*public static void main(String args[]){
		System.out.println(TencentMapUtils.ipLocation("182.139.87.103"));
		System.out.println(TencentMapUtils.addressToLoc("成都市武侯区"));
	}*/
}
