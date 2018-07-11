/**
 * Copyright &copy; 2017-2017<a href="#">rishi</a> All rights reserved.
 */
package com.xl.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xl.common.config.Global;
import com.xl.common.config.ResponseCodeCanstants;
import com.xl.common.config.ResponseResult;
import com.xl.common.persistence.Page;
import com.xl.common.service.BaseService;
import com.xl.common.utils.StringUtils;
import com.xl.common.web.BaseController;
import com.xl.modules.sys.entity.Office;
import com.xl.modules.sys.entity.User;
import com.xl.modules.sys.service.OfficeService;
import com.xl.modules.sys.utils.DictUtils;
import com.xl.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@ModelAttribute("office")
	public Office get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return officeService.get(id);
		} else {
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "deptList" })
	public String deptList(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (office == null) {
			office = new Office();
		}
		office.setType("2");
		User user = UserUtils.getUser();
		if (!user.isAdmin()) {
			office.setParent(user.getCompany());
		}
		office.getSqlMap().put("dsf", BaseService.dataScopeFilter(UserUtils.getUser(), "a", ""));
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office);
		model.addAttribute("page", page);
		return "modules/sys/deptList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "deptForm")
	public String deptForm(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent() == null || office.getParent().getId() == null) {
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea() == null) {
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId()) && office.getParent() != null) {
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i = 0; i < list.size(); i++) {
				Office e = list.get(i);
				if (e.getParent() != null && e.getParent().getId() != null
						&& e.getParent().getId().equals(office.getParent().getId())) {
					size++;
				}
			}
			office.setCode(office.getParent().getCode()
					+ StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/deptForm";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "list" })
	public String list(Office office, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (office == null) {
			office = new Office();
		}
		office.setType("1");
		Page<Office> page = officeService.findPage(new Page<Office>(request, response), office);
		model.addAttribute("page", page);
		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent() == null || office.getParent().getId() == null) {
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea() == null) {
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId()) && office.getParent() != null) {
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i = 0; i < list.size(); i++) {
				Office e = list.get(i);
				if (e.getParent() != null && e.getParent().getId() != null
						&& e.getParent().getId().equals(office.getParent().getId())) {
					size++;
				}
			}
			office.setCode(office.getParent().getCode()
					+ StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)) {
			return form(office, model);
		}
		officeService.save(office);

		if (office.getChildDeptList() != null) {
			Office childOffice = null;
			for (String id : office.getChildDeptList()) {
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade()) + 1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}

		addMessage(redirectAttributes, "保存'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		if (office.getType().equals("2")) {
			return "redirect:" + adminPath + "/sys/office/deptList?id=" + id + "&parentIds=" + office.getParentIds();
		} else {
			return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIds=" + office.getParentIds();
		}
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
		// if (Office.isRoot(id)){
		// addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
		// }else{
		officeService.delete(office);
		addMessage(redirectAttributes, "删除机构成功");
		// }
		if (office.getType().equals("2")) {
			return "redirect:" + adminPath + "/sys/office/deptList?id=" + office.getParentId() + "&parentIds="
					+ office.getParentIds();
		} else {
			return "redirect:" + adminPath + "/sys/office/list?id=" + office.getParentId() + "&parentIds="
					+ office.getParentIds();
		}
	}

	/**
	 * 获取机构JSON数据。
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			@RequestParam(required = false) String type, @RequestParam(required = false) Long grade,
			@RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId)
					|| (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1))
					&& (type == null || (type != null && type.equals(e.getType())))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)) {
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 * 用户信息显示及保存
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(Office office, HttpServletResponse response, Model model) {
		Office hospital = officeService.get(UserUtils.getUser().getCompany());
		if (StringUtils.isNotBlank(office.getName())) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/officeInfo";
			}
			hospital.setGrade(office.getGrade());
			hospital.setAddress(office.getAddress());
			hospital.setZipCode(office.getZipCode());
			hospital.setMaster(office.getMaster());
			hospital.setPhone(office.getPhone());
			hospital.setFax(office.getFax());
			hospital.setEmail(office.getEmail());
			hospital.setRemarks(office.getRemarks());
			hospital.setLogo(office.getLogo());
			hospital.setBanner(office.getBanner());
			hospital.setLotlat(office.getLotlat());

			officeService.updateCompanyInfo(hospital);

			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("office", hospital);
		return "modules/sys/officeInfo";
	}

	

	@ResponseBody
	@RequestMapping(value = "findInhospDept")
	public Object findInhospDept(Office office) {

		try {
			return new ResponseResult(ResponseCodeCanstants.SUCCESS, officeService.findInhospDept(office), "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseResult(ResponseCodeCanstants.SUCCESS, "操作失败");
	}
}
