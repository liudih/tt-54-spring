package com.tomtop.management.homepage.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.homepage.model.NewestImage;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.homepage.services.NewestImgService;
import com.tomtop.management.service.model.NewestImageObject;
import com.tomtop.management.services.CommonService;

/**
 * 
    * @ClassName: NewestImageController
    * @Description: 最新晒图管理
    * @author liuxin
    * @date 2015年12月29日
    *
 */
@Controller
@RequestMapping("/homepage/newestimg")
public class NewestImageController {

	@Autowired
	CommonService commonService;

	@Autowired
	NewestImgService imgService;
	
	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Language> languageList = commonService.getAllLanguage();
		List<Country> countries = commonService.getAllCountry();
		model.put("clientList", clientList);
		model.put("languageList", languageList);
		model.put("countries", countries);
		model.put("section", "homemodul/newimg.ftl");
		return "index";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Page<NewestImageObject> getCliPage(int pageNo, int pageLimit,
			NewestImage img,String clients,String languages) {
		Page<NewestImageObject> pagel = imgService.getKeyPage(pageNo,
				pageLimit, img,clients,languages);
		return pagel;
	}

	@RequestMapping("/add")
	@ResponseBody
	public boolean add(NewestImage img) {
		boolean result = true;
		int count = imgService.addKeyWord(img);
		if (count <= 0) {
			result = false;
		}else{
			eventBus.post(new ProductCacheEvent("customers_voices", EventType.Add));
		}
		return result;
	}

	@RequestMapping("/update")
	@ResponseBody
	public boolean update(NewestImage img) {
		boolean result = true;
		int count = imgService.updateSite(img);
		if (count <= 0) {
			result = false;
		}else{
			eventBus.post(new ProductCacheEvent("customers_voices", EventType.Update));
		}
		return result;
	}

	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public NewestImage getById(@PathVariable(value = "id") int id) {
		return NewestImage.db().find(NewestImage.class, id);
	}

	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean delete(@PathVariable(value = "delIds") String delIds) {
		NewestImage img = null;
		int count = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			img = NewestImage.db().find(NewestImage.class, ids[i]);
			img.setIs_deleted(1);
			NewestImage.db().update(img);
			count++;
		}
		if (count > 0) {
			eventBus.post(new ProductCacheEvent("customers_voices", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
