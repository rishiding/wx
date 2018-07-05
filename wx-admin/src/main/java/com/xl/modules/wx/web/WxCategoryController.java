package com.xl.modules.wx.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xl.common.config.Constants;
import com.xl.common.config.ResponseCodeCanstants;
import com.xl.common.config.ResponseResult;
import com.xl.common.persistence.Page;
import com.xl.common.web.BaseController;
import com.xl.modules.cms.entity.Article;
import com.xl.modules.cms.entity.Category;
import com.xl.modules.cms.service.ArticleDataService;
import com.xl.modules.cms.service.ArticleService;
import com.xl.modules.cms.service.CategoryService;
import com.xl.modules.sys.entity.Office;
@Controller
@RequestMapping(value = "${frontPath}/category")
public class WxCategoryController extends BaseController{
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDataService articleDataService;
/**
 * 获取文章列表
 * @param categoryId
 * @param hospitalid
 * @param request
 * @param response
 * @param model
 * @return
 */
	@ResponseBody 
	@RequestMapping(value = {"articleList"})
	public Object articleList(@RequestParam(required=true)String categoryId,String hospitalid, HttpServletRequest request, HttpServletResponse response, Model model) {
		Article article =new Article();
		article.setCategory(new Category(categoryId));
		article.setCompanyId(hospitalid);
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true); 
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page, "成功");
	}
	/**
	 * 获取文章详情
	 * @param id
	 * @param model
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "getArticle")
	public Object getArticle(@RequestParam(required=true)String id, Model model) {
		Article article=articleService.get(id);
		
		article.setArticleData(articleDataService.get(article.getId()));
		articleService.updateHitsAddOne(id);
		return new ResponseResult(ResponseCodeCanstants.SUCCESS, article, "操作成功");  
		
	}
	/**
	 * 获取栏目列表
	 * @param hospitalid
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = {"categoryList"})
	public Object categoryList(@RequestParam(required=true)String hospitalid) {
		List<Category> list = Lists.newArrayList();
		Category entity=new Category();
		entity.setOffice(new Office(hospitalid));
		List<Category> sourcelist = categoryService.findList(entity);
		if(sourcelist==null){
			return new ResponseResult(ResponseCodeCanstants.FAILED, "参数异常"); 
		}
		Category.sortList(list, sourcelist, Constants.ROOT_ID);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		for(Category o:list){
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", o.getId());
			map.put("name", o.getName());
			mapList.add(map);
		}
		return new ResponseResult(ResponseCodeCanstants.SUCCESS, mapList, "操作成功");  
		
	}

}
