package com.tomtop.management.homepage.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.homepage.model.NewestReview;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.service.model.NewestReviewObject;
import com.tomtop.management.services.CommonService;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @ClassName: NewestReviewService
 * @Description: TODO(最新评论服务类)
 * @author Guozy
 * @date 2015年12月24日
 *
 */
@Service
public class NewestReviewService {

	@Autowired
	CommonService commonService;

	public Page<NewestReviewObject> getNewestReviewPage(int pageNo, int pageLimit, NewestReview newestReview,
			String countrys, String clients, String languages) {
		Page<NewestReviewObject> newestReviewPage = new Page<NewestReviewObject>();
		String query = "find NewestReview where  is_deleted=0 ";
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
		if (StringUtil.isNotEmpty(countrys)) {
			query += " and country in ('" + countrys + "') ";
		}
		if (null != newestReview.getSku()) {
			query += " and sku like '%" + newestReview.getSku() + "%'";
		}
		if (null != newestReview.getReview_by()) {
			query += " and review_by like '%" + newestReview.getReview_by() + "%'";
		}
		PagedList<NewestReview> newestReviewPageList = NewestReview.db().createQuery(NewestReview.class, query)
				.order().desc("whenModified").findPagedList(pageNo - 1, pageLimit);
		newestReviewPageList.loadRowCount();
		newestReviewPage.setCount(newestReviewPageList.getTotalRowCount());
		newestReviewPage.setLimit(pageLimit);
		newestReviewPage.setPageNo(pageNo);
		List<NewestReviewObject> newestReviewObject = new ArrayList<NewestReviewObject>();
		for (NewestReview newest : newestReviewPageList.getList()) {
			NewestReviewObject newestRobj = new NewestReviewObject();
			try {
				BeanUtils.copyProperties(newestRobj, newest);
				newestReviewObject.add(newestRobj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (NewestReviewObject newsObj : newestReviewObject) {
			if (newsObj.getClient_id() != null) {
				newsObj.setClient(Client.db().find(Client.class, newsObj.getClient_id()));
			}
			if (newsObj.getLanguage_id() != null) {
				newsObj.setLanguage(Language.db().find(Language.class, newsObj.getLanguage_id()));
			}
			newsObj.setCreateTime(DateUtil.dateFormat(newsObj.getWhenCreated()));
			newsObj.setUpdateTime(DateUtil.dateFormat(newsObj.getWhenModified()));
			if (newsObj.getIs_enabled() != null) {
				if (newsObj.getIs_enabled() == 0) {
					newsObj.setEnabledStatus("Disabled");
				} else {
					newsObj.setEnabledStatus("Enabled");
				}
			}
		}

		newestReviewPage.setList(newestReviewObject);
		return newestReviewPage;
	}

	/**
	 * 
	 * @Title: getBannerId
	 * @Description: TODO( 根据编号，获取最新评论的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Banner 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public NewestReview getNewestReviewId(int id) {
		NewestReview newestReview = NewestReview.db().find(NewestReview.class, id);
		return newestReview;
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
	public List<NewestReview> validateCode(String dailyDeal) {
		return NewestReview.db().find(NewestReview.class).where().eq("is_deleted", 0).eq("code", dailyDeal).findList();
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
		NewestReview newestReview = null;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				newestReview = NewestReview.db().find(NewestReview.class, sourceStrArray[i]);
				newestReview.setIs_deleted(1);
				NewestReview.db().save(newestReview);
				count++;
			}
		} else {
			newestReview = NewestReview.db().find(NewestReview.class, param);
			newestReview.setIs_deleted(1);
			NewestReview.db().save(newestReview);
			count++;
		}
		return count;
	};

}
