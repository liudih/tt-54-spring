package com.tomtop.management.homepage.services;

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
import com.tomtop.management.ebean.homepage.model.Brand;
import com.tomtop.management.ebean.homepage.model.NewestVideo;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.service.model.NewestVideoObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductService;

@Service
public class NewestVideoService {
	@Autowired
	ProductService productService;
	@Autowired
	CommonService commonService;
	
	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: getNewestVideoPage
	 * @Description: TODO(分页查询最新视频)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param nv
	 * @param @return    
	 * @return Page<NewestVideoObject>    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public Page<NewestVideoObject> getNewestVideoPage(int pageNo, int pageLimit, NewestVideo nv, String clients, String languages) {
		Page<NewestVideoObject> newestVideoPage = new Page<NewestVideoObject>();
		String q = "find NewestVideo where is_deleted = 0";
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
		if (StringUtil.isNotEmpty(nv.getVideo_by())) {
			q += " and video_by like '%" + nv.getVideo_by() + "%'";
		}
		if (StringUtil.isNotEmpty(nv.getCountry())) {
			q += " and country = '" + nv.getCountry() + "'";
		}
		if (StringUtil.isNotEmpty(nv.getSku())) {
			q += " and sku like '%" + nv.getSku() + "%'";
		}
		PagedList<NewestVideo> newestVideoPageList = NewestVideo.db().createQuery(NewestVideo.class, q)
				.order().desc("whenModified").findPagedList(pageNo - 1, pageLimit);
		newestVideoPageList.loadRowCount();
		newestVideoPage.setCount(newestVideoPageList.getTotalRowCount());
		newestVideoPage.setLimit(pageLimit);
		newestVideoPage.setPageNo(pageNo);
		List<NewestVideoObject> nvoList = new ArrayList<NewestVideoObject>();
		for (NewestVideo newestVideo : newestVideoPageList.getList()) {
			NewestVideoObject nvo = new NewestVideoObject();
			try {
				BeanUtils.copyProperties(nvo, newestVideo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			nvoList.add(nvo);
		}
		for (NewestVideoObject nvo : nvoList) {
			if (null != nvo.getClient_id()) {
				nvo.setClient(Client.db().find(Client.class, nvo.getClient_id()));
			}
			if (null != nvo.getLanguage_id()) {
				nvo.setLanguage(Language.db().find(Language.class, nvo.getLanguage_id()));
			}
			nvo.setCrateTime(DateUtil.dateFormat(nvo.getWhenCreated()));
			nvo.setUpdateTime(DateUtil.dateFormat(nvo.getWhenModified()));
		}
		newestVideoPage.setList(nvoList);
		return newestVideoPage;
	}

	/**
	 * 
	 * @param languages 
	 * @param clients 
	 * @Title: addNewestVideo
	 * @Description: TODO(新增最新视频)
	 * @param @param nv
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int addNewestVideo(NewestVideo nv, String clients, String languages) {
		List<Integer> clientList = parseIntegerList(clients);
		List<Integer> languageList = parseIntegerList(languages);
		int addCount = 0;
		List<NewestVideo> nvList = new ArrayList<NewestVideo>();
		try {
			for(Integer clientId : clientList){
				for(Integer languageid : languageList){
					NewestVideo newestVideo = new NewestVideo();
					newestVideo.setClient_id(clientId);
					newestVideo.setCountry(nv.getCountry());
					newestVideo.setIs_deleted(0);
					newestVideo.setIs_enabled(nv.getIs_enabled());
					newestVideo.setLanguage_id(languageid);
					newestVideo.setListing_id(productService.getListingIdBySku(nv.getSku()));
					newestVideo.setSku(nv.getSku());
					newestVideo.setTitle(nv.getTitle());
					newestVideo.setVideo_by(nv.getVideo_by());
					newestVideo.setVideo_url(nv.getVideo_url());
					nvList.add(newestVideo);
				}
			}
			NewestVideo.db().insertAll(nvList);
			addCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: updateNewestVideo
	 * @Description: TODO(修改最新视频)
	 * @param @param nv
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int updateNewestVideo(NewestVideo nv) {
		int updateCount = 0;
		NewestVideo newestVideo = NewestVideo.db().find(NewestVideo.class, nv.getId());
		newestVideo.setCountry(nv.getCountry());
		newestVideo.setIs_enabled(nv.getIs_enabled());
		newestVideo.setSku(nv.getSku());
		newestVideo.setListing_id(productService.getListingIdBySku(nv.getSku()));
		newestVideo.setTitle(nv.getTitle());
		newestVideo.setVideo_by(nv.getVideo_by());
		newestVideo.setVideo_url(nv.getVideo_url());
		try {
			NewestVideo.db().save(newestVideo);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: getNewestVideoById
	 * @Description: TODO(通过id获取最新视频)
	 * @param @param id
	 * @param @return    
	 * @return NewestVideo    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public NewestVideo getNewestVideoById(Integer id) {
		return NewestVideo.db().find(NewestVideo.class, id);
	}

	/**
	 * 
	 * @Title: delete
	 * @Description: TODO(删除最新视频)
	 * @param @param nvIds
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2015年12月27日
	 */
	public int delete(String nvIds) {
		NewestVideo nv = null;
		int deleteCount = 0;
		if (nvIds.indexOf(",") != -1) {
			String[] ids = nvIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				nv = NewestVideo.db().find(NewestVideo.class, Integer.parseInt(ids[i]));
				try {
					nv.setIs_deleted(1);
					NewestVideo.db().save(nv);
					deleteCount++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			nv = NewestVideo.db().find(NewestVideo.class, Integer.parseInt(nvIds));
			try {
				nv.setIs_deleted(1);
				NewestVideo.db().save(nv);
				deleteCount++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deleteCount;
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
	 * @Title: newestVideoUniqueValidate
	 * @Description: TODO(最新视频唯一校验)
	 * @param @param nv
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return boolean    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月22日
	 */
	public boolean newestVideoUniqueValidate(NewestVideo nv, String clients, String languages) {
		int findCount = NewestVideo.db().find(NewestVideo.class).where().eq("title", nv.getTitle()).in("client_id", clients)
				.in("language_id", languages).eq("is_deleted", 0).findRowCount();
		return findCount > 0;
	}
}
