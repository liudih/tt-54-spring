package com.tomtop.management.homepage.services;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.avaje.ebean.Transaction;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.FoundationService;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.homepage.model.DailyDeal;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.ebean.product.model.ProductSalePrice;
import com.tomtop.management.service.model.DailyDealObject;
import com.tomtop.management.services.CommonService;
import com.tomtop.management.services.ProductSalePriceService;
import com.tomtop.management.services.ProductService;

import org.apache.commons.beanutils.BeanUtils;

/***
 * 
 * @ClassName: DailyDealService
 * @Description: TODO(每日促销服务类)
 * @author Administrator
 * @date 2015年12月24日
 *
 */
@Service
public class DailyDealService {

	@Autowired
	ProductService productService;
	@Autowired
	ProductSalePriceService salePriceService;
	@Autowired
	FoundationService foundationService;
	@Autowired
	CommonService commonService;

	public Page<DailyDealObject> getDailyDealPage(int pageNo, int pageLimit, DailyDealObject object, String clients,
			String languages) {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		Page<DailyDealObject> dailyDealPage = null;
		try {
			DailyDeal dailyDeal = new DailyDeal();
			BeanUtils.copyProperties(dailyDeal, object);
			dailyDealPage = new Page<DailyDealObject>();
			String query = "find DailyDeal where  is_deleted=0 ";
			String clientStr = "";
			if (StringUtil.isNotEmpty(clients)) {
				query += " and client_id in (" + clients + ") ";
			} else {
				List<Client> clientss = commonService.getAllClient();
				if (clientss.size() > 0 && clientss != null) {
					for (int i = 0; i < clientss.size(); i++) {
						if (i == 0) {
							clientStr = clientss.get(i).getId() + "";
						} else {
							clientStr += "," + clientss.get(i).getId();
						}
					}
					query += " and client_id in (" + clientStr + ") ";
				}
			}
			if (StringUtil.isNotEmpty(languages)) {
				query += " and language_id in (" + languages + ") ";
			}
			if (null != dailyDeal.getSku()) {
				query += " and sku like '%" + dailyDeal.getSku() + "%'";
			}
			if (null != object.getStartTime()) {
				date = sdf.parse(object.getStartTime());
				Timestamp datetime = new Timestamp(date.getTime());
				object.setStart_date(datetime);
				query += " and start_date = '" + object.getStart_date() + "'";
			}

			PagedList<DailyDeal> dailyDealPageList = DailyDeal.db().createQuery(DailyDeal.class, query)
					.order().desc("whenModified").findPagedList(pageNo - 1, pageLimit);
			dailyDealPageList.loadRowCount();
			dailyDealPage.setCount(dailyDealPageList.getTotalRowCount());
			dailyDealPage.setLimit(pageLimit);
			dailyDealPage.setPageNo(pageNo);
			List<DailyDealObject> dailyDealObject = new ArrayList<DailyDealObject>();
			for (DailyDeal deal : dailyDealPageList.getList()) {
				DailyDealObject dealObject = new DailyDealObject();
				BeanUtils.copyProperties(dealObject, deal);
				dailyDealObject.add(dealObject);

			}

			for (DailyDealObject daily : dailyDealObject) {
				if (daily.getClient_id() != null) {
					daily.setClient(Client.db().find(Client.class, daily.getClient_id()));
				}
				if (daily.getLanguage_id() != null) {
					daily.setLanguage(Language.db().find(Language.class, daily.getLanguage_id()));
				}
				daily.setCreateTime(DateUtil.dateFormat(daily.getWhenCreated()));
				daily.setUpdateTime(DateUtil.dateFormat(daily.getWhenModified()));
				daily.setStartTime(DateUtil.dateFormat(daily.getStart_date()));
				if (daily.getIs_enabled() != null) {
					if (daily.getIs_enabled() == 0) {
						daily.setEnabledStatus("Disabled");
					} else {
						daily.setEnabledStatus("Enabled");
					}
				}
			}

			dailyDealPage.setList(dailyDealObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dailyDealPage;
	}

	/**
	 * 
	 * @Title: getDailyDealId
	 * @Description: TODO( 根据编号，获取促销的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Banner 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public DailyDealObject getDailyDealId(int id) {
		DailyDealObject dealObject = new DailyDealObject();
		DailyDeal dailyDeal = DailyDeal.db().find(DailyDeal.class, id);
		try {
			BeanUtils.copyProperties(dealObject, dailyDeal);
			Product product = productService.getProductBySku(dealObject.getSku());
			double price = product.getFprice();
			double costprice = product.getFcostprice();
			double saleprice = price * dailyDeal.getDiscount() / 100;
			dealObject.setPrice((float) price);
			dealObject.setfCostPrice((float) costprice);
			dealObject.setSalePrice((float) saleprice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dealObject;
	};

	/**
	 * 
	 * @Title: validateCode
	 * @Description: TODO(验证每日促销)
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return List<Banner> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月15日
	 */
	public List<DailyDeal> validateCode(String dailyDeal) {
		return DailyDeal.db().find(DailyDeal.class).where().eq("is_deleted", 0).eq("code", dailyDeal).findList();
	};

	/**
	 * 
	 * @Title: add
	 * @Description: TODO(添加每日促销信息)
	 * @param @param
	 *            dailyDealObject
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月15日
	 */
	public int add(DailyDealObject dailyDealObject) {

		Date beginDate = DateUtil.parseDate(
				DateUtil.getDateTimeYYYYMMDD(dailyDealObject.getStartTime_date()) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateUtil.parseDate(
				DateUtil.getDateTimeYYYYMMDD(dailyDealObject.getStartTime_date()) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
		Date spiltbeginDate = DateUtil.getBeforeDay(beginDate, Calendar.SECOND, -1);
		Date spiltendDate = DateUtil.getBeforeDay(endDate, Calendar.SECOND, 1);
		int result = 0;
		Timestamp datetime = new Timestamp(dailyDealObject.getStartTime_date().getTime());
		dailyDealObject.setStart_date(datetime);
		DailyDeal dailyDeal = new DailyDeal();
		List<ProductSalePrice> salePrices = salePriceService
				.getProductSalePriceByListingId(dailyDealObject.getListing_id(), beginDate, endDate);
		Transaction tc = ProductSalePrice.db().beginTransaction();
		try {
			if (salePrices != null && salePrices.size() > 0) {
				for (ProductSalePrice productSalePrice : salePrices) {
					if (productSalePrice.getDbegindate().getTime() >= beginDate.getTime()
							&& endDate.getTime() >= productSalePrice.getDenddate().getTime()) {

						ProductSalePrice oldsalePrice = new ProductSalePrice();
						oldsalePrice.setIid(productSalePrice.getIid());
						oldsalePrice.setDenddate(new Timestamp(new Date().getTime()));
						oldsalePrice.setClistingid(dailyDealObject.getListing_id());
						oldsalePrice.setCsku(dailyDealObject.getSku());
						oldsalePrice.setFsaleprice(dailyDealObject.getSalePrice());
						oldsalePrice.setDbegindate(new Timestamp(beginDate.getTime()));
						oldsalePrice.setDenddate(new Timestamp(endDate.getTime()));
						oldsalePrice.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().update(oldsalePrice, tc);

					} else if (productSalePrice.getDbegindate().getTime() <= beginDate.getTime()
							&& endDate.getTime() <= productSalePrice.getDenddate().getTime()) {
						// ~ 更新
						ProductSalePrice oldsalePrice = new ProductSalePrice();
						oldsalePrice.setIid(productSalePrice.getIid());
						oldsalePrice.setDenddate(new Timestamp(new Date().getTime()));
						oldsalePrice.setClistingid(dailyDealObject.getListing_id());
						oldsalePrice.setCsku(dailyDealObject.getSku());
						oldsalePrice.setFsaleprice(dailyDealObject.getSalePrice());
						oldsalePrice.setDbegindate(productSalePrice.getDbegindate());
						oldsalePrice.setDenddate(new Timestamp(spiltbeginDate.getTime()));
						oldsalePrice.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().update(oldsalePrice, tc);

						// insert
						ProductSalePrice insertsalePricea = new ProductSalePrice();
						insertsalePricea.setClistingid(dailyDealObject.getListing_id());
						insertsalePricea.setCsku(dailyDealObject.getSku());
						insertsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						insertsalePricea.setFsaleprice(dailyDealObject.getSalePrice());
						insertsalePricea.setDbegindate(new Timestamp(beginDate.getTime()));
						insertsalePricea.setDenddate(new Timestamp(endDate.getTime()));
						insertsalePricea.setCcreateuser(foundationService.getCurrentMemberID());
						insertsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().save(insertsalePricea, tc);

						// ~ insert2
						ProductSalePrice spiltsalePricea = new ProductSalePrice();
						spiltsalePricea.setClistingid(dailyDealObject.getListing_id());
						spiltsalePricea.setCsku(dailyDealObject.getSku());
						spiltsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						spiltsalePricea.setFsaleprice(dailyDealObject.getSalePrice());
						spiltsalePricea.setDbegindate(new Timestamp(spiltendDate.getTime()));
						spiltsalePricea.setDenddate(productSalePrice.getDenddate());
						spiltsalePricea.setCcreateuser(foundationService.getCurrentMemberID());
						spiltsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().save(spiltsalePricea, tc);
					} else if (productSalePrice.getDbegindate().getTime() >= beginDate.getTime()
							&& endDate.getTime() <= productSalePrice.getDenddate().getTime()
							&& endDate.getTime() >= productSalePrice.getDbegindate().getTime()) {
						// ~ 更新
						ProductSalePrice oldsalePrice = new ProductSalePrice();
						oldsalePrice.setIid(productSalePrice.getIid());
						oldsalePrice.setDenddate(new Timestamp(new Date().getTime()));
						oldsalePrice.setClistingid(dailyDealObject.getListing_id());
						oldsalePrice.setCsku(dailyDealObject.getSku());
						oldsalePrice.setFsaleprice(dailyDealObject.getSalePrice());
						oldsalePrice.setDbegindate(new Timestamp(spiltendDate.getTime()));
						oldsalePrice.setDenddate(productSalePrice.getDenddate());
						oldsalePrice.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().update(oldsalePrice, tc);

						// insert
						ProductSalePrice insertsalePricea = new ProductSalePrice();
						insertsalePricea.setClistingid(dailyDealObject.getListing_id());
						insertsalePricea.setCsku(dailyDealObject.getSku());
						insertsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						insertsalePricea.setFsaleprice(dailyDealObject.getSalePrice());
						insertsalePricea.setDbegindate(new Timestamp(beginDate.getTime()));
						insertsalePricea.setDenddate(new Timestamp(endDate.getTime()));
						insertsalePricea.setCcreateuser(foundationService.getCurrentMemberID());
						insertsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().save(insertsalePricea, tc);
					} else if (productSalePrice.getDbegindate().getTime() <= beginDate.getTime()
							&& endDate.getTime() >= productSalePrice.getDenddate().getTime()
							&& beginDate.getTime() <= productSalePrice.getDenddate().getTime()) {
						// ~ 更新
						ProductSalePrice oldsalePrice = new ProductSalePrice();
						oldsalePrice.setIid(productSalePrice.getIid());
						oldsalePrice.setDenddate(new Timestamp(new Date().getTime()));
						oldsalePrice.setClistingid(dailyDealObject.getListing_id());
						oldsalePrice.setCsku(dailyDealObject.getSku());
						oldsalePrice.setFsaleprice(dailyDealObject.getSalePrice());
						oldsalePrice.setDbegindate(productSalePrice.getDbegindate());
						oldsalePrice.setDenddate(new Timestamp(spiltbeginDate.getTime()));
						oldsalePrice.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().update(oldsalePrice, tc);

						// insert
						ProductSalePrice insertsalePricea = new ProductSalePrice();
						insertsalePricea.setClistingid(dailyDealObject.getListing_id());
						insertsalePricea.setCsku(dailyDealObject.getSku());
						insertsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						insertsalePricea.setFsaleprice(dailyDealObject.getSalePrice());
						insertsalePricea.setDbegindate(new Timestamp(beginDate.getTime()));
						insertsalePricea.setDenddate(new Timestamp(endDate.getTime()));
						insertsalePricea.setCcreateuser(foundationService.getCurrentMemberID());
						insertsalePricea.setDcreatedate(new Timestamp(new Date().getTime()));
						ProductSalePrice.db().save(insertsalePricea, tc);
					}
				}
			} else {
				ProductSalePrice salePrice = new ProductSalePrice();
				salePrice.setClistingid(dailyDealObject.getListing_id());
				salePrice.setCsku(dailyDealObject.getSku());
				salePrice.setDenddate(new Timestamp(new Date().getTime()));
				salePrice.setFsaleprice(dailyDealObject.getSalePrice());
				salePrice.setDbegindate(new Timestamp(beginDate.getTime()));
				salePrice.setDenddate(new Timestamp(endDate.getTime()));
				salePrice.setCcreateuser(foundationService.getCurrentMemberID());
				salePrice.setDcreatedate(new Timestamp(new Date().getTime()));
				ProductSalePrice.db().save(salePrice, tc);
			}

			BeanUtils.copyProperties(dailyDeal, dailyDealObject);
			DailyDeal.db().save(dailyDeal);
			tc.commit();
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
			tc.rollback();
			result = 0;
		}
		return result;
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
		DailyDeal dailyDeal = null;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				dailyDeal = DailyDeal.db().find(DailyDeal.class, sourceStrArray[i]);
				dailyDeal.setIs_deleted(1);
				DailyDeal.db().save(dailyDeal);
				salePriceService.delete(dailyDeal.getListing_id(), dailyDeal.getStart_date());
				count++;
			}
		} else {
			dailyDeal = DailyDeal.db().find(DailyDeal.class, param);
			dailyDeal.setIs_deleted(1);
			DailyDeal.db().save(dailyDeal);
			salePriceService.delete(dailyDeal.getListing_id(), dailyDeal.getStart_date());
			count++;
		}
		return count;
	};

	/**
	 * 
	 * @Title: validateSkuAndStartDate
	 * @Description: TODO(验证sku同一天是否存在多个)
	 * @param @param
	 *            dailyDeal
	 * @param @return
	 *            参数
	 * @return List<DailyDeal> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月6日
	 */
	public List<DailyDeal> validateSkuAndStartDate(DailyDealObject dailyDealObject) {
		Timestamp datetime = new Timestamp(dailyDealObject.getStartTime_date().getTime());
		dailyDealObject.setStart_date(datetime);
		DailyDeal dailyDeal = new DailyDeal();
		List<DailyDeal> dailyDeals = null;
		try {
			BeanUtils.copyProperties(dailyDeal, dailyDealObject);
			dailyDeals = DailyDeal.db().find(DailyDeal.class).where().eq("sku", dailyDeal.getSku())
					.eq("start_date", dailyDeal.getStart_date()).eq("is_deleted", 0).findList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dailyDeals;
	};
}
