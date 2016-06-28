package com.tomtop.management.handler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.WebUtil;
import com.tomtop.management.config.GlobalParameter;
import com.tomtop.management.event.AdvertCacheEvent;
import com.tomtop.management.event.BaseCacheEvent;
import com.tomtop.management.event.EventType;
import com.tomtop.management.event.ProductCacheEvent;
import com.tomtop.management.event.SalePriceCacheEvent;

@Component
public class CacheClearHandler {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	GlobalParameter globalParameter;

	/**
	 * 
	 * @Title: clearBaseModuleCache
	 * @Description: 清理基础信息缓存
	 * @param @param event 参数
	 * @return void 返回类型
	 * @throws @author fcl
	 * @date 2015年12月30日
	 */
	@Subscribe
	public void clearBaseModuleCache(final BaseCacheEvent event) {
		try {
			String result = "no";
			if (event.getEventType() != EventType.Query) {
				String url = String.format(globalParameter
						.getCacheBaseClearUrl(), event.getTableName().trim());

				log.debug("clear base cache Handle Event : {}", url);
				result = WebUtil.executeGet(url);
			}
			log.info("run base cache handle Handle Event : {}", result);
		} catch (Exception ex) {
			log.error("clear base cache Handle Event error: ", ex);
		}
	}

	/**
	 * 
	 * @Title: clearProductModuleCache
	 * @Description: 清理产品信息缓存
	 * @param @param event 参数
	 * @return void 返回类型
	 * @throws @author fcl
	 * @date 2015年12月30日
	 */
	@Subscribe
	public void clearProductModuleCache(final ProductCacheEvent event) {
		try {
			String result = "no";
			if (event.getEventType() != EventType.Query) {
				String url = String
						.format(globalParameter.getCacheProductClearUrl(),
								event.getTableName().trim());
				log.info("clear base cache Handle Event : {}", url);
				result = WebUtil.executeGet(url);
			}
			// /cache/v1/clean/
			log.info("run Product cache handle Handle Event : {}", result);
		} catch (Exception ex) {
			log.error("clear Product cache Handle Event error: ", ex);
		}
	}

	/**
	 * @Title: clearAdvertiModuleCache
	 * @Description: 清理广告缓存
	 * @param @param event 参数
	 * @return void 返回类型
	 * @throws @author fcl
	 * @date 2015年12月30日
	 */
	@Subscribe
	public void clearAdvertModuleCache(final AdvertCacheEvent event) {
		try {
			String result = "no";
			if (event.getEventType() != EventType.Query) {
				String url = String.format(globalParameter
						.getCacheAdvertClearUrl(), event.getTableName().trim());
				log.debug("clear Advert cache Handle Event : {}", url);
				result = WebUtil.executeGet(url);
			}
			log.info("run Advert cache handle Handle Event : {}", result);
		} catch (Exception ex) {
			log.error("clear Advert cache Handle Event error: ", ex);
		}
	}

	@Subscribe
	public void clearPriceModuleCache(final SalePriceCacheEvent event) {
		try {
			/*
			 * String result = "no"; if (event.getEventType() !=
			 * EventType.Query) { String url =
			 * String.format(globalParameter.getCachePriceClearUrl(),
			 * event.getListingId().trim());
			 * log.debug("clear Advert cache Handle Event : {}", url);
			 * Map<String, String> map = new HashMap<String, String>();
			 * map.put(event.getListingId(), event.getListingId());
			 * map.put("beginDate",
			 * DateUtil.getStrFromYYYYMMDDHHMMSS(event.getdBegindate()));
			 * map.put("endDate",
			 * DateUtil.getStrFromYYYYMMDDHHMMSS(event.getdBegindate()));
			 * map.put("salePrice", String.format("%.2f", event.getPrice()));
			 * map.put("listingId", event.getdEndDate().toString()); result =
			 * WebUtil.post(url, map); }
			 */
			String result = "no";
			if (event.getEventType() != EventType.Query) {
				String url = String.format(globalParameter
						.getCachePriceClearUrl(), event.getListingId().trim());
				log.debug("clear saveprice cache Handle Event : {}", url);
				result = WebUtil.executeGet(url);
			}
			log.info("run Advert cache handle Handle Event : {}", result);
		} catch (Exception ex) {
			log.error("clear Advert cache Handle Event error: ", ex);
		}
	}
}
