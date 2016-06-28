package com.tomtop.management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tomtop.management.authority.services.serviceImp.CurrentUserService;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.forms.CurrentUser;

@Service
public class ProductService {
	
	@Autowired
	CurrentUserService currentUserService;
	/**
	 * 
	 * @Title: getListingIdBySku
	 * @Description: TODO(通过sku查询listingid)
	 * @param @param
	 *            sku
	 * @param @return
	 * @return String
	 * @throws @author
	 *             yinfei
	 * @date 2015年12月16日
	 */
	public String getListingIdBySku(String sku) {
		if (null != sku && !sku.equals("")) {
			List<Product> product = Product.db().find(Product.class).where().eq("iwebsiteid", getCurrentSiteId()).eq("csku", sku).eq("bactivity", false).findList();
			if (null != product && product.size() > 0) {
				return product.get(0).getClistingid();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: getProductBySku
	 * @Description: TODO(通过sku查询Product信息)
	 * @param @param
	 *            sku
	 * @param @return
	 *            参数
	 * @return Product 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月4日
	 */
	public Product getProductBySku(String sku) {
		return Product.db().find(Product.class).where().eq("csku", sku).eq("iwebsiteid", getCurrentSiteId()).eq("bactivity", false).findUnique();
	}

	/**
	 * 
	 * @Title: getCountBySku
	 * @Description: TODO(通过sku查询产品个数)
	 * @param @param sku
	 * @param @return    
	 * @return int    
	 * @throws
	 * @author yinfei
	 * @date 2016年1月16日
	 */
	public int getCountBySku(String sku){
		int count = 0;
		try {
			count = Product.db().find(Product.class).where().eq("iwebsiteid", getCurrentSiteId()).eq("csku", sku).eq("bactivity", false).findRowCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 
	 * @Title: getCurrentSiteId
	 * @Description: TODO(获取当前站点id)
	 * @param @return    
	 * @return Long    
	 * @throws
	 * @author yinfei
	 * @date 2016年3月7日
	 */
	public Long getCurrentSiteId(){
		CurrentUser currentUser = currentUserService.getUserDetails();
		Integer currentSite = currentUser.getSiteMap().get("siteId");
		if(null == currentSite){
			currentSite = 1;
		}
		return Long.valueOf(currentSite);
	}
}
