package com.xl.modules.sys.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xl.common.config.ResponseCodeCanstants;
import com.xl.common.config.ResponseResult;
import com.xl.common.utils.IdGen;
import com.xl.common.web.BaseController;

import sun.misc.BASE64Decoder;

@Controller
public class UploadifyController extends BaseController {
		private static final String URL="uploads";
		/**
		 * 文件上传
		 * @param request
		 * @param response
		 */
		@ResponseBody
		@RequestMapping(value = "/uploadify/uploadify")
	    public Object upload(HttpServletRequest request, HttpServletResponse response) {
//	        request = new MulpartRequestWrapper(request);
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
	        String returnUrl="/"+URL+"/";
	        String ctxPath=request.getSession().getServletContext().getRealPath("/")+URL+"/"; 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
	        String ymd = sdf.format(new Date());  
	        ctxPath += ymd + "/";  
	        returnUrl+=ymd+ "/";
	        //创建文件夹  
	            File file = new File(ctxPath);    
	            if (!file.exists()) {    
	                file.mkdirs();    
	            }    
	            String fileName = null;
	            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
	                // 上传文件名    
	                MultipartFile mf = entity.getValue();    
	                fileName = mf.getOriginalFilename();  
	              
	                
	                String uuid =IdGen.uuid();// 返回一个随机UUID。
	                String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;

	                String newFileName =  uuid + (suffix!=null?suffix:"");// 构成新文件名。   
	                File uploadFile = new File(ctxPath + newFileName);    
	                try {  
	                    FileCopyUtils.copy(mf.getBytes(), uploadFile); 
	                    returnUrl=returnUrl+newFileName;
	                   
		            } catch (Exception e) {  
		            	logger.error(e.getMessage());
		            	return new ResponseResult(ResponseCodeCanstants.FAILED, returnUrl);
		                
		            }    
	            }   
	            return new ResponseResult(ResponseCodeCanstants.SUCCESS, returnUrl);
	    }
		/**
		 * 二进制转图片
		 * @param byteString
		 * @return
		 */
		public static String saveByteImg(HttpServletRequest request,String byteString){
			BASE64Decoder decoder = new sun.misc.BASE64Decoder(); 
			
			String returnUrl=request.getContextPath()+"/"+URL+"/";
			try {   
				 
			        String ctxPath=request.getSession().getServletContext().getRealPath("/")+URL+"/"; 
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");  
			        String ymd = sdf.format(new Date());  
			        ctxPath += ymd + "/";  
			        returnUrl+=ymd+ "/";
			        //创建文件夹  
			            File file = new File(ctxPath);    
			            if (!file.exists()) {    
			                file.mkdirs();    
			            }    
			            String uuid =IdGen.uuid()+".";// 返回一个随机UUID。
		                String suffix ="jpg";
		                String newFileName =  uuid + (suffix!=null?suffix:"");// 构成新文件名。   
		                returnUrl=returnUrl+newFileName;
			            
	            byte[] bytes1 = decoder.decodeBuffer(byteString.split(",")[1]);   
	               
	            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);   
	            BufferedImage bi1 =ImageIO.read(bais);   
	            File w2 = new File(ctxPath + newFileName);//可以是jpg,png,gif格式   
	            ImageIO.write(bi1, suffix, w2);//不管输出什么格式图片，此处不需改动   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }   
			return returnUrl;
		}
		
		
}
