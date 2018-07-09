package com.xl.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.xl.common.config.Global;
import com.xl.common.utils.IdGen;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片工具
 * 
 * @author 丁仁鑫
 *
 */
@SuppressWarnings("restriction")
public class ImageUtils {
	// 文件保存目录相对路径
	private static String basePath = Global.USERFILES_BASE_URL + "wx";
	private static String start = "data:image/";
	private static String end = ";base64,";
	
	/*public static void main(String args[]) throws Exception{
		System.out.println(ImageUtils.downloadPicture("http://www.cd120.com/thirdparty/ueditor/jsp/upload1/20180706/16771530872651760.png"));
	}*/

	// 链接url下载图片
	public static String downloadPicture(String urlList) throws Exception {
		URL url = new URL(urlList);
		DataInputStream dataInputStream = new DataInputStream(url.openStream());
		String ext = urlList.substring(urlList.lastIndexOf(".")+1);
		String savePath = Global.getUserfilesBaseDir() + basePath + "/";
		String saveUrl = basePath + "/";
		String ymd = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		ymd = sdf.format(new Date());
		String uuid = IdGen.uuid();
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		System.out.println(savePath);
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		savePath += uuid + "." + ext;
		saveUrl += uuid + "." + ext;
		FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath));
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int length;

		while ((length = dataInputStream.read(buffer)) > 0) {
			output.write(buffer, 0, length);
		}
//		byte[] context = output.toByteArray();
		fileOutputStream.write(output.toByteArray());
		dataInputStream.close();
		fileOutputStream.close();
		return Global.getServerPath() + saveUrl;
	}

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr
	 *            base64编码字符串 args[0] 文件路径 args[1] 文件名filename
	 * @return 图片路径
	 */
	public static String generateImage(HttpServletRequest request, String imgStr, String... args) {
		if (StringUtils.isBlank(imgStr)) {
			return null;
		}
		// imgStr=URLDecoder.decode(imgStr);
		String ext = imgStr.substring(start.length(), imgStr.indexOf(end));// 图片后缀
		String base64ImgData = imgStr.substring(imgStr.indexOf(end) + end.length());// 图片数据

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// 解密
			byte[] b = decoder.decodeBuffer(base64ImgData);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}

			String savePath = Global.getUserfilesBaseDir() + basePath + "/";
			String saveUrl = basePath + "/";
			String ymd = "";
			if (args.length > 0) {
				ymd = args[0];
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				ymd = sdf.format(new Date());
			}
			String uuid = "";
			if (args.length > 1) {
				uuid = args[1];
			} else {
				uuid = IdGen.uuid();
			}
			savePath += ymd + "/";
			saveUrl += ymd + "/";
			System.out.print(savePath);
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			savePath += uuid + "." + ext;
			saveUrl += uuid + "." + ext;
			OutputStream out = new FileOutputStream(savePath);
			out.write(b);
			out.flush();
			out.close();

			return saveUrl;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取签名信息
	 * 
	 * @param request
	 * @param imgStr
	 * @return
	 */
	public static String getSignature(HttpServletRequest request, String imgStr) {
		if (StringUtils.isBlank(imgStr)) {
			return null;
		}
		if (imgStr.startsWith(start)) {
			return generateImage(request, imgStr);
		}
		return imgStr;
	}

	/**
	 * 根据图片地址转换为base64编码字符串
	 * 
	 * @param imgFile
	 *            图片路径
	 * @return
	 */
	public static String getImageStr(String imgFile) {
		String path = Global.getUserfilesBaseDir() + imgFile;
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(path);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		// 加密
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * 获取图片高度
	 * 
	 * @param imgFile
	 * @return
	 * @throws IOException
	 */
	public static String getImageHeight(String imgFile) {
		double width = 5274310;
		int maxheight = 1300;
		String path = Global.getUserfilesBaseDir() + imgFile;
		InputStream is = null;
		double height = 0;
		try {
			File file = new File(path);
			is = new FileInputStream(file);
			BufferedImage srcImage = ImageIO.read(is);
			int srcImageHeight = srcImage.getHeight();
			if (srcImageHeight > maxheight) {
				srcImageHeight = maxheight;
			}
			int srcImageWidth = srcImage.getWidth();
			height = srcImageHeight * width / srcImageWidth;

			/*
			 * System.out.println(srcImageHeight); System.out.println(height);
			 * System.out.println(srcImageWidth);
			 */

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close(); // 关闭流
				} catch (IOException e) {
					System.err.println("getImageInfo I/O exception " + e.getMessage());
				}
			}
		}

		return height + "";
	}

}
