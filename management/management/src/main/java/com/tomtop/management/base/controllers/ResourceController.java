package com.tomtop.management.base.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Resource;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.ResourceObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ResourceService;

@Controller
@RequestMapping("/base/resource")
public class ResourceController {

	@Autowired
	CommonService commonService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	private EventBus eventBus;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(资源信息页面显示)
	 * @param @param model
	 * @param @param section
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		model.addAttribute("clientList", clientList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("section", "baseModul/resourceManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getResourcePage
	 * @Description: TODO(分页查询资源信息)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param r
	 * @param @return    
	 * @return Page<ResourceObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Page<ResourceObject> getResourcePage(int pageNo, int pageLimit, Resource r, String clients, String languages) {
		return resourceService.getResourcePage(pageNo, pageLimit, r , clients, languages);
	}

	/**
	 * 
	 * @Title: addResource
	 * @Description: TODO(新增资源信息)
	 * @param @param r
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addResource(Resource r, String clients, String languages) {
		int addCount = resourceService.addResource(r, clients, languages);
		if (addCount > 0) {
			eventBus.post(new BaseCacheEvent("resource", EventType.Add));
			return "success";
		} else {
			return "failed";
		}
	}

	/**
	 * 
	 * @Title: getResourceById
	 * @Description: TODO(通过id获取资源信息)
	 * @param @param id
	 * @param @return    
	 * @return Resource    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Resource getResourceById(@PathVariable(value = "id") Integer id) {
		return resourceService.getResourceById(id);
	}

	/**
	 * 
	 * @Title: updateResource
	 * @Description: TODO(修改资源信息)
	 * @param @param r
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateResource(Resource r) {
		int updateCount = resourceService.updateResource(r);
		if (updateCount > 0) {
			eventBus.post(new BaseCacheEvent("resource", EventType.Update));
			return "success";
		} else {
			return "failed";
		}
	}

	/**
	 * 
	 * @Title: deleteResource
	 * @Description: TODO(删除资源信息)
	 * @param @param Ids
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/delete/{Ids}", method = RequestMethod.GET)
	@ResponseBody
	public String deleteResource(@PathVariable(value = "Ids") String Ids) {
		int deleteCount = resourceService.deleteResource(Ids);
		if (deleteCount > 0) {
			eventBus.post(new BaseCacheEvent("resource", EventType.Delete));
			return "success";
		} else {
			return "fail";
		}
	}

	/**
	 * 
	 * @Title: resourceKeyUniqueValidate
	 * @Description: TODO(校验资源标识是否重复)
	 * @param @param id
	 * @param @param resourceKey
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/resourceKeyUV", method = RequestMethod.POST)
	@ResponseBody
	public String resourceKeyUniqueValidate(Integer id, String resourceKey) {
		int findCount = resourceService.resourceKeyUniqueValidate(id, resourceKey);
		if (findCount > 0) {
			return "false";
		} else {
			return "true";
		}
	}

	/**
	 * 
	 * @Title: download
	 * @Description: TODO(下载资源)
	 * @param @param res
	 * @param @param fileType
	 * @param @param r
	 * @param @throws IOException    
	 * @return void    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse res, Integer fileType, Resource r, String clients, String languages) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		OutputStream os = res.getOutputStream();
		try {
			String fileName = null;
			String fileString = null;
			if (fileType == 0) {
				fileName = "resource_android" + sdf.format(new Date()) + ".xml";
				fileString = resourceService.getXMLString(r, clients, languages);
			} else {
				fileName = "resource_ISO" + sdf.format(new Date()) + ".txt";
				fileString = resourceService.getTXTString(r, clients, languages);
			}
			res.reset();
			res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			res.setContentType("application/octet-stream; charset=utf-8");
			byte[] b = fileString.getBytes("UTF-8");
			os.write(b);
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * 
	 * @Title: resourceUniqueValidate
	 * @Description: TODO(资源信息唯一校验)
	 * @param @param r
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月21日
	 */
	@RequestMapping(value = "/resourceUV", method = RequestMethod.POST)
	@ResponseBody
	public boolean resourceUniqueValidate(Resource r, String clients, String languages){
		return resourceService.resourceUniqueValidate(r, clients, languages);
	}
}
