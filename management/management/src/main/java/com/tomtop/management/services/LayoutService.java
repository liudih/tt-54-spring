package com.tomtop.management.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.config.CurrentUser;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.service.model.LayoutObject;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @ClassName: LayoutService
 * @Description: TODO(布局服务类)
 * @author Guozy
 * @date 2015年12月16日
 *
 */
@Service
public class LayoutService {

	@Autowired
	CurrentUser currentUser;
	@Autowired
	CommonService commonService;

	/**
	 * 
	 * @param clients
	 * @Title: getLayoutPage
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
	public Page<LayoutObject> getLayoutPage(int pageNo, int pageLimit, Layout layout, String clients,
			String languages) {
		Page<LayoutObject> layoutPage = new Page<LayoutObject>();
		String query = "find Layout where is_deleted=0 ";
		/*
		 * if (null != layout.getClient_id()) { query += " and client_id = " +
		 * layout.getClient_id(); }
		 */
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
		if (null != layout.getName()) {
			query += " and name like '%" + layout.getName() + "%'";
		}

		PagedList<Layout> layoutPageList = Layout.db().createQuery(Layout.class, query).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		layoutPageList.loadRowCount();
		layoutPage.setCount(layoutPageList.getTotalRowCount());
		layoutPage.setLimit(pageLimit);
		layoutPage.setPageNo(pageNo);
		List<LayoutObject> layoutModels = new ArrayList<LayoutObject>();
		for (Layout layo : layoutPageList.getList()) {
			LayoutObject lay = new LayoutObject();
			try {
				BeanUtils.copyProperties(lay, layo);
				layoutModels.add(lay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (LayoutObject layoutModel : layoutModels) {
			if (layoutModel.getClient_id() != null) {
				layoutModel.setClient(Client.db().find(Client.class, layoutModel.getClient_id()));
			}
			if (layoutModel.getLanguage_id() != null) {
				layoutModel.setLanguage(Language.db().find(Language.class, layoutModel.getLanguage_id()));
			}
			layoutModel.setCreateTime(DateUtil.dateFormat(layoutModel.getWhenCreated()));
			layoutModel.setUpdateTime(DateUtil.dateFormat(layoutModel.getWhenModified()));
			if (layoutModel.getIs_enabled() != null) {
				if (layoutModel.getIs_enabled() == 0) {
					layoutModel.setEnabledStatus("Disabled");
				} else {
					layoutModel.setEnabledStatus("Enabled");
				}
			}
		}

		layoutPage.setList(layoutModels);
		return layoutPage;
	}

	/**
	 * 
	 * @Title: getLayoutId
	 * @Description: TODO( 根据编号，获取布局的数据信息)
	 * @param @param
	 *            id
	 * @param @return
	 *            参数
	 * @return Layout 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2015年12月16日
	 */
	public Layout getLayoutId(int id) {
		Layout layout = Layout.db().find(Layout.class, id);
		return layout;
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
		Layout layout = null;
		int count = 0;
		if (param.indexOf(",") != -1) {
			String[] sourceStrArray = param.split(",");
			for (int i = 0; i < sourceStrArray.length; i++) {
				layout = Layout.db().find(Layout.class, sourceStrArray[i]);
				layout.setIs_deleted(1);
				Layout.db().save(layout);
				count++;
			}
		} else {
			layout = Layout.db().find(Layout.class, param);
			layout.setIs_deleted(1);
			Layout.db().save(layout);
			count++;
		}
		return count;
	}

	/**
	 * 
	 * @Title: addLayout
	 * @Description: TODO(新增布局)
	 * @param @param
	 *            layout
	 * @param @param
	 *            clients
	 * @return void
	 * @throws @author
	 *             yinfei
	 * @date 2016年1月7日
	 */
	public void addLayout(Layout layout, String clients) {
		List<Layout> layoutList = new ArrayList<Layout>();
		if (clients.indexOf(",") != -1) {
			String[] clientIds = clients.split(",");
			for (int i = 0; i < clientIds.length; i++) {
				Layout l = new Layout();
				l.setClient_id(Integer.parseInt(clientIds[i]));
				l.setIs_deleted(layout.getIs_deleted());
				l.setCode(layout.getCode());
				l.setDescription(layout.getDescription());
				l.setIs_enabled(layout.getIs_enabled());
				l.setKeyword(layout.getKeyword());
				l.setLanguage_id(layout.getLanguage_id());
				l.setName(layout.getName());
				l.setRemark(layout.getRemark());
				l.setTitle(layout.getTitle());
				l.setUrl(layout.getUrl());
				l.setWhenCreated(new Timestamp(new Date().getTime()));
				l.setWhenModified(new Timestamp(new Date().getTime()));
				l.setWhoCreated(currentUser.currentUser().toString());
				l.setWhoModified(currentUser.currentUser().toString());
				layoutList.add(l);
			}
		} else {
			Layout l = new Layout();
			l.setIs_deleted(layout.getIs_deleted());
			l.setClient_id(Integer.parseInt(clients));
			l.setCode(layout.getCode());
			l.setDescription(layout.getDescription());
			l.setIs_enabled(layout.getIs_enabled());
			l.setKeyword(layout.getKeyword());
			l.setLanguage_id(layout.getLanguage_id());
			l.setName(layout.getName());
			l.setRemark(layout.getRemark());
			l.setTitle(layout.getTitle());
			l.setUrl(layout.getUrl());
			l.setWhenCreated(new Timestamp(new Date().getTime()));
			l.setWhenModified(new Timestamp(new Date().getTime()));
			l.setWhoCreated(currentUser.currentUser().toString());
			l.setWhoModified(currentUser.currentUser().toString());
			layoutList.add(l);
		}
		Layout.db().insertAll(layoutList);
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
	 * @date 2015年12月15日
	 */
	public List<Layout> validateLayout(Layout layout, String clients) {
		List<Layout> layouts = new ArrayList<Layout>();
		if (layout.getId() != null) {
			Layout lay = Layout.db().find(Layout.class, layout.getId());
			if (lay.getClient_id().equals(layout.getClient_id()) && lay.getLanguage_id().equals(layout.getLanguage_id())
					&& lay.getName().equals(layout.getName())) {
			} else {
				layouts = Layout.db().find(Layout.class).where().eq("client_id", layout.getClient_id())
						.eq("language_id", layout.getLanguage_id()).eq("name", layout.getName()).findList();
			}
		} else {
			String query = "find Layout where 1 = 1 and is_deleted=0 ";
			if (StringUtil.isNotEmpty(clients)) {
				query += " and client_id in (" + clients + ") ";
			}
			if (null != layout.getLanguage_id()) {
				query += " and language_id = " + layout.getLanguage_id();
			}
			if (null != layout.getName()) {
				query += " and name='" + layout.getName() + "'";
			}
			layouts = Layout.db().createQuery(Layout.class, query).orderBy("id desc").findList();
		}
		return layouts;
	};

	/**
	 * 
	 * @param clients 
	 * @Title: validateCode
	 * @Description: TODO(通过布局标识验证，布局标识是否存在)
	 * @param @return
	 *            参数
	 * @return List<Layout> 返回类型
	 * @throws @author
	 *             Guozy
	 * @date 2016年1月18日
	 */
	public List<Layout> validateCode(Layout layout, String clients) {
		return Layout.db().find(Layout.class).where().eq("code", layout.getCode()).in("client_id", clients)
				.eq("is_deleted", 0).findList();
	};
}
