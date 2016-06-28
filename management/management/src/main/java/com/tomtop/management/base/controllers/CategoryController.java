package com.tomtop.management.base.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.services.CategoryService;

@Controller
@RequestMapping("/base/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	/**
	 * 
	 * @Title: getCategoryByParentId
	 * @Description: TODO(通过父id查询品类)
	 * @param @param parentId
	 * @param @return    
	 * @return List<Category>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/getCategoryByPId/{parentId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Category> getCategoryByParentId(@PathVariable(value = "parentId") Integer parentId) {
		return categoryService.getCategoryByParentId(parentId);
	}
	
	/**
	 * 
	 * @Title: getCategoryById
	 * @Description: TODO(通过id查询品类)
	 * @param @param id
	 * @param @return    
	 * @return Category    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月16日
	 */
	@RequestMapping(value = "/getCategory/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Category getCategoryById(@PathVariable(value = "id") Integer id) {
		return categoryService.getCategoryById(id);
	}
}
