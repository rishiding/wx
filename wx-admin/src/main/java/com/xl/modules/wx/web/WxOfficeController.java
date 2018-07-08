package com.xl.modules.wx.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.soecode.wxtools.util.StringUtils;
import com.xl.common.config.Global;
import com.xl.common.config.ResponseCodeCanstants;
import com.xl.common.config.ResponseResult;
import com.xl.common.persistence.Page;
import com.xl.common.web.BaseController;
import com.xl.modules.gen.util.DecodeUtils;
import com.xl.modules.sys.entity.Office;
import com.xl.modules.sys.entity.User;
import com.xl.modules.sys.service.OfficeService;
import com.xl.modules.sys.service.SystemService;
import com.xl.modules.wx.vo.Banner;

@Controller
@RequestMapping(value = "${frontPath}/api/office")
public class WxOfficeController extends BaseController{
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService userService;
	/**
	 * 获取医院
	 * @param hospitalid
	 * @param wxuserid
	 * @return
	 */
	
	@ResponseBody 
	@RequestMapping(value = "getBanners")  
	public Object getBanners(String hospitalid,String wxuserid,HttpServletRequest request) {  
		Office hospital=null;
		if(StringUtils.isNotBlank(hospitalid)){
			hospital=officeService.get(hospitalid);
		}
		if(hospital==null&&StringUtils.isNotBlank(wxuserid)){
			hospital=officeService.getByWxUserId(wxuserid);
		}
		if(hospital==null){
			return new ResponseResult(ResponseCodeCanstants.FAILED, "参数异常"); 
		}else{
			String arr[]=hospital.getBanner().split("\\|");
			List<Banner> list=Lists.newArrayList();
			Arrays.asList(arr).forEach((a)->{if(StringUtils.isNotBlank(a))list.add(new Banner("",a));});
			return new ResponseResult(ResponseCodeCanstants.SUCCESS,list , "操作成功");  
		}
		
	}
	
	/**
	 * 获取医院
	 * @param hospitalid
	 * @param wxuserid
	 * @return
	 */
	
	@ResponseBody 
	@RequestMapping(value = "getHospital")  
	public Object getHospital(String hospitalid,String wxuserid) {  
		Office hospital=null;
		if(StringUtils.isNotBlank(hospitalid)){
			hospital=officeService.get(hospitalid);
		}
		if(hospital==null&&StringUtils.isNotBlank(wxuserid)){
			hospital=officeService.getByWxUserId(wxuserid);
		}
		
		if(hospital==null){
			return new ResponseResult(ResponseCodeCanstants.FAILED, "参数异常"); 
		}else{
			if(StringUtils.isNotBlank(hospital.getRemarks())){
				hospital.setRemarks(DecodeUtils.decord(hospital.getRemarks()));
			}
			return new ResponseResult(ResponseCodeCanstants.SUCCESS, hospital, "操作成功");  
		}
		
	}
	/**
	 * 获取专家
	 * @param hospitalid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDoctor")
	public Object getDoctor(HttpServletRequest request, HttpServletResponse response,@RequestParam(required=true) String hospitalid,@RequestParam(required=false) String deptid) {
		User user=new User();
		user.setUserType("2");//医生为2
		user.setCompany(new Office(hospitalid));
		if(StringUtils.isNotBlank(deptid)){
			user.setOffice(new Office(deptid));			
		}
		Page<User> page=userService.findUser(new Page<User> (request, response), user);
		return new ResponseResult(ResponseCodeCanstants.SUCCESS,page, "成功");
	}
	/**
	 * 获取科室
	 * @param hospitalid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDept")
	public List<Map<String, Object>> getDept(@RequestParam(required=true) String hospitalid) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(true);
		String type="2";
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ( (e.getParentIds().indexOf(","+hospitalid+",")>=0)
					&& (type == null || (type != null &&  type.equals(e.getType()) ))					
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
}
