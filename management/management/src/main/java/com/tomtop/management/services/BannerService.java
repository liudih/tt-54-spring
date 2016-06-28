package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.manage.model.Banner;
import com.tomtop.management.ebean.manage.model.BannerContent;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Category;
import com.tomtop.management.service.model.BannerContentObject;
import com.tomtop.management.service.model.BannerObject;
import com.tomtop.management.service.model.BannerRequest;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @ClassName: BannerService
 * @Description: TODO(广告组服务类)
 * @author Guozy
 * @date 2015年12月16日
 *
 */
@Service
public class BannerService {

	@Autowired
	CommonService commonService;
	/**
	 * 
	 * @Title: getBannerPage
	 * @Description: TODO(分页查询广告)
	 * @param @param pageNo
	 * @param @param pageLimit
	 * @param @param banner
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return Page<BannerObject>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	public Page<BannerObject> getBannerPage(int pageNo, int pageLimit, Banner banner, String clients, String languages) {
		Page<BannerObject> bannerPage = new Page<BannerObject>();
		String query = "find Banner where 1 = 1 and is_deleted=0 ";
		if(StringUtil.isNotEmpty(clients)){
			query += " and client_id in ("+clients+") ";
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
			query += " and client_id in ("+client+") ";
		}
		if(StringUtil.isNotEmpty(languages)){
			query += " and language_id in ("+languages+") ";
		}
		if (null !=banner.getLayout_code()) {
			query += " and layout_code = '" + banner.getLayout_code()+"'";
		}
		if (null != banner.getName()) {
			query += " and name like '%" + banner.getName() + "%'";
		}
		PagedList<Banner> bannerPageList = Banner.db().createQuery(Banner.class, query).orderBy("id desc")
				.findPagedList(pageNo - 1, pageLimit);
		bannerPageList.loadRowCount();
		bannerPage.setCount(bannerPageList.getTotalRowCount());
		bannerPage.setLimit(pageLimit);
		bannerPage.setPageNo(pageNo);
		List<BannerObject> bannerObject = new ArrayList<BannerObject>();
		for (Banner layo : bannerPageList.getList()) {
			BannerObject lay = new BannerObject();
			try {
				BeanUtils.copyProperties(lay, layo);
				bannerObject.add(lay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (BannerObject bannerModel : bannerObject) {
			if (bannerModel.getClient_id() != null) {
				bannerModel.setClient(Client.db().find(Client.class, bannerModel.getClient_id()));
			}
			if (bannerModel.getLanguage_id() != null) {
				bannerModel.setLanguage(Language.db().find(Language.class, bannerModel.getLanguage_id()));
			}
			if (null != bannerModel.getPosition_id()) {
				BaseParameter baseParameter = new BaseParameter();
				baseParameter.setValue(bannerModel.getPosition_id());
				List<BaseParameter> baseParameters = BaseParameter.db().find(BaseParameter.class).where()
						.eq("value", baseParameter.getValue()).eq("type", "BANNER-POSITION").findList();
				bannerModel.setParameter(baseParameters.get(0));
			}
			bannerModel.setCreateTime(DateUtil.dateFormat(bannerModel.getWhenCreated()));
			bannerModel.setUpdateTime(DateUtil.dateFormat(bannerModel.getWhenModified()));
			if (bannerModel.getIs_enabled()!=null) {
				if (bannerModel.getIs_enabled()==0) {
					bannerModel.setEnabledStatus("Disabled");
				}else {
					bannerModel.setEnabledStatus("Enabled");
				}
			}
		}

		bannerPage.setList(bannerObject);
		return bannerPage;
	}

	/**
	 * 
	 * @Title: getBannerId
	 * @Description: TODO( 根据编号，获取广告的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Banner 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public Banner getBannerId(int id) {
		Banner banner = Banner.db().find(Banner.class, id);
		return banner;
	};

	/**
	 * 
	 * @Title: validateCode
	 * @Description: TODO(验证广告标识)
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return List<Banner> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月15日
	 */
	public List<Banner> validateCode(String code) {
		return Banner.db().find(Banner.class).where().eq("code", code).findList();
	};

