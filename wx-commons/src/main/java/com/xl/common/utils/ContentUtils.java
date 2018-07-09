package com.xl.common.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xl.common.config.Global;

/**
 * 富文本处理工具类
 * @author Administrator
 *
 */
public class ContentUtils {
	public static String getContent(String content) throws Exception{
		content=DecodeUtils.decord(content);
		 Element doc = Jsoup.parseBodyFragment(content).body();
	        Elements pngs = doc.select("img[src]");
	        String httpHost = Global.getServerPath();
	        for (Element element : pngs) {
	            String imgUrl = element.attr("src").trim();
	            if (imgUrl.startsWith(Global.USERFILES_BASE_URL)) { // 会去匹配我们富文本的图片的 src 的相对路径的首个字符，请注意一下
	                imgUrl =httpHost + imgUrl;
	                element.attr("src", imgUrl);
	            }else{
	            	if(!imgUrl.startsWith(httpHost)){
	            		imgUrl=ImageUtils.downloadPicture(imgUrl);
	            		element.attr("src", imgUrl);
	            	}
	            }
	        }
	      return doc.toString();
	}
}
