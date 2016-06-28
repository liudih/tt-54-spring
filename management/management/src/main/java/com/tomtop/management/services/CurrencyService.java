package com.tomtop.management.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.base.model.BaseCurrency;
import com.tomtop.management.ebean.base.model.BaseCurrencyRate;
import com.tomtop.management.ebean.manage.model.Currency;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.service.model.CurrencyObject;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @ClassName: CurrencyService
 * @Description: TODO(汇率管理服务类)
 * @author Administrator
 * @date 2015年12月22日
 *
 */
@Service
public class CurrencyService {

	/**
	 * 
	 * @Title: getcurrencyPage
	 * @Description: TODO(查询布局的所有信息)
	 * @param @param
	 *            pageNo
	 * @param @param
	 *            pageLimit
	 * @param @param
	 *            layout
	 * @param @return
	 *            参数
	 * @return Page<LayoutModel> 返回类型
	 * @throws @author
	 *             guozy
	 * @date 2015年12月16日
	 */
	public Page<CurrencyObject> getcurrencyPage(int pageNo, int pageLimit, Currency currency) {
		currency.setName(currency.getName() == "" ? null : currency.getName());
		currency.setCode(currency.getCode() == "" ? null : currency.getCode());
		Page<CurrencyObject> currencyPage = new Page<CurrencyObject>();
		String query = "find Currency where  is_deleted=0 ";
		if (null != currency.getName()) {
			query += " and name like '%" + currency.getName() + "%'";
		}
		if (null != currency.getCode()) {
			query += " and code like '%" + currency.getCode() + "%'";
		}
		PagedList<Currency> currencyPageList = Currency.db().createQuery(Currency.class, query).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		currencyPageList.loadRowCount();
		currencyPage.setCount(currencyPageList.getTotalRowCount());
		currencyPage.setLimit(pageLimit);
		currencyPage.setPageNo(pageNo);
		List<CurrencyObject> currencyObjects = new ArrayList<CurrencyObject>();
		for (Currency curren : currencyPageList.getList()) {
			CurrencyObject currencyObj = new CurrencyObject();
			try {
				BeanUtils.copyProperties(currencyObj, curren);
				currencyObjects.add(currencyObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (CurrencyObject currencyObj : currencyObjects) {

			currencyObj.setCreateTime(DateUtil.dateFormat(currencyObj.getWhenCreated()));
			currencyObj.setUpdateTime(DateUtil.dateFormat(currencyObj.getWhenModified()));
			currencyObj.setSynchroTime(DateUtil.dateFormat(currencyObj.getSynchro_date()));
			if (currencyObj.getIs_enabled() != null) {
				if (currencyObj.getIs_enabled() == 0) {
					currencyObj.setEnabledStatus("Disabled");
				} else {
					currencyObj.setEnabledStatus("Enabled");
				}
			}
			if (currencyObj.getSymbol_positions() != null) {
				if (currencyObj.getSymbol_positions() == 1) {
					currencyObj.setSymbolPositionStr("Left");
				} else {
					currencyObj.setSymbolPositionStr("Right");
				}
			}
		}

		currencyPage.setList(currencyObjects);
		return currencyPage;
	}

	/**
	 * 
	 * @Title: getCurrencyId
	 * @Description: TODO( 根据编号，获取汇率的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Layout 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public Currency getCurrencyId(int id) {
		Currency currency = Currency.db().find(Currency.class, id);
		return currency;
	};

	/**
	 * 
	 * @Title: validateName
	 * @Description: TODO(验证布局名称)
	 * @param @param
	 *            name
	 * @param @return
	 *            参数
	 * @return List<Layout> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月22日
	 */
	public List<Currency> validateName(String name) {
		return Currency.db().find(Currency.class).where().eq("name", name).eq("is_deleted", 0).findList();
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
	 * @date 2015年12月22日
	 */
	public int delById(String param) {
		Currency currency = null;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				currency = Currency.db().find(Currency.class, sourceStrArray[i]);
				currency.setIs_deleted(1);
				try {
					Layout.db().save(currency);
					deleteCurrencyAndRate(currency);
					count++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			currency = Currency.db().find(Currency.class, param);
			currency.setIs_deleted(1);
			try {
				Layout.db().save(currency);
				deleteCurrencyAndRate(currency);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 
	 * @Title: addCurrency
	 * @Description: TODO(新增货币)
	 * @param @param
	 *            currency
	 * @param @throws
	 *            Exception
	 * @return void
	 * @throws @author
	 *             yinfei
	 * @date 2016年1月18日
	 */
	public void addCurrency(Currency currency) throws Exception {
		Currency.db().insert(currency);
		BaseCurrency baseCurrency = BaseCurrency.db().find(BaseCurrency.class).where().eq("ccode", currency.getCode())
				.findUnique();
		if (null == baseCurrency) {
			BaseCurrency bc = new BaseCurrency();
			bc.setBshow(true);
			bc.setCcode(currency.getCode());
			bc.setCsymbol(currency.getSymbol_code());
			bc.setCversionsid(null);
			bc.setFexchangerate(currency.getCurrent_rate());
			BaseCurrency.db().insert(bc);
			BaseCurrencyRate bcr = new BaseCurrencyRate();
			bcr.setBuse(true);
			bcr.setCcode(currency.getCode());
			bcr.setFexchangerate(currency.getCurrent_rate());
			BaseCurrencyRate.db().insert(bcr);
		}
	}

	/**
	 * 
	 * @Title: updateCurrency
	 * @Description: TODO(修改货币)
	 * @param @param
	 *            currency
	 * @param @throws
	 *            Exception
	 * @return void
	 * @throws @author
	 *             yinfei
	 * @date 2016年1月18日
	 */
	public void updateCurrency(Currency currency) throws Exception {
		Currency.db().update(currency);
		BaseCurrency baseCurrency = BaseCurrency.db().find(BaseCurrency.class).where().eq("ccode", currency.getCode())
				.findUnique();
		if (null != baseCurrency) {
			baseCurrency.setFexchangerate(currency.getCurrent_rate());
			BaseCurrency.db().update(baseCurrency);
		} else {
			BaseCurrency bc = new BaseCurrency();
			bc.setBshow(true);
			bc.setCcode(currency.getCode());
			bc.setCsymbol(currency.getSymbol_code());
			bc.setCversionsid(null);
			bc.setFexchangerate(currency.getCurrent_rate());
			BaseCurrency.db().insert(bc);
		}
		BaseCurrencyRate baseCurrencyRate = BaseCurrencyRate.db().find(BaseCurrencyRate.class).where()
				.eq("ccode", currency.getCode()).eq("buse", true).findUnique();
		if (null != baseCurrencyRate) {
			baseCurrencyRate.setFexchangerate(currency.getCurrent_rate());
			BaseCurrencyRate.db().update(baseCurrencyRate);
		} else {
			BaseCurrencyRate bcr = new BaseCurrencyRate();
			bcr.setBuse(true);
			bcr.setCcode(currency.getCode());
			bcr.setFexchangerate(currency.getCurrent_rate());
			BaseCurrencyRate.db().insert(bcr);
		}
	};

	/**
	 * 
	 * @Title: deleteCurrencyAndRate
	 * @Description: TODO(删除货币和货币汇率)
	 * @param @param
	 *            currency
	 * @param @throws
	 *            Exception
	 * @return void
	 * @throws @author
	 *             yinfei
	 * @date 2016年1月18日
	 */
	private void deleteCurrencyAndRate(Currency currency) throws Exception {
		BaseCurrency baseCurrency = BaseCurrency.db().find(BaseCurrency.class).where().eq("ccode", currency.getCode())
				.findUnique();
		if (null != baseCurrency) {
			BaseCurrency.db().delete(baseCurrency);
		}
		BaseCurrencyRate baseCurrencyRate = BaseCurrencyRate.db().find(BaseCurrencyRate.class).where()
				.eq("ccode", currency.getCode()).eq("buse", true).findUnique();
		if (null != baseCurrencyRate) {
			baseCurrencyRate.setBuse(false);
			BaseCurrencyRate.db().update(baseCurrencyRate);
		}
	}

}
