package com.tomtop.management.base.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.eventbus.EventBus;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.PlatForm;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.service.model.BaseClientObject;
import com.tomtop.management.services.ClientService;
import com.tomtop.management.services.CommonService;

@Controller
@RequestMapping("/base/client")
public class ClientController {

	@Autowired
	CommonService commonService;

	@Autowired
	ClientService clientService;

	@Autowired
	private EventBus eventBus;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String view(Map<String, Object> model) {
		List<Client> clientList = commonService.getAllClient();
		List<Site> sites = commonService.getAllSite();
		List<PlatForm> parameters = commonService.getPlatFormParam();
		model.put("clientList", clientList);
		model.put("sites", sites);
		model.put("parameters", parameters);
		model.put("section", "baseModul/client_manage.ftl");
		return "index";
	}

	/**
	 * 
	 * @Title: getCliPage
	 * @Description: 查询信息
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            client
	 * @param @return
	 *            参数
	 * @return Page<BaseClientObject> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Page<BaseClientObject> getCliPage(int pageNo, int pageLimit, Client client) {
		Page<BaseClientObject> pagel = clientService.getCliPage(pageNo, pageLimit, client);
		return pagel;
	}

	/**
	 * 
	 * @Title: addClient
	 * @Description: 添加
	 * @param @param
	 *            client
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	@RequestMapping("/add")
	@ResponseBody
	public boolean addClient(Client client) {
		boolean result = true;
		int count = clientService.addClient(client);
		if (count <= 0) {
			result = false;
		} else {
			eventBus.post(new BaseCacheEvent("client", EventType.Add));
		}
		return result;
	}

	/**
	 * 
	 * @Title: updateClient
	 * @Description: 修改
	 * @param @param
	 *            client
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	@RequestMapping("/update")
	@ResponseBody
	public boolean updateClient(Client client) {
		boolean re = true;
		int count = clientService.updateClient(client);
		if (count <= 0) {
			re = false;
		} else {
			eventBus.post(new BaseCacheEvent("client", EventType.Update));
		}
		return re;
	}

	/**
	 * 
	 * @Title: getClientById
	 * @Description: 根据主键获取信息
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Client 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "/getClientById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Client getClientById(@PathVariable(value = "id") int id) {
		return Client.db().find(Client.class, id);
	}

	/**
	 * 
	 * @Title: deletedById
	 * @Description: 删除一条或多条记录
	 * @param @param
	 *            delIds
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "/delete/{delIds}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deletedById(@PathVariable(value = "delIds") String delIds) {
		Client client = null;
		int count = 0;
		boolean result = true;
		String[] ids = delIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			client = Client.db().find(Client.class, ids[i]);
			client.setIs_deleted(1);
			Client.db().update(client);
			count++;
		}
		if (count > 0) {
			eventBus.post(new BaseCacheEvent("client", EventType.Delete));
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * @Title: checkClientName
	 * @Description: 验证客户端唯一性
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return String 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月18日
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkName(@RequestParam("id") Integer id, @RequestParam("name") String name) {
		boolean re = true;
		int clients = clientService.checkName(id, name);
		if (clients > 0) {
			re = false;
		}
		return re;
	}

	@RequestMapping(value = "/checkOther", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkOther(@RequestParam("id") Integer id, @RequestParam("site_id") Integer siteId,
			@RequestParam("platform") Integer platId) {
		boolean re = true;
		int clients = clientService.checkOther(id, siteId, platId);
		if (clients > 0) {
			re = false;
		}
		return re;
	}
}
