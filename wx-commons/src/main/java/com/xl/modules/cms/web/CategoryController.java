/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xl.modules.cms.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.xl.common.config.Constants;
import com.xl.common.config.Global;
import com.xl.common.utils.StringUtils;
import com.xl.common.web.BaseController;
import com.xl.modules.cms.entity.Category;
import com.xl.modules.cms.service.CategoryService;
import com.xl.modules.sys.utils.UserUtils;

/**
 * 栏目Controller
 * 
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/category")
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
   
	
	@ModelAttribute("category")
	public Category get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return categoryService.get(id);
		}else{
			return new Category();
		}
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Category> list = Lists.newArrayList();
		List<Category> sourcelist = categoryService.findByUser(true, null);
		Category.sortList(list, sourcelist, Constants.ROOT_ID);
        model.addAttribute("list", list);
		return "modules/cms/categoryList";
	}

	@RequiresPermissions("cms:category:view")
	@RequestMapping(value = "form")
	public String form(Category category, Model model) {
		if (category.getParent()==null||category.getParent().getId()==null){
			category.setParent(new Category(Constants.ROOT_ID));
		}
		Category parent = categoryService.get(category.getParent().getId());
		category.setParent(parent);
		if (category.getOffice()==null||category.getOffice().getId()==null){
			category.setOffice(UserUtils.getUser().getCompany()); //所属医院
		}

		model.addAttribute("category", category);
		return "modules/cms/categoryForm";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "save")
	public String save(Category category, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/category/";
		}
		if (!beanValidator(model, category)){
			return form(category, model);
		}
		categoryService.save(category);
		addMessage(redirectAttributes, "保存栏目'" + category.getName() + "'成功");
		return "redirect:" + adminPath + "/cms/category/list?repage";
	}
	
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "delete")
	public String delete(Category category, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/cms/category/list";
		}
		if (Category.isRoot(category.getId())){
			addMessage(redirectAttributes, "删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			categoryService.delete(category);
			addMessage(redirectAttributes, "删除栏目成功");
		}
		return "redirect:" + adminPath + "/cms/category/list?repage";
	}

	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("cms:category:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Category[] entitys = new Category[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = categoryService.get(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		categoryService.save(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:" + adminPath + "/cms/category/list?repage";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) String extId, HttpServletResponse response) {
		
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Category> list = categoryService.findByUser(true, module);
		for (int i=0; i<list.size(); i++){
			Category e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				map.put("module", e.getModule());
				mapList.add(map);
			}
		}
		return mapList;
	}

}
