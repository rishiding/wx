/**
 * Copyright &copy; 2017 <a href="#">xf</a> All rights reserved.
 */
package com.xl.modules.wxuser.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xl.common.config.Global;
import com.xl.common.persistence.Page;
import com.xl.common.web.BaseController;
import com.xl.common.utils.StringUtils;
import com.xl.modules.wxuser.entity.WxUser;
import com.xl.modules.wxuser.service.WxUserService;

/**
 * 微信用户管理Controller
 * @author dingrenxin
 * @version 2018-07-05
 */
@Controller
@RequestMapping(value = "${adminPath}/wxuser/wxUser")
public class WxUserController extends BaseController {

	@Autowired
	private WxUserService wxUserService;
	
	@ModelAttribute
	public WxUser get(@RequestParam(required=false) String id) {
		WxUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxUserService.get(id);
		}
		if (entity == null){
			entity = new WxUser();
		}
		return entity;
	}
	
	@RequiresPermissions("wxuser:wxUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(WxUser wxUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxUser> page = wxUserService.findPage(new Page<WxUser>(request, response), wxUser); 
		model.addAttribute("page", page);
		return "modules/wxuser/wxUserList";
	}

	@RequiresPermissions("wxuser:wxUser:view")
	@RequestMapping(value = "form")
	public String form(WxUser wxUser, Model model) {
		model.addAttribute("wxUser", wxUser);
		return "modules/wxuser/wxUserForm";
	}

	@RequiresPermissions("wxuser:wxUser:edit")
	@RequestMapping(value = "save")
	public String save(WxUser wxUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wxUser)){
			return form(wxUser, model);
		}
		wxUserService.save(wxUser);
		addMessage(redirectAttributes, "保存微信用户管理成功");
		return "redirect:"+Global.getAdminPath()+"/wxuser/wxUser/?repage";
	}
	
	@RequiresPermissions("wxuser:wxUser:edit")
	@RequestMapping(value = "delete")
	public String delete(WxUser wxUser, RedirectAttributes redirectAttributes) {
		wxUserService.delete(wxUser);
		addMessage(redirectAttributes, "删除微信用户管理成功");
		return "redirect:"+Global.getAdminPath()+"/wxuser/wxUser/?repage";
	}

}