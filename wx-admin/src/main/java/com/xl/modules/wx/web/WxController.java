package com.xl.modules.wx.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import com.xl.common.web.BaseController;
@Controller
@RequestMapping(value = "${frontPath}/wx")
public class WxController  extends BaseController{
	 private IService iService = new WxService();

	    @GetMapping
	    @ResponseBody
	    public String check(String signature, String timestamp, String nonce, String echostr) {
	        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
	            return echostr;
	        }
	        return null;
	    }
	    
	    @PostMapping
	    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        request.setCharacterEncoding("UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();

	        try {
	            // 微信服务器推送过来的是XML格式。
	            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
	            System.out.println("消息：\n " + wx.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            out.close();
	        }

	    }
}
