/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.xl.modules.cms.web;

import java.util.List;

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

import com.xl.common.mapper.JsonMapper;
import com.xl.common.persistence.Page;
import com.xl.common.utils.StringUtils;
import com.xl.common.web.BaseController;
import com.xl.modules.cms.entity.Article;
import com.xl.modules.cms.entity.Category;
import com.xl.modules.cms.service.ArticleDataService;
import com.xl.modules.cms.service.ArticleService;
import com.xl.modules.cms.service.CategoryService;
import com.xl.modules.sys.utils.UserUtils;

/**
 * 文章Controller
 * @author 
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
	@Autowired
	private CategoryService categoryService;
  
	
	@ModelAttribute
	public Article get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return articleService.get(id);
		}else{
			return new Article();
		}
	}
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = { ""})
	public String index(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {

       
		return "modules/cms/articleIndex";
	}
	
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = {"list"})
	public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {

        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true); 
        model.addAttribute("page", page);
		return "modules/cms/articleList";
	}

	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "form")
	public String form(Article article, Model model) {
		// 如果当前传参有子节点，则选择取消传参选择
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId())){
			List<Category> list = categoryService.findByParentId(article.getCategory().getId(), "");
			if (list.size() > 0){
				article.setCategory(null);
			}else{
				article.setCategory(categoryService.get(article.getCategory().getId()));
			}
		}
		article.setArticleData(articleDataService.get(article.getId()));

		model.addAttribute("article", article);
		
		return "modules/cms/articleForm";
	}

	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "save")
	public String save(Article article, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, article)){
			return form(article, model);
		}
		if(StringUtils.isBlank(article.getCompanyId())){
			article.setCompanyId(UserUtils.getUser().getCompany().getId());
		}
		articleService.save(article);
		addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(),50) + "'成功");
		String message= "保存文章'" + StringUtils.abbr(article.getTitle(),50) + "'成功";
		return "redirect:" + adminPath + "/cms/article/list?message="+message;
	}
	
	@RequiresPermissions("cms:article:edit")
	@RequestMapping(value = "delete")
	public String delete(Article article, String categoryId, @RequestParam(required=false) Boolean isRe, RedirectAttributes redirectAttributes) {
		// 如果没有审核权限，则不允许删除或发布。
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			addMessage(redirectAttributes, "你没有删除或发布权限");
		}
		articleService.delete(article, isRe);
		addMessage(redirectAttributes, "删除文章成功");
		return "redirect:" + adminPath + "/cms/article/list";
	}

	/**
	 * 文章选择列表
	 */
	@RequiresPermissions("cms:article:view")
	@RequestMapping(value = "selectList")
	public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
		return "modules/cms/articleSelectList";
	}
	
	/**
	 * 通过编号获取文章标题
	 */
	@RequiresPermissions("cms:article:view")
	@ResponseBody
	@RequestMapping(value = "findByIds")
	public String findByIds(String ids) {
		List<Object[]> list = articleService.findByIds(ids);
		return JsonMapper.nonDefaultMapper().toJson(list);
	}

  
}
