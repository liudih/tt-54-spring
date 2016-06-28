package com.tomtop.management.services;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.PagedList;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.config.PersistBatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.tomtop.management.common.DateUtil;
import com.tomtop.management.common.Page;
import com.tomtop.management.common.StringUtil;
import com.tomtop.management.ebean.manage.model.BaseParameter;
import com.tomtop.management.ebean.manage.model.Client;
import com.tomtop.management.ebean.manage.model.Language;
import com.tomtop.management.ebean.manage.model.Layout;
import com.tomtop.management.ebean.manage.model.LoyoutModul;
import com.tomtop.management.ebean.manage.model.ModulContent;
import com.tomtop.management.homepage.service.values.RecommendValue;
import com.tomtop.management.homepage.service.values.SkuContentValue;
import com.tomtop.management.service.model.LayoutModelObject;
import com.tomtop.management.service.model.RecommendObject;

@Service
public class RecommendService {

	@Autowired
	CommonService commonService;

	@Autowired
	ProductService productService;

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	public Page<LayoutModelObject> getLoyoutModulPage(int pageNo,
			int pageLimit, RecommendObject recommend) {
		Page<LayoutModelObject> mPage = new Page<LayoutModelObject>();
		String q = " find LoyoutModul where is_deleted=0 ";
		if (null != recommend) {
			if (StringUtil.isNotEmpty(recommend.getClients())) {
				q += " and client_id in (" + recommend.getClients() + ")";
			} else {
				List<Client> clients = commonService.getAllClient();
				String cList = "";
				if (null != clients && clients.size() > 0) {
					for (int i = 0; i < clients.size(); i++) {
						if (i == 0) {
							cList = clients.get(0).getId().toString();
						} else {
							cList += "," + clients.get(i).getId();
						}
					}
					q += " and client_id in (" + cList + ")";
				}
			}
			if (StringUtil.isNotEmpty(recommend.getLanguages())) {
				q += " and language_id in (" + recommend.getLanguages() + ")";
			}
			if (null != recommend.getLayout_id()
					&& !recommend.getLayout_id().equals("")) {
				q += " and layout_id = " + recommend.getLayout_id();
			}
			if (null != recommend.getName() && !recommend.getName().equals("")) {
				q += " and name like '%" + recommend.getName() + "%'";
			}
		}
		PagedList<LoyoutModul> pagedList = ModulContent.db()
				.createQuery(LoyoutModul.class, q).order().desc("whenModified")
				.findPagedList(pageNo - 1, pageLimit);
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
				oo.setLanguage(Language.db().find(Language.class,
						oo.getLanguage_id()));
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
				List<BaseParameter> pp = BaseParameter.db()
						.find(BaseParameter.class).where()
						.eq("value", p.getValue())
						.eq("type", "LAYOUT-POSITION").findList();
				if (pp != null && pp.size() > 0) {
					oo.setParameter(pp.get(0));
				}
			}
			oo.setCreateTime(DateUtil.dateFormat(oo.getWhenCreated()));
			oo.setUpdateTime(DateUtil.dateFormat(oo.getWhenModified()));
		}
		mPage.setList(lObjects);
		return mPage;

	}

	public List<LoyoutModul> checkModuleName(RecommendValue recommend) {
		List<LoyoutModul> modul = LoyoutModul.db().find(LoyoutModul.class)
				.where().in("client_id", recommend.getClients())
				.in("language_id", recommend.getLanguages())
				.eq("code", recommend.getCode()).eq("is_deleted", 0).findList();
		return modul;
	}

	public void insert(List<LoyoutModul> fcsList, Transaction paramTransaction)
			throws IOException {
		LoyoutModul.db().insertAll(fcsList, paramTransaction);
	}

	public boolean Insert(RecommendValue recommend) throws Exception {
		Transaction ts2 = null;
		try {

			ts2 = ModulContent.db().beginTransaction();
			ts2.setBatchSize(50);
			ts2.setBatch(PersistBatch.ALL);
			ts2.setBatchOnCascade(PersistBatch.INSERT);
			ts2.setBatchGetGeneratedKeys(false);
			List<LoyoutModul> list = new ArrayList<LoyoutModul>();
			Layout layout = Layout.db().find(Layout.class,
					recommend.getLayout_id());
			recommend.getClients().forEach(p -> {
				recommend.getLanguages().forEach(l -> {
					LoyoutModul layoutModul = new LoyoutModul();
					layoutModul.setCode(recommend.getCode());
					layoutModul.setIs_enabled(recommend.getIs_enabled());
					layoutModul.setLayout_id(recommend.getLayout_id());
					layoutModul.setLayout_code(layout.getCode());
					layoutModul.setNumber(recommend.getNumber());
					layoutModul.setSort(recommend.getSort());
					layoutModul.setPosition_id(recommend.getPosition_id());
					layoutModul.setUrl(recommend.getUrl());
					layoutModul.setName(recommend.getName());
					layoutModul.setClient_id(p);
					layoutModul.setLanguage_id(l);
					layoutModul.setIs_deleted(0);
					list.add(layoutModul);
				});
			});
			this.insert(list, ts2);
			if (null != recommend.getSkuContentValues()
					&& recommend.getSkuContentValues().size() != 0) {
				List<SkuContentValue> skus = fixRepeatSkus(recommend
						.getSkuContentValues());
				List<ModulContent> modulContents = new ArrayList<ModulContent>();
				recommend
						.getClients()
						.forEach(
								p -> {
									recommend
											.getLanguages()
											.forEach(
													l -> {
														LoyoutModul loyoutModul = LoyoutModul
																.db()
																.find(LoyoutModul.class)
																.where()
																.eq("code",
																		recommend
																				.getCode())
																.eq("client_id",
																		p)
																.eq("language_id",
																		l)
																.eq("is_deleted",
																		0)
																.findUnique();
														skus.forEach(s -> {
															ModulContent modulContent = new ModulContent();
															modulContent
																	.setIs_enabled(s
																			.getIs_enabled());
															modulContent.setSort(s
																	.getSort());
															modulContent.setSku(s
																	.getSku());
															modulContent
																	.setClient_id(p);
															modulContent
																	.setIs_deleted(0);
															modulContent
																	.setLayout_code(layout
																			.getCode());
															modulContent
																	.setLayout_module_code(recommend
																			.getCode());
															modulContent
																	.setLayout_module_id(loyoutModul
																			.getId()
																			.intValue());
															modulContent
																	.setLayout_id(recommend
																			.getLayout_id());
															modulContent
																	.setLanguage_id(l);
															modulContent
																	.setIs_show(1);
															modulContent
																	.setListing_id(s
																			.getListingId());
															modulContent
																	.setCategory_id(0);
															modulContents
																	.add(modulContent);
														});
													});
								});

				ModulContent.db().insertAll(modulContents, ts2);
			}
			ts2.commit();
			return true;
		} catch (Exception ex) {
			logger.error("RecommendService insert error ->", ex);
			ts2.rollback();
			throw ex;
		}
	}

	/**
	 * 
	    * @Title: fixRepeatSkus
	    * @Description: 处理重复listingid
	    * @param @param ttskus
	    * @param @return    参数
	    * @return List<SkuContentValue>    返回类型
	    * @throws
		* @author Administrator
	    * @date 2016年3月26日
	 */
	private List<SkuContentValue> fixRepeatSkus(List<SkuContentValue> ttskus) {
		List<SkuContentValue> skus = Lists.newArrayList();
		Multimap<String, SkuContentValue> skumaps = Multimaps.index(ttskus,
				p -> p.getListingId());
		skumaps.keySet().forEach(
				li -> {
					if (skumaps.get(li).size() > 1) {
						skumaps.get(li).forEach(
								plis -> {
									SkuContentValue sv = new SkuContentValue();
									sv.setId(plis.getId());
									sv.setIs_enabled(sv.getIs_enabled());
									sv.setListingId(productService
											.getListingIdBySku(sv.getSku()));
									sv.setSku(sv.getSku());
									sv.setSort(sv.getSort());
									skus.add(sv);
								});

					} else {
						skus.addAll(skumaps.get(li));
					}
				});
		return skus;
	}

	public boolean update(RecommendValue recommend) throws Exception {
		Transaction ts2 = null;
		try {
			if (recommend == null || recommend.getClient_id() == null
					|| recommend.getLanguage_id() == null) {
				return false;
			}
			ts2 = ModulContent.db().beginTransaction();
			ts2.setBatchSize(50);
			ts2.setBatchGetGeneratedKeys(false);
			Layout layout = Layout.db().find(Layout.class,
					recommend.getLayout_id());
			LoyoutModul layoutModul = new LoyoutModul();
			layoutModul.setId(recommend.getId());
			layoutModul.setCode(recommend.getCode());
			layoutModul.setIs_enabled(recommend.getIs_enabled());
			layoutModul.setLayout_id(recommend.getLayout_id());
			layoutModul.setLayout_code(layout.getCode());
			layoutModul.setNumber(recommend.getNumber());
			layoutModul.setSort(recommend.getSort());
			layoutModul.setPosition_id(recommend.getPosition_id());
			layoutModul.setUrl(recommend.getUrl());
			layoutModul.setName(recommend.getName());
			layoutModul.setClient_id(recommend.getClient_id());
			layoutModul.setLanguage_id(recommend.getLanguage_id());
			layoutModul.setIs_deleted(0);

			LoyoutModul.db().update(layoutModul, ts2);

			List<ModulContent> mcs = ModulContent.db().find(ModulContent.class)
					.where().eq("is_deleted", 0)
					.eq("layout_module_id", recommend.getId()).findList();
			Multimap<String, ModulContent> dbMaps = null;
			if (mcs != null && mcs.size() > 0) {
				dbMaps = Multimaps.index(mcs, p -> p.getSku());
			}
			Multimap<String, ModulContent> newMaps = dbMaps;
			List<ModulContent> fixList = com.google.common.collect.FluentIterable
					.from(recommend.getSkuContentValues())
					.transform(
							p -> {
								ModulContent mc = new ModulContent();
								mc.setClient_id(recommend.getClient_id());
								if (newMaps != null
										&& newMaps.containsKey(p.getSku())) {
									ModulContent dbmc = (ModulContent) newMaps
											.get(p.getSku()).toArray()[0];
									mc.setId(dbmc.getId());
								}
								mc.setIs_deleted(0);
								mc.setIs_enabled(p.getIs_enabled());
								mc.setLanguage_id(recommend.getLanguage_id());
								mc.setLayout_code(layout.getCode());
								mc.setLayout_id(recommend.getLayout_id());
								mc.setLayout_module_code(recommend.getCode());
								mc.setLayout_module_id(recommend.getId()
										.intValue());
								mc.setListing_id(p.getListingId());
								mc.setSku(p.getSku());
								mc.setCategory_id(0);
								mc.setIs_show(1);
								mc.setSort(p.getSort());
								return mc;
							}).toList();

			List<ModulContent> updatelists = Lists.newArrayList();
			List<ModulContent> updateList = com.google.common.collect.FluentIterable
					.from(fixList).filter(p -> p.getId() != null).toList();
			List<String> keys = Lists.transform(
					recommend.getSkuContentValues(), p -> p.getSku());
			List<ModulContent> deleteList = com.google.common.collect.FluentIterable
					.from(mcs).filter(p -> keys.contains(p.getSku()) == false)
					.transform(p -> {
						p.setIs_deleted(1);
						return p;
					}).toList();
			if (updateList != null) {
				updatelists.addAll(updateList);
			}
			if (deleteList != null) {
				updatelists.addAll(deleteList);
			}
			if (updatelists.size() > 0) {
				ModulContent.db().updateAll(updatelists, ts2);
			}
			List<ModulContent> insertList = com.google.common.collect.FluentIterable
					.from(fixList).filter(p -> p.getId() == null).toList();
			if (insertList != null && insertList.size() > 0) {
				ModulContent.db().insertAll(insertList, ts2);
			}

			ts2.commit();
			return true;
		} catch (Exception ex) {
			logger.error("RecommendService update error ->", ex);
			ts2.rollback();
			throw ex;
		}
	}

	public List<ModulContent> getById(ModulContent modulContent) {
		List<ModulContent> modulContents = ModulContent.db()
				.find(ModulContent.class).where()
				.eq("layout_module_id", modulContent.getLayout_module_id())
				.eq("is_deleted", 0).findList();
		return modulContents;
	}

}
