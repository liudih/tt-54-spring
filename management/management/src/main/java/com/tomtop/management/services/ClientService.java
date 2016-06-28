package com.tomtop.management.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.PlatForm;
import com.tomtop.management.ebean.manage.model.Site;
import com.tomtop.management.service.model.BaseClientObject;

@Service
public class ClientService {

	public Page<BaseClientObject> getCliPage(int pageNo, int pageLimit, Client client) {
		String q = " find Client where is_deleted=0 ";
		if (null != client.getName() && !client.getName().equals("")) {
			q += "and name like '%" + client.getName() + "%'";
		}
		Page<BaseClientObject> mPage = new Page<BaseClientObject>();

		PagedList<Client> pagedList = Client.db().createQuery(Client.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
		pagedList.loadRowCount();
		mPage.setCount(pagedList.getTotalRowCount());
		mPage.setPageNo(pageNo);
		mPage.setLimit(pageLimit);
		List<BaseClientObject> cliList = new ArrayList<BaseClientObject>();
		for (Client c : pagedList.getList()) {
			BaseClientObject bObject = new BaseClientObject();
			try {
				BeanUtils.copyProperties(bObject, c);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cliList.add(bObject);
		}
		for (BaseClientObject bObject : cliList) {
			if (null != bObject.getSite_id()) {
				Site site = Site.db().find(Site.class, bObject.getSite_id());
				bObject.setSite(site);
			}
			if (null != bObject.getPlatform()) {
				PlatForm platform = PlatForm.db().find(PlatForm.class, bObject.getPlatform());
				if (null != platform) {
					bObject.setParameter(platform);
				}

			}
			bObject.setCreateTime(DateUtil.dateFormat(bObject.getWhenCreated()));
			bObject.setUpdateTime(DateUtil.dateFormat(bObject.getWhenModified()));
		}
		mPage.setList(cliList);
		return mPage;
	}

	/**
	 * 
	 * @Title: addClient
	 * @Description: TODO新增客户端
	 * @param @param
	 *            client
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	public int addClient(Client client) {
		int count = 0;
		client.setIs_deleted(0);
		Client.db().save(client);
		count = 1;
		return count;
	}

	/**
	 * 
	 * @Title: updateClient
	 * @Description: 根据主键修改信息
	 * @param @param
	 *            client
	 * @param @return
	 *            参数
	 * @return int 返回类型
	 * @throws @author
	 *             liuxin
	 * @date 2015年12月17日
	 */
	public int updateClient(Client client) {
		int count = 0;
		Client cc = Client.db().find(Client.class, client.getId());
		cc.setName(client.getName());
		cc.setPlatform(client.getPlatform());
		cc.setRemark(client.getRemark());
		cc.setIs_enabled(client.getIs_enabled());
		cc.setSite_id(client.getSite_id());
		Client.db().update(cc);
		count = 1;
		return count;
	}

	public int checkName(Integer id, String name) {
		int findCount = 0;
		if (id == 0) {
			findCount = Client.db().find(Client.class).where().eq("name", name).findRowCount();
		} else {
			Client client = Client.db().find(Client.class, id);
			if (!client.getName().equals(name)) {
				findCount = Client.db().find(Client.class).where().eq("name", name).findRowCount();
			}
		}

		return findCount;
	}

	public int checkOther(Integer id, Integer siteId, Integer platId) {
		int findCount = 0;
		if (id == 0) {
			findCount = Client.db().find(Client.class).where().eq("site_id", siteId).eq("platform", platId)
					.findRowCount();
		} else {
			Client client = Client.db().find(Client.class, id);
			if (client.getSite_id() != siteId || client.getPlatform() != platId) {
				findCount = Client.db().find(Client.class).where().eq("site_id", siteId).eq("platform", platId)
						.findRowCount();
			}
		}

		return findCount;
	}
}