	/**
	 * 
	 * @Title: delById
	 * @Description: TODO(通过编号，删除数据信息)
	 * @param @param
	 *            param
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public int delById(String param) {
		Banner banner = null;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				banner = Banner.db().find(Banner.class, sourceStrArray[i]);
				banner.setIs_deleted(1);
				Banner.db().save(banner);
				count++;
			}
		} else {
			banner = Banner.db().find(Banner.class, param);
			banner.setIs_deleted(1);
			Banner.db().save(banner);
			count++;
		}
		return count;
	}

	/**
	 * 
	 * @Title: addBanner
	 * @Description: TODO(新增广告)
	 * @param @param bannerRequest    
	 * @return void    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	public void addBanner(BannerRequest bannerRequest) {
		String clients = bannerRequest.getClients();
		String languages = bannerRequest.getLanguages();
		ArrayList<BannerContent> bcList = bannerRequest.getBcList();
		List<Banner> bannerList = new ArrayList<Banner>();
		List<BannerContent> bannerContentList = new ArrayList<BannerContent>();
		if (clients.indexOf(",") != -1) {
			String[] clientIds = clients.split(",");
			for (int i = 0; i < clientIds.length; i++) {
				if(languages.indexOf(",") != -1){
					String[] languageIds = languages.split(",");
					for(int j=0;j<languageIds.length;j++){
						Banner banner = new Banner();
						banner.setCode(bannerRequest.getCode());
						banner.setIs_enabled(bannerRequest.getIs_enabled());
						banner.setLayout_code(bannerRequest.getLayout_code());
						banner.setName(bannerRequest.getName());
						banner.setPosition_id(bannerRequest.getPosition_id());
						banner.setLanguage_id(Integer.parseInt(languageIds[j]));
						banner.setClient_id(Integer.parseInt(clientIds[i]));
						banner.setIs_deleted(0);
						bannerList.add(banner);
						for (BannerContent bc : bcList) {
							BannerContent bannerContent = new BannerContent();
							bannerContent.setBanner_code(banner.getCode());
							bannerContent.setClient_id(Integer.parseInt(clientIds[i]));
							bannerContent.setLanguage_id(Integer.parseInt(languageIds[j]));
							bannerContent.setIs_deleted(0);
							bannerContent.setCategory_id(null != bc.getCategory_id() ? bc.getCategory_id() : 0);
							bannerContent.setImg_url(bc.getImg_url());
							bannerContent.setIs_enabled(bc.getIs_enabled());
							bannerContent.setLayout_code(banner.getLayout_code());
							bannerContent.setName(bc.getName());
							bannerContent.setSort(bc.getSort());
							bannerContent.setTitle(bc.getTitle());
							bannerContent.setUrl(bc.getUrl());
							bannerContentList.add(bannerContent);
						}
					}
				}else{
					Banner banner = new Banner();
					banner.setCode(bannerRequest.getCode());
					banner.setIs_enabled(bannerRequest.getIs_enabled());
					banner.setLayout_code(bannerRequest.getLayout_code());
					banner.setName(bannerRequest.getName());
					banner.setPosition_id(bannerRequest.getPosition_id());
					banner.setLanguage_id(Integer.parseInt(languages));
					banner.setClient_id(Integer.parseInt(clientIds[i]));
					banner.setIs_deleted(0);
					bannerList.add(banner);
					for (BannerContent bc : bcList) {
						BannerContent bannerContent = new BannerContent();
						bannerContent.setBanner_code(banner.getCode());
						bannerContent.setClient_id(Integer.parseInt(clientIds[i]));
						bannerContent.setLanguage_id(Integer.parseInt(languages));
						bannerContent.setIs_deleted(0);
						bannerContent.setCategory_id(null != bc.getCategory_id() ? bc.getCategory_id() : 0);
						bannerContent.setImg_url(bc.getImg_url());
						bannerContent.setIs_enabled(bc.getIs_enabled());
						bannerContent.setLayout_code(banner.getLayout_code());
						bannerContent.setName(bc.getName());
						bannerContent.setSort(bc.getSort());
						bannerContent.setTitle(bc.getTitle());
						bannerContent.setUrl(bc.getUrl());
						bannerContentList.add(bannerContent);
					}
				}
			}
		} else {
			if(languages.indexOf(",") != -1){
				String[] languageIds = languages.split(",");
				for(int j=0;j<languageIds.length;j++){
					Banner banner = new Banner();
					banner.setCode(bannerRequest.getCode());
					banner.setIs_enabled(bannerRequest.getIs_enabled());
					banner.setLayout_code(bannerRequest.getLayout_code());
					banner.setName(bannerRequest.getName());
					banner.setPosition_id(bannerRequest.getPosition_id());
					banner.setLanguage_id(Integer.parseInt(languageIds[j]));
					banner.setClient_id(Integer.parseInt(clients));
					banner.setIs_deleted(0);
					bannerList.add(banner);
					for (BannerContent bc : bcList) {
						BannerContent bannerContent = new BannerContent();
						bannerContent.setBanner_code(banner.getCode());
						bannerContent.setClient_id(Integer.parseInt(clients));
						bannerContent.setLanguage_id(Integer.parseInt(languageIds[j]));
						bannerContent.setIs_deleted(0);
						bannerContent.setCategory_id(null != bc.getCategory_id() ? bc.getCategory_id() : 0);
						bannerContent.setImg_url(bc.getImg_url());
						bannerContent.setIs_enabled(bc.getIs_enabled());
						bannerContent.setLayout_code(banner.getLayout_code());
						bannerContent.setName(bc.getName());
						bannerContent.setSort(bc.getSort());
						bannerContent.setTitle(bc.getTitle());
						bannerContent.setUrl(bc.getUrl());
						bannerContentList.add(bannerContent);
					}
				}
			}else{
				Banner banner = new Banner();
				banner.setCode(bannerRequest.getCode());
				banner.setIs_enabled(bannerRequest.getIs_enabled());
				banner.setLayout_code(bannerRequest.getLayout_code());
				banner.setName(bannerRequest.getName());
				banner.setPosition_id(bannerRequest.getPosition_id());
				banner.setLanguage_id(Integer.parseInt(languages));
				banner.setClient_id(Integer.parseInt(clients));
				banner.setIs_deleted(0);
				bannerList.add(banner);
				for (BannerContent bc : bcList) {
					BannerContent bannerContent = new BannerContent();
					bannerContent.setBanner_code(banner.getCode());
					bannerContent.setClient_id(Integer.parseInt(clients));
					bannerContent.setLanguage_id(Integer.parseInt(languages));
					bannerContent.setIs_deleted(0);
					bannerContent.setCategory_id(null != bc.getCategory_id() ? bc.getCategory_id() : 0);
					bannerContent.setImg_url(bc.getImg_url());
					bannerContent.setIs_enabled(bc.getIs_enabled());
					bannerContent.setLayout_code(banner.getLayout_code());
					bannerContent.setName(bc.getName());
					bannerContent.setSort(bc.getSort());
					bannerContent.setTitle(bc.getTitle());
					bannerContent.setUrl(bc.getUrl());
					bannerContentList.add(bannerContent);
				}
			}
		}
		try {
			Banner.db().insertAll(bannerList);
			BannerContent.db().insertAll(bannerContentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param pageLimit 
	 * @param pageNo 
	 * @Title: getBCListByBannerCode
	 * @Description: TODO(通过广告标识查询广告内容)
	 * @param @param bannerCode
	 * @param @return    
	 * @return List<BannerContentObject>    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	public Page<BannerContentObject> getBCListByBanner(int pageNo, int pageLimit, Banner banner) {
		Page<BannerContentObject> bcPage = new Page<BannerContentObject>();
		PagedList<BannerContent> bcList = BannerContent.db().find(BannerContent.class).where()
				.eq("banner_code", banner.getCode()).eq("layout_code", banner.getLayout_code())
				.eq("client_id", banner.getClient_id()).eq("language_id", banner.getLanguage_id()).eq("is_deleted", 0)
				.orderBy().desc("is_enabled").orderBy().desc("whenModified").orderBy().asc("sort")
				.findPagedList(pageNo - 1, pageLimit);
		bcList.loadRowCount();
		bcPage.setCount(bcList.getTotalRowCount());
		bcPage.setLimit(pageLimit);
		bcPage.setPageNo(pageNo);
		List<BannerContentObject> bcoList = new ArrayList<BannerContentObject>();
		for (BannerContent bannerContent : bcList.getList()) {
			BannerContentObject bco = new BannerContentObject();
			try {
				BeanUtils.copyProperties(bco, bannerContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bcoList.add(bco);
		}
		for (BannerContentObject bco : bcoList) {
			if (null != bco.getCategory_id()) {
				Category c = Category.db().find(Category.class, bco.getCategory_id());
				if (null != c) {
					bco.setCategoryName(c.getCpath());
				}
			} else {
				bco.setCategoryName("");
			}
			if (null != bco.getIs_enabled()) {
				if (bco.getIs_enabled() == 1) {
					bco.setIsEnabled("Enabled");
				} else {
					bco.setIsEnabled("Disabled");
				}
			} else {
				bco.setIsEnabled("");
			}
		}
		bcPage.setList(bcoList);
		return bcPage;
	}

	/**
	 * 
	 * @Title: updateBanner
	 * @Description: TODO(修改广告)
	 * @param @param bannerRequest    
	 * @return void    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	public void updateBanner(BannerRequest bannerRequest) {
		Banner banner = Banner.db().find(Banner.class, bannerRequest.getId());
		banner.setIs_enabled(bannerRequest.getIs_enabled());
		banner.setName(bannerRequest.getName());
		banner.setPosition_id(bannerRequest.getPosition_id());
		ArrayList<BannerContent> bcList = bannerRequest.getBcList();
		List<BannerContent> oldbcList = BannerContent.db().find(BannerContent.class).where()
				.eq("banner_code", bannerRequest.getCode()).eq("client_id", banner.getClient_id())
				.eq("layout_code", banner.getLayout_code())
				.eq("language_id", banner.getLanguage_id()).eq("is_deleted", 0).findList();
		List<BannerContent> insertBCList = new ArrayList<BannerContent>();
		List<BannerContent> updateBCList = new ArrayList<BannerContent>();
		List<BannerContent> deleteBCList = new ArrayList<BannerContent>();
		for (BannerContent bc : bcList) {
			if(null == bc.getId()){
				BannerContent bannerContent = new BannerContent();
				bannerContent.setBanner_code(bannerRequest.getCode());
				bannerContent.setClient_id(bannerRequest.getClient_id());
				bannerContent.setLanguage_id(bannerRequest.getLanguage_id());
				bannerContent.setIs_deleted(0);
				bannerContent.setCategory_id(null != bc.getCategory_id() ? bc.getCategory_id() : 0);
				bannerContent.setImg_url(bc.getImg_url());
				bannerContent.setIs_enabled(bc.getIs_enabled());
				bannerContent.setLayout_code(bannerRequest.getLayout_code());
				bannerContent.setName(bc.getName());
				bannerContent.setSort(bc.getSort());
				bannerContent.setTitle(bc.getTitle());
				bannerContent.setUrl(bc.getUrl());
				insertBCList.add(bannerContent);
			}
		}
		for(int i=0;i<oldbcList.size();i++){
			for(int j=0;j<bcList.size();j++){
				if(oldbcList.get(i).getId().equals(bcList.get(j).getId())){
					BannerContent bannerContent = new BannerContent();
					bannerContent.setBanner_code(bannerRequest.getCode());
					bannerContent.setClient_id(bannerRequest.getClient_id());
					bannerContent.setLanguage_id(bannerRequest.getLanguage_id());
					bannerContent.setId(bcList.get(j).getId());
					bannerContent.setCategory_id(null != bcList.get(j).getCategory_id() ? bcList.get(j).getCategory_id() : 0);
					bannerContent.setImg_url(bcList.get(j).getImg_url());
					bannerContent.setIs_enabled(bcList.get(j).getIs_enabled());
					bannerContent.setLayout_code(bannerRequest.getLayout_code());
					bannerContent.setName(bcList.get(j).getName());
					bannerContent.setSort(bcList.get(j).getSort());
					bannerContent.setTitle(bcList.get(j).getTitle());
					bannerContent.setUrl(bcList.get(j).getUrl());
					updateBCList.add(bannerContent);
				}
			}
		}
		List<Long> oldIds = new ArrayList<Long>();
		List<Long> Ids = new ArrayList<Long>();
		for(BannerContent bc : oldbcList){
			oldIds.add(bc.getId());
		}
		for(BannerContent bc : bcList){
			Ids.add(bc.getId());
		}
		for(Long id : oldIds){
			if(!Ids.contains(id)){
				BannerContent bannerContent = new BannerContent();
				bannerContent = BannerContent.db().find(BannerContent.class, id);
				bannerContent.setIs_deleted(1);
				deleteBCList.add(bannerContent);
			}
		}
		try {
			Banner.db().update(banner);
			BannerContent.db().insertAll(insertBCList);
			BannerContent.db().updateAll(updateBCList);
			BannerContent.db().updateAll(deleteBCList);
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: bannerCodeUniqueValidate
	 * @Description: TODO(广告标识重复验证)
	 * @param @param id
	 * @param @param code
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月13日
	 */
	public int bannerCodeUniqueValidate(Integer id, String code) {
		int findCount = 0;
		if (id == 0) {
			findCount = Banner.db().find(Banner.class).where().eq("code", code).findRowCount();
		} else {
			Banner c = Banner.db().find(Banner.class, id);
			if (!c.getCode().equals(code)) {
				findCount = Banner.db().find(Banner.class).where().eq("code", code).findRowCount();
			}
		}
		return findCount;
	}

