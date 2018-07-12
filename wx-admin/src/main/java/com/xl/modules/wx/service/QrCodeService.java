package com.xl.modules.wx.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;
import com.xl.common.config.Global;
import com.xl.common.service.BaseService;

/**
 * 二维码服务
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly = true)
public class QrCodeService extends BaseService{
	private static String basePath = Global.USERFILES_BASE_URL+"wx";	
	
/*	public static void main(String args[]){
		QrCodeService s=new QrCodeService();
		s.getminiqrQr("1");
		
		
	}*/
	
 
	/**
	 * 生成二维码图片
	 * @param sceneStr
	 * @param accessToken
	 * @param hospitalId
	 * @return
	 */
	public String getminiqrQr( String hospitalId) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        IService iService = new WxService();
        try {
        	String accessToken=iService.getAccessToken();
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
            String savePath = Global.getUserfilesBaseDir()+ basePath + "/";
			String saveUrl =  basePath + "/";
            Map<String,Object> param = new HashMap<>();
            param.put("scene", hospitalId);
            param.put("page", "pages/home/pages/index/index");
            param.put("width", 430);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            logger.debug("调用生成微信URL接口传参:" + param);
            CloseableHttpClient  httpClient = HttpClientBuilder.create().build();

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String body = JSON.toJSONString(param);
            StringEntity entity;
            entity = new StringEntity(body);
            entity.setContentType("image/png");

            httpPost.setEntity(entity);
            HttpResponse response;

            response = httpClient.execute(httpPost);
            inputStream = response.getEntity().getContent();
            
           
            File file = new File(savePath);  
			if(!file.exists()){  
			    file.mkdirs();  
			} 
            savePath=savePath+hospitalId+".png";
            saveUrl=saveUrl+hospitalId+".png";
            System.out.println(savePath);
            outputStream = new FileOutputStream(savePath);
           
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            return saveUrl;
        } catch (Exception e) {
            logger.error("调用小程序生成微信永久小程序码URL接口异常",e);
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;    
    }
}
