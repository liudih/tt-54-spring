package com.tomtop.management.services;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.google.common.collect.Lists;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.ebean.manage.model.ModulContent;
import com.tomtop.management.ebean.product.model.Product;
import com.tomtop.management.service.model.LayoutModelObject;

@Service
public class LayoutEnquiryService {

	public String getName() {
		List<Layout> list = Lists.newArrayList();
		List<Product> plist = Lists.newArrayList();
		try {
			list = Layout.db().find(Layout.class).findList();
			plist = Product.db().find(Product.class).findList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(list.size() + plist.size());
	}

	public Page<LayoutModelObject> getLoyoutModulPage(int pageNo, int pageLimit, LoyoutModul modul) {
		Page<LayoutModelObject> mPage = new Page<LayoutModelObject>();
		String q = "find LoyoutModul where is_deleted=0 ";
		if (null != modul.getClient_id()) {
			q += " and client_id = " + modul.getClient_id();
		}
		if (null != modul.getLanguage_id()) {
			q += " and language_id =" + modul.getLanguage_id();
		}
		if (null != modul.getLayout_id()) {
			q += " and layout_id =" + modul.getLayout_id();
		}
		if (null != modul.getPosition_id()) {
			q += " and position_id=" + modul.getPosition_id();
		}
		if (null != modul.getName() && !modul.getName().equals("")) {
			q += " and name like '%" + modul.getName()+ "%'";
		}
		PagedList<LoyoutModul> pagedList = ModulContent.db().createQuery(LoyoutModul.class, q).orderBy("last_updated_by").findPagedList(pageNo - 1,
				pageLimit);
		pagedList.loadRowCount();
		mPage.setCount(pagedList.getTotalRowCount());
		mPage.setPageNo(pageNo);
		mPage.setLimit(pageLimit);
		List<LayoutModelObject> lObjects = new ArrayList<LayoutModelObject>();
		for (LoyoutModul mm : pagedList.getList()) {
			LayoutModelObject outObj = new LayoutModelObject();
			try {
				BeanUtils.copyProperties(outObj, mm);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lObjects.add(outObj);
		}
		for (LayoutModelObject oo : lObjects) {
			if (null != oo.getClient_id()) {
				oo.setClient(Client.db().find(Client.class, oo.getClient_id()));
			}
			if (null != oo.getLanguage_id()) {
				oo.setLanguage(Language.db().find(Language.class, oo.getLanguage_id()));
			}
			if (null != oo.getLayout_id()) {
				Layout l = new Layout();
				Long id = Long.valueOf(oo.getLayout_id());
				l.setId(id);
				oo.setLayout(Layout.db().find(Layout.class, l.getId()));
			}
			if (null != oo.getPosition_id()) {
				BaseParameter p = new BaseParameter();
				p.setValue(oo.getPosition_id());
				List<BaseParameter> pp = BaseParameter.db().find(BaseParameter.class).where().eq("value", p.getValue())
						.eq("type", "LAYOUT-POSITION").findList();
				if (pp != null && pp.size() >0) {
					oo.setParameter(pp.get(0));
				}
			}
			oo.setCreateTime(DateUtil.dateFormat(oo.getWhenCreated()));
			oo.setUpdateTime(DateUtil.dateFormat(oo.getWhenModified()));
		}
		mPage.setList(lObjects);
		return mPage;
	}

	public int getAddReturn(LoyoutModul modul) {
		Layout layout = Layout.db().find(Layout.class, modul.getLayout_id());
		if (null != layout) {
			modul.setLayout_code(layout.getCode());
		}
		int count = 0;
		modul.setIs_deleted(0);
		LoyoutModul.db().save(modul);
		count = 1;
		return count;
	}

	public int getUpdateReturn(LoyoutModul m) {
		int count = 0;
		LoyoutModul modul = LoyoutModul.db().find(LoyoutModul.class, m.getId());
		modul.setClient_id(m.getClient_id());
		modul.setLanguage_id(m.getLanguage_id());
		modul.setLayout_id(m.getLayout_id());
		modul.setName(m.getName());
		modul.setCode(m.getCode());
		modul.setSort(m.getSort());
		modul.setNumber(m.getNumber());
		modul.setIs_enabled(m.getIs_enabled());
		modul.setPosition_id(m.getPosition_id());
		modul.setUrl(m.getUrl());
		LoyoutModul.db().update(modul);
		count = 1;
		return count;
	}

	public List<LoyoutModul> checkModuleName(String name) {
		List<LoyoutModul> modul = LoyoutModul.db().find(LoyoutModul.class).where().eq("name", name).eq("is_deleted", 0)
				.findList();
		return modul;
	}
}