	/**
	 * 
	 * @param layoutCode 
	 * @Title: bannerUniqueValidate
	 * @Description: TODO(广告唯一验证)
	 * @param @param code
	 * @param @param clients
	 * @param @param languages
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月14日
	 */
	public int bannerUniqueValidate(String code, String clients, String languages, String layoutCode) {
		return Banner.db().find(Banner.class).where().in("client_id", clients).in("language_id", languages)
				.eq("layout_code", layoutCode).eq("code", code).eq("is_deleted", 0).findRowCount();
	}

	/**
	 * 
	 * @Title: addBannerContent
	 * @Description: TODO(新增广告内容)
	 * @param @param bc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	public int addBannerContent(BannerContent bc) {
		int addCount = 0;
		bc.setIs_deleted(0);
		if(null == bc.getCategory_id()){
			bc.setCategory_id(0);
		}
		try {
			BannerContent.db().insert(bc);
			addCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addCount;
	}

	/**
	 * 
	 * @Title: updateBannerContent
	 * @Description: TODO(修改广告内容)
	 * @param @param bc
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	public int updateBannerContent(BannerContent bc) {
		int updateCount = 0;
		if(null == bc.getCategory_id()){
			bc.setCategory_id(0);
		}
		try {
			BannerContent.db().update(bc);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

	/**
	 * 
	 * @Title: deleteBannerContent
	 * @Description: TODO(删除广告内容)
	 * @param @param id
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	public int deleteBannerContent(Long id) {
		int deleteCount = 0;
		try {
			BannerContent bc = BannerContent.db().find(BannerContent.class, id);
			bc.setIs_deleted(1);
			BannerContent.db().update(bc);
			deleteCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return deleteCount;
	}

	/**
	 * 
	 * @Title: updateBannerOnly
	 * @Description: TODO(修改广告（只修改广告）)
	 * @param @param banner
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月9日
	 */
	public int updateBannerOnly(Banner banner) {
		int updateCount = 0;
		try {
			Banner.db().update(banner);
			updateCount++;
		} catch (OptimisticLockException e) {
			e.printStackTrace();
		}
		return updateCount;
	}

}
