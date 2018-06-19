/**
 * Copyright &copy; 2017-2017<a href="#">rishi</a> All rights reserved.
 */
package com.xl.modules.sys.web;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xl.common.config.ResponseCodeCanstants;
import com.xl.common.web.BaseController;
import com.xl.common.config.ResponseResult;
import com.xl.modules.sys.entity.Office;
import com.xl.modules.sys.service.OfficeService;


/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${frontPath}/dept")
public class OfficeFrontController extends BaseController {

	@Autowired
	private OfficeService officeService;
	
	
	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：医院；2：科室/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDepts")
	public  Object getDepts() {
		Office obj=new Office();
		obj.setType("2");
		List<Office> list = officeService.findDeptList(obj);
		if(list!=null&&list.size()>0){
			return new ResponseResult(ResponseCodeCanstants.SUCCESS, list, "成功");
		}
		return new ResponseResult(ResponseCodeCanstants.FAILED, list, "没有数据");
		
	}
}
