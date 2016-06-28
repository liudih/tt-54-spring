package com.tomtop.management.base.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.ContentCatalogue;
import com.tomtop.management.ebean.manage.model.ContentDetails;
import com.tomtop.management.ebean.manage.model.ContentDetailsContext;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.ContentDetailsObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ContentService;
import com.tomtop.management.shipping.values.ValidCheckInfo;

@Controller
@RequestMapping("/base/cms/content")
public class ContentController {

	@Autowired
	CommonService commonService;
	@Autowired
	ContentService contentService;

	/**
	 * 
	 * @Title: view
	 * @Description: TODO(内容管理页面显示)
	 * @param @param model
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Model model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		model.addAttribute("clientList", clientList);
		model.addAttribute("languageList", languageList);
		model.addAttribute("section", "baseModul/contentManage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getContentCatalogueByClient
	 * @Description: TODO(通过客户端id查询内容目录)
	 * @param @param clientId
	 * @param @return    
	 * @return List<ContentCatalogue>    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/get/{clientId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ContentCatalogue> getContentCatalogueByClient(@PathVariable(value = "clientId") Long clientId) {
		return contentService.getContentCatalogueByClient(clientId);
	}

	/**
	 * 
	 * @Title: addContentCatalogue
	 * @Description: TODO(新增内容目录)
	 * @param @param cc
	 * @param @return    
	 * @return Long    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Long addContentCatalogue(ContentCatalogue cc) {
		Long result = null;
		Long id = contentService.addContentCatalogue(cc);
		if (id > 0) {
			result = id;
		} else {
			result = 0l;
		}
		return result;
	}

	/**
	 * 
	 * @Title: updateContentCatalogue
	 * @Description: TODO(修改内容目录)
	 * @param @param cc
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String updateContentCatalogue(ContentCatalogue cc) {
		String result = null;
		int updateCount = contentService.updateContentCatalogue(cc);
		if (updateCount == 1) {
			result = "succ";
		}
		if (updateCount == 0) {
			result = "fail";
		}
		return result;
	}

	/**
	 * 
	 * @Title: deleteContentCatalogue
	 * @Description: TODO(删除内容目录)
	 * @param @param catalogueId
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteContentCatalogue(@PathVariable(value = "id") Long catalogueId) {
		String result = null;
		int deleteCount = contentService.deleteContentCatalogue(catalogueId);
		if (deleteCount == 1) {
			result = "succ";
		}
		if (deleteCount == 0) {
			result = "fail";
		}
		return result;
	}

	/**
	 * 
	 * @Title: getContentDetailsByCatalogueId
	 * @Description: TODO(通过目录id查询内容详情)
	 * @param @param catalogueId
	 * @param @return    
	 * @return ContentDetailsObject    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/ContentDetails/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ContentDetailsObject getContentDetailsByCatalogueId(@PathVariable(value = "id") Long catalogueId) {
		return contentService.getContentDetailsByCatalogueId(catalogueId);
	}

	/**
	 * 
	 * @Title: getContentDetailsContext
	 * @Description: TODO(通过详情id和语言id查询详情内容)
	 * @param @param details_id
	 * @param @param language_id
	 * @param @return    
	 * @return ContentDetailsContext    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/ContentDetailsContext/get", method = RequestMethod.POST)
	@ResponseBody
	public ContentDetailsContext getContentDetailsContext(Long details_id, Long language_id) {
		return contentService.getContentDetailsContext(details_id, language_id);
	}

	/**
	 * 
	 * @Title: saveContentDetailsContext
	 * @Description: TODO(保存详情和详情内容)
	 * @param @param cd
	 * @param @param cdc
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月16日
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ValidCheckInfo saveContentDetailsContext(ContentDetails cd, ContentDetailsContext cdc) {
		return contentService.saveContentCatalogue(cd, cdc);
	}
	
	/**
	 * 
	 * @Title: getEnglishUrl
	 * @Description: TODO(获取English的内容链接)
	 * @param @param detailsId
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2016年4月12日
	 */
	@RequestMapping(value = "/ContentDetailsContext/getEnglishUrl/{did}", method = RequestMethod.GET)
	@ResponseBody
	public String getEnglishUrl(@PathVariable(value = "did")Long detailsId){
		return contentService.getEnglishUrl(detailsId);
	}
}
