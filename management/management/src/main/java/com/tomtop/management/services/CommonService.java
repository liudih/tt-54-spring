package com.tomtop.management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tomtop.management.ebean.authority.model.AdminRole;
import com.tomtop.management.ebean.base.model.Storage;
import com.tomtop.management.ebean.manage.model.Banner;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Country;
import com.tomtop.management.ebean.manage.model.Currency;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.ebean.manage.model.PlatForm;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.ebean.shipping.model.ShippingTemplate;
import com.tomtop.management.ebean.shipping.model.ShippingType;

@Service
public class CommonService {
	@Autowired
	ProductService productService;

	/**
	 * 
	 * @Title: getAllClient
	 * @Description: TODO(查询所有的客户端)
	 * @param @return
	 * @return List<Client>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public List<Client> getAllClient() {
		return Client.db().find(Client.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();

	}

	/**
	 * 
	 * @Title: getAllLanguage
	 * @Description: TODO(查询所有的语言)
	 * @param @return
	 * @return List<Language>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public List<Language> getAllLanguage() {
		return Language.db().find(Language.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	}

	/**
	 * 
	 * @Title: getAllLayout
	 * @Description: TODO(查询所有的布局)
	 * @param @return
	 * @return List<Layout>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public List<Layout> getAllLayout() {
		Long siteId = productService.getCurrentSiteId();
		List<Client> currentSiteClients = Client.db().find(Client.class).where().eq("is_deleted", 0).eq("is_enabled", 1)
				.eq("site_id", siteId).findList();
		List<Layout> lList = Lists.newArrayList();
		if (null != currentSiteClients && currentSiteClients.size() > 0) {
			List<Long> clientIds = Lists.transform(currentSiteClients, c -> c.getId());
			lList = Layout.db().find(Layout.class).where().eq("is_deleted", 0).eq("is_enabled", 1)
					.in("client_id", clientIds).findList();
		}
		return lList;
	}

	/**
	 * 
	 * @Title: getAllLoyoutModuls
	 * @Description: TODO(查询所有的布局模块)
	 * @param @return
	 * @return List<LoyoutModul>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public List<LoyoutModul> getAllLoyoutModuls() {
		return LoyoutModul.db().find(LoyoutModul.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	}

	/**
	 * 
	 * @Title: getAllBanner
	 * @Description: TODO(查询所有广告)
	 * @param @return
	 * @return List<Banner>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月22日
	 */
	public List<Banner> getAllBanner() {
		return Banner.db().find(Banner.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	}

	/**
	 * 
	 * @Title: getAllParam
	 * @Description: 查询位置键值对 left,right,top,bottom
	 * @param @return
	 *            参数
	 * @return List<BaseParameter> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月16日
	 */
	public List<BaseParameter> getAllParam() {
		Long siteId = productService.getCurrentSiteId();
		List<Client> currentSiteClients = Client.db().find(Client.class).where().eq("is_deleted", 0).eq("is_enabled", 1)
				.eq("site_id", siteId).findList();
		List<BaseParameter> baseParameters = Lists.newArrayList();
		String q = " find BaseParameter where type = 'LAYOUT-POSITION' ";
		if (null != currentSiteClients && currentSiteClients.size() > 0) {
			List<Long> clients = Lists.transform(currentSiteClients, p -> {
				return p.getId();
			});
			List<BaseParameter> bList = BaseParameter.db().createQuery(BaseParameter.class, q).where()
					.in("client_id", clients).eq("is_deleted", 0).eq("is_enabled", 1).orderBy("sort").findList();
			if (null != bList && bList.size() > 0) {
				baseParameters.addAll(bList);
			}
		}

		return baseParameters;
	}

	/**
	 * 
	 * @Title: getAllSite
	 * @Description: 获取所有站点信息
	 * @param @return
	 *            参数
	 * @return List<Site> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	public List<Site> getAllSite() {
		return Site.db().find(Site.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	}

	/**
	 * 
	 * @Title: getPlatFormParam
	 * @Description: 获取base_paramer表中的平台信息
	 * @param @return
	 *            参数
	 * @return List<BaseParameter> 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	public List<PlatForm> getPlatFormParam() {
		return PlatForm.db().find(PlatForm.class).where().eq("is_deleted", 0).findList();
	}

	/**
	 * 
	 * @Title: getAllCurrency
	 * @Description: TODO(查询所有货币)
	 * @param @return
	 * @return List<Currency>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月22日
	 */
	public List<Currency> getAllCurrency() {
		return Currency.db().find(Currency.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	}

	/**
	 * 
	 * @Title: getAllCountry
	 * @Description: TODO(查询所有国家)
	 * @param @return
	 * @return List<Country>
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月25日
	 */
	public List<Country> getAllCountry() {
		return Country.db().find(Country.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	}

	public List<AdminRole> getAllRole() {
		return AdminRole.db().find(AdminRole.class).findList();
	}

	/**
	 * 
	 * @Title: getAllShippingType
	 * @Description: TODO(查询所有的物流类型信息)
	 * @param @return
	 *            参数
	 * @return List<ShippingType> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年2月20日
	 */
	public List<ShippingType> getAllShippingType() {
		return ShippingType.db().find(ShippingType.class).where().eq("is_deleted", 0).eq("is_enabled", 1).findList();
	};

	/**
	 * 
	 * @Title: getAllFilter
	 * @Description: TODO(获取所有筛选条件)
	 * @param @return
	 * @return List<BaseParameter>
	 * @throws @author
	 *             yinfei
	 * @date 2016年2月23日
	 */
	public List<BaseParameter> getAllFilter() {
		return BaseParameter.db().find(BaseParameter.class).where().eq("is_deleted", 0).eq("is_enabled", 1)
				.eq("type", "FILTER").orderBy("sort").findList();
	}

	/**
	 * 
	 * @Title: getAllStorage
	 * @Description: TODO(获取所有仓库)
	 * @param @return
	 * @return List<Storage>
	 * @throws @author
	 *             yinfei
	 * @date 2016年2月23日
	 */
	public List<Storage> getAllStorage() {
		return Storage.db().find(Storage.class).orderBy().asc("cstoragename").findList();
	}

	/**
	 * 
	 * @Title: getAllShippingTemplate
	 * @Description: TODO(获取所有物流模版)
	 * @param @return
	 * @return List<ShippingTemplate>
	 * @throws @author
	 *             yinfei
	 * @date 2016年2月23日
	 */
	public List<ShippingTemplate> getAllShippingTemplate() {
		return ShippingTemplate.db().find(ShippingTemplate.class).where().eq("is_deleted", 0).eq("is_enabled", 1)
				.findList();
	}

}
