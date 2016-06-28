package com.tomtop.management.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import com.tomtop.management.ebean.product.model.ProductSalePrice;

@Service
public class ProductSalePriceService {

	/**
	 * 
	 * @Title: getProductSalePriceBySku
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param sku
	 * @param @return 参数
	 * @return List<ProductSalePrice> 返回类型
	 * @throws @author fcl
	 * @date 2016年1月6日
	 */
	public List<ProductSalePrice> getProductSalePriceByListingId(
			String listingId, Date begin, Date end) {
		List<ProductSalePrice> productSalePriceList = ProductSalePrice
				.db()
				.find(ProductSalePrice.class)
				.where()
				.and(com.avaje.ebean.Expr.eq("clistingid", listingId),
						com.avaje.ebean.Expr.or(com.avaje.ebean.Expr.or(
								com.avaje.ebean.Expr.between("dbegindate",
										begin, end), com.avaje.ebean.Expr
										.between("denddate", begin, end)),
								com.avaje.ebean.Expr.and(com.avaje.ebean.Expr
										.le("dbegindate", begin),
										com.avaje.ebean.Expr
												.ge("denddate", end))))
				.findList();
		return productSalePriceList;

	}

	/**
	 * @Title: delete
	 * @Description: 删除时间段的促销数据
	 * @param @param clistingid
	 * @param @param date
	 * @param @return 参数
	 * @return int 返回类型
	 * @throws
	 * @author fcl
	 * @date 2016年1月16日
	 */
	public int delete(String clistingid, Date date) {
		return ProductSalePrice.db().find(ProductSalePrice.class).where()
				.eq("clistingid", clistingid).le("dbegindate", date)
				.ge("denddate", date).delete();
	}
}
