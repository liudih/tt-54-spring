package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.config.CurrentUser;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Resource;
import com.tomtop.management.service.model.ResourceObject;

@Service
public class ResourceService {
	@Autowired
	CurrentUser currentUser;
	@Autowired
	CommonService commonService;

	/**
	 * 
	 * @param languages 
	 * @param clients 
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
	public Page<ResourceObject> getResourcePage(int pageNo, int pageLimit, Resource r, String clients, String languages) {
		Page<ResourceObject> resourcePage = new Page<ResourceObject>();
		String q = "find Resource where is_deleted = 0";
		if (StringUtil.isNotEmpty(clients)) {
			q += " and client_id in (" + clients + ")";
		}else{
			List<Client> clientList = commonService.getAllClient();
			String client = null;
			if(clientList.size() > 0){
				client = "";
				for(int i=0;i<clientList.size();i++){
					if(i == 0){
						client += clientList.get(i).getId();
					}else{
						client += ","+clientList.get(i).getId();
					}
				}
			}
			q += " and client_id in ("+client+") ";
		}
		if (StringUtil.isNotEmpty(languages)) {
			q += " and language_id in (" + languages + ")";
		}
		if (StringUtil.isNotEmpty(r.getKey())) {
			q += " and key like '%" + r.getKey() + "%'";
		}
		if (StringUtil.isNotEmpty(r.getValue())) {
			q += " and value like '%" + r.getValue() + "%'";
		}
		if (null != r.getIs_enabled()) {
			q += " and is_enabled = " + r.getIs_enabled();
		}
		PagedList<Resource> rPageList = Resource.db().createQuery(Resource.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		rPageList.loadRowCount();
		resourcePage.setCount(rPageList.getTotalRowCount());
		resourcePage.setLimit(pageLimit);
		resourcePage.setPageNo(pageNo);
		List<ResourceObject> roList = new ArrayList<ResourceObject>();
		for (Resource resource : rPageList.getList()) {
			ResourceObject ro = new ResourceObject();
			try {
				BeanUtils.copyProperties(ro, resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
			roList.add(ro);
		}
		for (ResourceObject ro : roList) {
			if (null != ro.getClient_id()) {
				ro.setClient(Client.db().find(Client.class, ro.getClient_id()));
			}
			if (null != ro.getLanguage_id()) {
				ro.setLanguage(Language.db().find(Language.class, ro.getLanguage_id()));
			}
			ro.setCrateTime(DateUtil.dateFormat(ro.getWhenCreated()));
			ro.setUpdateTime(DateUtil.dateFormat(ro.getWhenModified()));
		}
		resourcePage.setList(roList);
		return resourcePage;
	}

	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: addResource
	 * @Description: TODO(新增资源信息)
	 * @param @param r
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int addResource(Resource r, String clients, String languages) {
		List<Integer> clientList = parseIntegerList(clients);
		List<Integer> languageList = parseIntegerList(languages);
		int addCount = 0;
		List<Resource> rList = new ArrayList<Resource>();
		try {
			for(Integer clientId : clientList){
				for(Integer languageid : languageList){
					Resource resource = new Resource();
					resource.setClient_id(clientId);
					resource.setIs_deleted(0);
					resource.setIs_enabled(r.getIs_enabled());
					resource.setKey(r.getKey());
					resource.setLanguage_id(languageid);
					resource.setValue(r.getValue());
					rList.add(resource);
				}
			}
			for(Resource resource : rList){
				addSingleResource(resource);
				addCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addCount;
	}
	
	private int addSingleResource(Resource r) throws Exception {
		int addCount = 0;
		String sql = "INSERT INTO `manage`.`base_resource` ( `language_id`, `client_id`, `key`, `value`, `is_deleted`, `created_by`, `created_on`, `last_updated_by`, `last_updated_on`, `is_enabled`) "
				+ "VALUES (" + r.getLanguage_id() + ", " + r.getClient_id() + ", '" + r.getKey() + "', '" + r.getValue()
				+ "', " + "'0', '" + String.valueOf(currentUser.currentUser()) + "', " + "sysdate(), '"
				+ String.valueOf(currentUser.currentUser()) + "', " + "sysdate(), " + "'1')";
		Resource.db().createSqlUpdate(sql).execute();
		addCount++;
		return addCount;
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
	public Resource getResourceById(Integer id) {
		return Resource.db().find(Resource.class, id);
	}

	/**
	 * 
	 * @Title: updateResource
	 * @Description: TODO(修改资源信息)
	 * @param @param r
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int updateResource(Resource r) {
		int update = 0;
		try {
			String sql = "UPDATE `manage`.`base_resource` SET "
					+ "`key` = '" + r.getKey() + "', " + "`value` = '"
					+ r.getValue() + "', " + "`is_enabled` = " + r.getIs_enabled() + ", " + "`last_updated_by` = '"
					+ String.valueOf(currentUser.currentUser()) + "', " + "`last_updated_on` = sysdate() WHERE id = "
					+ r.getId();
			Resource.db().createSqlUpdate(sql).execute();
			update++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return update;
	}

	/**
	 * 
	 * @Title: deleteResource
	 * @Description: TODO(删除资源信息)
	 * @param @param rids
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int deleteResource(String rids) {
		Resource r = null;
		int deleteCount = 0;
		if (rids.indexOf(",") != -1) {
			String[] ids = rids.split(",");
			for (int i = 0; i < ids.length; i++) {
				r = Resource.db().find(Resource.class, Integer.parseInt(ids[i]));
				try {
					r.setIs_deleted(1);
					Resource.db().save(r);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			r = Resource.db().find(Resource.class, Integer.parseInt(rids));
			try {
				r.setIs_deleted(1);
				Resource.db().save(r);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
	}

	/**
	 * 
	 * @Title: resourceKeyUniqueValidate
	 * @Description: TODO(校验资源标识是否重复)
	 * @param @param id
	 * @param @param resourceKey
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int resourceKeyUniqueValidate(Integer id, String resourceKey) {
		int findCount = 0;
		if (id == 0) {
			findCount = Resource.db().find(Resource.class).where().eq("key", resourceKey).findRowCount();
		} else {
			Resource r = Resource.db().find(Resource.class, id);
			if (!r.getKey().equals(resourceKey)) {
				findCount = Resource.db().find(Resource.class).where().eq("key", resourceKey).findRowCount();
			}
		}
		return findCount;
	}

	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: getXMLString
	 * @Description: TODO(获取XML文件字符串)
	 * @param @param r
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public String getXMLString(Resource r, String clients, String languages) {
		List<Resource> rList = getResourceList(r, clients, languages);
		String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n<resources>\r\n";
		for (Resource resource : rList) {
			s += "\t<string name=\"" + resource.getKey() + "\">\"" + resource.getValue() + "\"</string>\r\n";
		}
		s += "</resources>";
		return s;
	}

	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: getTXTString
	 * @Description: TODO(获取TXT文件字符串)
	 * @param @param r
	 * @param @return    
	 * @return String    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public String getTXTString(Resource r, String clients, String languages) {
		List<Resource> rList = getResourceList(r, clients, languages);
		String s = "";
		for (Resource resource : rList) {
			s += "\"" + resource.getKey() + "\"=\"" + resource.getValue() + "\";\r\n";
		}
		return s;
	}

	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: getResourceList
	 * @Description: TODO(查询资源列表)
	 * @param @param r
	 * @param @return    
	 * @return List<Resource>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	private List<Resource> getResourceList(Resource r, String clients, String languages) {
		String q = "find Resource where is_deleted = 0";
		if (StringUtil.isNotEmpty(clients)) {
			q += " and client_id in (" + clients + ")";
		}
		if (StringUtil.isNotEmpty(languages)) {
			q += " and language_id in (" + languages + ")";
		}
		if (StringUtil.isNotEmpty(r.getKey())) {
			q += " and key like '%" + r.getKey() + "%'";
		}
		if (StringUtil.isNotEmpty(r.getValue())) {
			q += " and value like '%" + r.getValue() + "%'";
		}
		if (null != r.getIs_enabled()) {
			q += " and is_enabled = " + r.getIs_enabled();
		}
		return Resource.db().createQuery(Resource.class, q).orderBy("id").findList();
	}

	/**
	 * 
	 * @Title: parseIntegerList
	 * @Description: TODO(将字符串转化为整型集合)
	 * @param @param s
	 * @param @return    
	 * @return List<Integer>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月21日
	 */
	private List<Integer> parseIntegerList(String s) {
		List<Integer> list = new ArrayList<Integer>();
		if (s.indexOf(",") != -1) {
			String[] arr = s.split(",");
			for (int i = 0; i < arr.length; i++) {
				list.add(Integer.parseInt(arr[i]));
			}
		} else {
			list.add(Integer.parseInt(s));
		}
		return list;
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
	public boolean resourceUniqueValidate(Resource r, String clients, String languages) {
		int findCount = Resource.db().find(Resource.class).where().in("client_id", clients).in("language_id", languages)
				.eq("key", r.getKey()).eq("is_deleted", 0).findRowCount();
		return findCount > 0;
	}
}
