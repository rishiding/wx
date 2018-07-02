//package com.xl.modules.wx.service;
//
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import com.soecode.wxtools.api.IService;
//import com.soecode.wxtools.api.WxService;
//import com.xl.common.config.Global;
//import com.xl.common.mapper.JsonMapper;
//import com.xl.common.service.BaseService;
//import com.xl.common.utils.HttpUtils;
//import com.xl.modules.wx.vo.AccessToken;
//
///**
// * 二维码服务
// * @author Administrator
// *
// */
//@Service
//@Transactional(readOnly = true)
//public class QrCodeService extends BaseService{
//	private static String basePath = Global.USERFILES_BASE_URL+"wx";
//	private static String APPID="wx977ba15cb305ce5f";
//	private static String APPSECRET="69dceaa3dc9ca4859c5bca5545987b5d";
//	public static void main(String args[]){
//		QrCodeService qr=new QrCodeService();
////		qr.getAccesstoken();
//		System.out.println(qr.getminiqrQr("hospitalId", "1"));
//	}
//	
//	public String getAccesstoken(){
//		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
//		String out=HttpUtils.doPost(url, "");
//		logger.debug(out);
//		AccessToken t=(AccessToken)JsonMapper.fromJsonString(out, AccessToken.class);
//		if(t==null||StringUtils.isBlank(t.getAccessToken())){
//			return null;
//		}
//		return  t.getAccessToken();
//	}
//	/**
//	 * 生成二维码图片
//	 * @param sceneStr
//	 * @param accessToken
//	 * @param hospitalId
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public String getminiqrQr(String sceneStr, String hospitalId) {
//        RestTemplate rest = new RestTemplate();
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        IService iService = new WxService();
//        try {
//        	String accessToken=getAccesstoken();
////        	String accessToken=iService.getAccessToken();//getAccesstoken();
//            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
//            String savePath = Global.getUserfilesBaseDir()+ basePath + "/";
//			String saveUrl =  basePath + "/";
//            Map<String,Object> param = new HashMap<>();
//            param.put("scene", sceneStr);
//            param.put("page", "pages/index/index");
//            param.put("width", 430);
//            param.put("auto_color", true);
//            param.put("hospitalId", hospitalId);
////            param.put("is_hyaline", true);
//          /*  Map<String,Object> line_color = new HashMap<>();
//            line_color.put("r", 0);
//            line_color.put("g", 0);
//            line_color.put("b", 0);
//            param.put("line_color", line_color);*/
//            logger.info("调用生成微信URL接口传参:" + param);
//            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
//            HttpEntity requestEntity = new HttpEntity(param, headers);
//          
//            ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
//            logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
//            byte[] result = entity.getBody();
//            logger.info(Base64.getEncoder().encodeToString(result));
//            inputStream = new ByteArrayInputStream(result);
//            File file = new File(savePath);  
//			if(!file.exists()){  
//			    file.mkdirs();  
//			} 
//            savePath=savePath+hospitalId+".png";
//            saveUrl=saveUrl+hospitalId+".png";
//            System.out.println(savePath);
//            outputStream = new FileOutputStream(savePath);
//           
//            int len = 0;
//            byte[] buf = new byte[1024];
//            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
//                outputStream.write(buf, 0, len);
//            }
//            outputStream.flush();
//            return saveUrl;
//        } catch (Exception e) {
//            logger.error("调用小程序生成微信永久小程序码URL接口异常",e);
//        } finally {
//            if(inputStream != null){
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(outputStream != null){
//                try {
//                    outputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;    
//    }
//}
