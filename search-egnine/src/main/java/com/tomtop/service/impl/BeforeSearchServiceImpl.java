package com.tomtop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.tomtop.entity.Filter;
import com.tomtop.entity.IndexEntity;
import com.tomtop.entity.PageBean;
import com.tomtop.entity.ProductTypeEntity;
import com.tomtop.service.IBeforeSearchService;
import com.tomtop.service.index.ISearchIndex;

/**
 * 索引查询
 * @author ztiny
 * @Date 2015-12-23
 */
@Component
public class BeforeSearchServiceImpl implements IBeforeSearchService {

	Logger logger = Logger.getLogger(BeforeSearchServiceImpl.class);
	
	@Resource(name="searchIndexImpl")
	private ISearchIndex searchIndexImpl;
	
	@Override
	public PageBean query(PageBean pageBean) {
		return searchIndexImpl.queryBykey(pageBean);
	}

	@Override
	public PageBean queryMoreLikeThis(PageBean bean) {
		IndexEntity index = searchIndexImpl.queryById(bean.getKeyword(), bean.getIndexName(), bean.getIndexType());
		ProductTypeEntity model = null;
		if(index!=null && index.getMutil()!=null && index.getMutil().getProductTypes()!=null){
			List<ProductTypeEntity> list = index.getMutil().getProductTypes();
			for (ProductTypeEntity productTypeEntity : list) {
				if(productTypeEntity.getLevel()==1){
					bean.setKeyword(index.getMutil().getTitle());
					model = productTypeEntity;
					break;
				}
			}
			if(StringUtils.isNotBlank(index.getSku())){
				Filter skuFilter = new Filter("sku",index.getSku(),"!=");
				bean.getFilters().add(skuFilter);
			}
			Filter listingIdFilter = new Filter("listingId",index.getListingId(),"!=");
			bean.getFilters().add(listingIdFilter);
			
			if(StringUtils.isNotBlank(index.getSpu())){
				Filter filter = new Filter("spu",index.getSpu(),"!=");
				bean.getFilters().add(filter);
			}
			Filter bMainFiler = new Filter("bmain",true,"&&");
			bean.getFilters().add(bMainFiler);
			Filter typefilter = new Filter("mutil.productTypes.productTypeId",model.getProductTypeId(),"&&");
			bean.getFilters().add(typefilter);
			
			bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
		}
		
		if(bean.getIndexs()==null || bean.getIndexs().size()<25){
			if(index!=null && index.getMutil()!=null){
				bean.setKeyword(index.getMutil().getTitle());
				bean = searchIndexImpl.queryMoreLikeThis(bean,null);
			}
		}
		return bean;
	}

	/**
	 * 根据listingid查询
	 * @param bean 里面有必填参数
	 * @return
	 */
	public PageBean queryByListingId(PageBean bean) {
		try{
			if(StringUtils.equals(bean.getKeyword(), "null")){
				throw new Exception ("关键字不能为空null字符串。。。。");
			}
			
			if(StringUtils.isNotBlank(bean.getKeyword()) && (bean.getKeyword().length()==36)){
				//先以listingId去查询
				IndexEntity index = searchIndexImpl.queryById(bean.getKeyword(), bean.getIndexName(), bean.getIndexType());
				if(index==null){
					//listingId没有查询到结果在以url去查询
					bean = searchIndexImpl.queryTermsAnyValue(bean, "mutil.url");
				}else{
					bean.getIndexs().add(index);
				}
			}else if(StringUtils.isNotBlank(bean.getKeyword()) && bean.getKeyword().length()<36 ||  StringUtils.isNotBlank(bean.getKeyword()) && bean.getKeyword().length()>36){
				bean = searchIndexImpl.queryTermsAnyValue(bean, "mutil.url");
				//如果url没有查询到结果，并且关键字的长度小于36位，则再以sku去查询
				if((bean.getIndexs()==null || bean.getIndexs().size()<1) && StringUtils.isNotBlank(bean.getKeyword()) && bean.getKeyword().length()<36){
					bean.setKeyword(bean.getKeyword().toUpperCase());
					bean = searchIndexImpl.queryTermsAnyValue(bean, "sku");
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return bean;
	}

	/**
	 * 首页查询
	 */
	@Override
	public PageBean queryHomePage(PageBean bean) {
		return searchIndexImpl.queryByIds(bean);
	}
	
	
	/**
	 * 热门推荐查询
	 */
	public PageBean queryHot(PageBean bean) {
		
		return searchIndexImpl.queryBykey(bean);
	}
	
	
	/**
	 * 一起查询多个listingid
	 * @param indexName	索引名称
	 * @param indexType	索引类型
	 * @param listingIds 集合
	 * @return
	 */
	public PageBean queryByIds(PageBean bean,String listingIds){
		return searchIndexImpl.queryByIds(bean);
	}

	@Override
	public List<IndexEntity> queryYouMightLike(PageBean bean) {
		IndexEntity index = searchIndexImpl.queryById(bean.getKeyword(), bean.getIndexName(), bean.getIndexType());
		ProductTypeEntity model = null;
		List<IndexEntity> indexs = null;
		int topProductTypeId =-1;
		if(index!=null && index.getMutil()!=null && index.getMutil().getProductTypes()!=null){
			//顶级类目的id
			List<ProductTypeEntity> list = index.getMutil().getProductTypes();
			for (ProductTypeEntity productTypeEntity : list) {
				if(topProductTypeId>0 && model!=null){
					break;
				}
				
				//取类目ID
				if(productTypeEntity.getLevel()==3 ){
					model = productTypeEntity;
				}else if(productTypeEntity.getLevel()==1 ){
					topProductTypeId = productTypeEntity.getProductTypeId();
				}
			}
			
			String keyword = "";
			if(index.getMutil()!=null && StringUtils.isNotBlank(index.getMutil().getTitle())){
				keyword = index.getMutil().getTitle();
			}
			bean.setKeyword(keyword);
			
			if(StringUtils.isNotBlank(index.getSku())){
				Filter skuFilter = new Filter("sku",index.getSku(),"!=");
				bean.getFilters().add(skuFilter);
			}
			Filter listingIdFilter = new Filter("listingId",index.getListingId(),"!=");
			bean.getFilters().add(listingIdFilter);
			
			if(StringUtils.isNotBlank(index.getSpu())){
				Filter filter = new Filter("spu",index.getSpu(),"!=");
				bean.getFilters().add(filter);
			}
			Filter bMainFiler = new Filter("bmain",true,"&&");
			bean.getFilters().add(bMainFiler);
			Filter typefilter = new Filter("mutil.productTypes.productTypeId",model==null?topProductTypeId:model.getProductTypeId(),"&&");
			bean.getFilters().add(typefilter);
			
			bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
			indexs = bean.getIndexs();
			if(indexs==null || indexs.size()<25){
				//补齐25条记录
				bean.setEndNum(25-(indexs==null?0:indexs.size()));
				//小于25条记录在此区查询
				List<Filter> filters = bean.getFilters();
				for (Filter filter : filters) {
					//根据顶级类目去查询
					if(filter.getPropetyName().equals("mutil.productTypes.productTypeId")){
						filter.setPropertyValue(topProductTypeId);
						break;
					}
				}
				bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				//结果集进行合并
				bean.getIndexs().addAll(indexs);
				indexs = bean.getIndexs();
//				//如果还小于25条
//				if(indexs ==null || indexs.size()<25){
//					bean.setEndNum(25-bean.getIndexs().size());
//					bean.setKeyword(index.getMutil().getTitle());
//					bean = searchIndexImpl.queryMoreLikeThis(bean,null);
//					bean.getIndexs().addAll(indexs);
//				}
			}
		}else{
			//直接根据标题
			bean.setKeyword(index.getMutil().getTitle());
			bean = searchIndexImpl.queryMoreLikeThis(bean,null);
			indexs = bean.getIndexs();
		}
		return indexs;
	}

	@Override
	public List<IndexEntity> queryYouRecentlyLike(PageBean bean) {
		IndexEntity index = searchIndexImpl.queryById(bean.getKeyword(), bean.getIndexName(), bean.getIndexType());
		ProductTypeEntity model = null;
		List<IndexEntity> indexs = null;
		
		if(index!=null && index.getMutil()!=null && index.getMutil().getProductTypes()!=null){
			//顶级类目的id
			int topProductTypeId = -1;
			List<ProductTypeEntity> list = index.getMutil().getProductTypes();
			for (ProductTypeEntity productTypeEntity : list) {
				if(topProductTypeId>0 && model!=null && topProductTypeId>0){
					break;
				}
				if(productTypeEntity.getLevel()==3){
					bean.setKeyword(index.getMutil().getTitle());
					model = productTypeEntity;
				}else if (productTypeEntity.getLevel()==1){
					topProductTypeId = productTypeEntity.getProductTypeId();
				}
			}
			if(StringUtils.isNotBlank(index.getSku())){
				Filter skuFilter = new Filter("sku",index.getSku(),"!=");
				bean.getFilters().add(skuFilter);
			}
			Filter listingIdFilter = new Filter("listingId",index.getListingId(),"!=");
			bean.getFilters().add(listingIdFilter);
			
			if(StringUtils.isNotBlank(index.getSpu())){
				Filter filter = new Filter("spu",index.getSpu(),"!=");
				bean.getFilters().add(filter);
			}
			Filter mutilTitleFilter = new Filter("mutil.title",index.getMutil().getTitle(),"!=");
			bean.getFilters().add(mutilTitleFilter);
			
			Filter bMainFiler = new Filter("bmain",true,"&&");
			bean.getFilters().add(bMainFiler);
			Filter typefilter = new Filter("mutil.productTypes.productTypeId",model==null?topProductTypeId:model.getProductTypeId(),"&&");
			bean.getFilters().add(typefilter);
			
			bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
			indexs = bean.getIndexs();
			if(indexs!=null){
				Filter fi = null;
				Map<String,String> strMap = new HashMap<String,String>();
				for (int i = 0; i <indexs.size(); i++) {
					if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
						fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
						bean.getFilters().add(fi);
						fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
						bean.getFilters().add(fi);
						if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
							fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
							bean.getFilters().add(fi);
						}
					}
					if(strMap.containsKey(indexs.get(i).getSpu())){
						indexs.remove(i);
					}else{
						strMap.put(indexs.get(i).getSpu(), indexs.get(i).getSpu());
					}
				}
			}
			if(indexs==null || indexs.size()<25){
				int num = 25-(indexs==null?0:indexs.size());
				//补齐25条记录
				bean.setEndNum(num);
				//小于25条记录在此区查询
				List<Filter> filters = bean.getFilters();
				Filter fil = null;
				for (int i = 0; i < filters.size(); i++) {
					fil = filters.get(i);
					//根据顶级类目去查询
					if(fil.getPropetyName().equals("mutil.productTypes.productTypeId")){
						fil.setPropertyValue(topProductTypeId);
						bean.getFilters().remove(i);
						bean.getFilters().add(i, fil);
						break;
					}
				}
			
				bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				//结果集进行合并
				//bean.getIndexs().addAll(bean.getIndexs());
				indexs = bean.getIndexs();
				if(indexs!=null){
					Filter fi = null;
					Map<String,String> strMap = new HashMap<String,String>();
					for (int i = 0; i <indexs.size(); i++) {
						if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
							fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
							bean.getFilters().add(fi);
							fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
							bean.getFilters().add(fi);
							if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
								fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
								bean.getFilters().add(fi);
							}
						}
						if(strMap.containsKey(indexs.get(i).getSpu())){
							indexs.remove(i);
						}else{
							strMap.put(indexs.get(i).getSpu(), indexs.get(i).getSpu());
						}
					}
				}
				//如果还小于25条
				if(indexs ==null ||indexs.size()<25){
					bean.setEndNum(25-bean.getIndexs().size());
					if(indexs!=null){
						Filter fi = null;
						for (int i = 0; i <indexs.size(); i++) {
							if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
								fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
								bean.getFilters().add(fi);
								fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
								bean.getFilters().add(fi);
								if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
									fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
									bean.getFilters().add(fi);
								}
							}
						}
					}
					String[] ks = bean.getKeyword().split("\\ ");
					if(ks != null && ks.length > 2){
						String ky = "";
						int fc = ks.length / 2;
						for (int i = 0; i < fc; i++) {
							ky += ks[i] + " ";
						}
						bean.setKeyword(ky);
					}
					bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				}
			}
		}else{
			//直接根据标题
			bean.setKeyword(bean.getKeyword());
			Filter listingIdFilter = new Filter("listingId",bean.getKeyword(),"!=");
			bean.getFilters().add(listingIdFilter);
			Filter bMainFiler = new Filter("bmain",true,"&&");
			bean.getFilters().add(bMainFiler);
			bean = searchIndexImpl.queryMoreLikeThis(bean,"listingId");
			indexs = bean.getIndexs();
		}
		return indexs;
	}

	@Override
	public List<IndexEntity> queryMoreLikeForCustomersItem(PageBean bean) {
		IndexEntity index = searchIndexImpl.queryById(bean.getKeyword(), bean.getIndexName(), bean.getIndexType());
		ProductTypeEntity model = null;
		List<IndexEntity> indexs = null;
		
		if(index!=null && index.getMutil()!=null && index.getMutil().getProductTypes()!=null){
			//顶级类目的id
			int topProductTypeId = -1;
			List<ProductTypeEntity> list = index.getMutil().getProductTypes();
			for (ProductTypeEntity productTypeEntity : list) {
				if(topProductTypeId>0 && model!=null ){
					break;
				}
				if(productTypeEntity.getLevel()==2){
					bean.setKeyword(index.getMutil().getTitle());
					model = productTypeEntity;
				}else if (productTypeEntity.getLevel()==1){
					topProductTypeId = productTypeEntity.getProductTypeId();
				}
			}
			if(StringUtils.isNotBlank(index.getSku())){
				Filter skuFilter = new Filter("sku",index.getSku(),"!=");
				bean.getFilters().add(skuFilter);
			}
			Filter listingIdFilter = new Filter("listingId",index.getListingId(),"!=");
			bean.getFilters().add(listingIdFilter);
			
			if(StringUtils.isNotBlank(index.getSpu())){
				Filter filter = new Filter("spu",index.getSpu(),"!=");
				bean.getFilters().add(filter);
			}
			Filter mutilTitleFilter = new Filter("mutil.title",index.getMutil().getTitle(),"!=");
			bean.getFilters().add(mutilTitleFilter);
			
			Filter bMainFiler = new Filter("bmain",true,"&&");
			bean.getFilters().add(bMainFiler);
			Filter typefilter = new Filter("mutil.productTypes.productTypeId",model==null?topProductTypeId:model.getProductTypeId(),"&&");
			bean.getFilters().add(typefilter);
			
			bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
			indexs = bean.getIndexs();
			if(indexs!=null){
				Filter fi = null;
				Map<String,String> strMap = new HashMap<String,String>();
				for (int i = 0; i <indexs.size(); i++) {
					if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
						fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
						bean.getFilters().add(fi);
						fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
						bean.getFilters().add(fi);
						if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
							fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
							bean.getFilters().add(fi);
						}
					}
					if(strMap.containsKey(indexs.get(i).getSpu())){
						indexs.remove(i);
					}else{
						strMap.put(indexs.get(i).getSpu(), indexs.get(i).getSpu());
					}
				}
			}
			if(indexs==null || indexs.size()<25){
				int num = 25-(indexs==null?0:indexs.size());
				//补齐25条记录
				bean.setEndNum(num);
				//小于25条记录在此区查询
				List<Filter> filters = bean.getFilters();
				Filter fil = null;
				for (int i = 0; i < filters.size(); i++) {
					fil = filters.get(i);
					//根据顶级类目去查询
					if(fil.getPropetyName().equals("mutil.productTypes.productTypeId")){
						fil.setPropertyValue(topProductTypeId);
						bean.getFilters().remove(i);
						bean.getFilters().add(i, fil);
						break;
					}
				}
			
				bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				//结果集进行合并
				//bean.getIndexs().addAll(bean.getIndexs());
				indexs = bean.getIndexs();
				if(indexs!=null){
					Filter fi = null;
					Map<String,String> strMap = new HashMap<String,String>();
					for (int i = 0; i <indexs.size(); i++) {
						if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
							fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
							bean.getFilters().add(fi);
							fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
							bean.getFilters().add(fi);
							if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
								fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
								bean.getFilters().add(fi);
							}
						}
						if(strMap.containsKey(indexs.get(i).getSpu())){
							indexs.remove(i);
						}else{
							strMap.put(indexs.get(i).getSpu(), indexs.get(i).getSpu());
						}
					}
				}
				//如果还小于25条
				if(indexs ==null ||indexs.size()<25){
					bean.setEndNum(25-bean.getIndexs().size());
					if(indexs!=null){
						Filter fi = null;
						for (int i = 0; i <indexs.size(); i++) {
							if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
								fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
								bean.getFilters().add(fi);
								fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
								bean.getFilters().add(fi);
								if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
									fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
									bean.getFilters().add(fi);
								}
							}
						}
					}
					String[] ks = bean.getKeyword().split("\\ ");
					if(ks != null && ks.length > 2){
						String ky = "";
						int fc = ks.length / 2;
						for (int i = 0; i < fc; i++) {
							ky += ks[i] + " ";
						}
						bean.setKeyword(ky);
					}
					bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				}
			}
		}else{
			//直接根据标题
			bean.setKeyword(index.getMutil().getTitle());
			bean = searchIndexImpl.queryMoreLikeThis(bean,null);
			indexs = bean.getIndexs();
		}
		
		return indexs;
	}


	@Override
	public List<IndexEntity> queryMoreLikeForCustomersViewed(PageBean bean) {
		IndexEntity index = searchIndexImpl.queryById(bean.getKeyword(), bean.getIndexName(), bean.getIndexType());
		ProductTypeEntity model = null;
		List<IndexEntity> indexs = null;
		
		if(index!=null && index.getMutil()!=null && index.getMutil().getProductTypes()!=null){
			//顶级类目的id
			int topProductTypeId = -1;
			List<ProductTypeEntity> list = index.getMutil().getProductTypes();
			for (ProductTypeEntity productTypeEntity : list) {
				if(topProductTypeId>0 && model!=null){
					break;
				}
				if(productTypeEntity.getLevel()==2){
					bean.setKeyword(index.getMutil().getTitle());
					model = productTypeEntity;
				}else if (productTypeEntity.getLevel()==1){
					topProductTypeId = productTypeEntity.getProductTypeId();
				}
			}
			
			//过滤掉重复数据，此处过滤sku、lingtingId、spu主要是因为数据问题
			if(StringUtils.isNotBlank(index.getSku())){
				Filter skuFilter = new Filter("sku",index.getSku(),"!=");
				bean.getFilters().add(skuFilter);
			}
			Filter listingIdFilter = new Filter("listingId",index.getListingId(),"!=");
			bean.getFilters().add(listingIdFilter);
			
			if(StringUtils.isNotBlank(index.getSpu())){
				Filter filter = new Filter("spu",index.getSpu(),"!=");
				bean.getFilters().add(filter);
			}
			
			Filter mutilTitleFilter = new Filter("mutil.title",index.getMutil().getTitle(),"!=");
			bean.getFilters().add(mutilTitleFilter);
			
			Filter bMainFiler = new Filter("bmain",true,"&&");
			bean.getFilters().add(bMainFiler);
			Filter typefilter = new Filter("mutil.productTypes.productTypeId",model==null?topProductTypeId:model.getProductTypeId(),"&&");
			bean.getFilters().add(typefilter);
			
			bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
			indexs = bean.getIndexs();
			if(indexs!=null){
				Filter fi = null;
				Map<String,String> strMap = new HashMap<String,String>();
				for (int i = 0; i <indexs.size(); i++) {
					if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
						fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
						bean.getFilters().add(fi);
						fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
						bean.getFilters().add(fi);
						if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
							fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
							bean.getFilters().add(fi);
						}
					}
					if(strMap.containsKey(indexs.get(i).getSpu())){
						indexs.remove(i);
					}else{
						strMap.put(indexs.get(i).getSpu(), indexs.get(i).getSpu());
					}
				}
			}
			if(indexs==null || indexs.size()<25){
				int num = 25-(indexs==null?0:indexs.size());
				//补齐25条记录
				bean.setEndNum(num);
				//小于25条记录在此区查询
				List<Filter> filters = bean.getFilters();
				Filter fil = null;
				for (int i = 0; i < filters.size(); i++) {
					fil = filters.get(i);
					//根据顶级类目去查询
					if(fil.getPropetyName().equals("mutil.productTypes.productTypeId")){
						fil.setPropertyValue(topProductTypeId);
						bean.getFilters().remove(i);
						bean.getFilters().add(i, fil);
						break;
					}
				}
			
				bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				//结果集进行合并
				//bean.getIndexs().addAll(bean.getIndexs());
				indexs = bean.getIndexs();
				if(indexs!=null){
					Filter fi = null;
					Map<String,String> strMap = new HashMap<String,String>();
					for (int i = 0; i <indexs.size(); i++) {
						if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
							fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
							bean.getFilters().add(fi);
							fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
							bean.getFilters().add(fi);
							if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
								fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
								bean.getFilters().add(fi);
							}
						}
						if(strMap.containsKey(indexs.get(i).getSpu())){
							indexs.remove(i);
						}else{
							strMap.put(indexs.get(i).getSpu(), indexs.get(i).getSpu());
						}
					}
				}
				//如果还小于25条
				if(indexs ==null ||indexs.size()<25){
					bean.setEndNum(25-bean.getIndexs().size());
					if(indexs!=null){
						Filter fi = null;
						for (int i = 0; i <indexs.size(); i++) {
							if(indexs.get(i) != null && indexs.get(i).getListingId() != null){
								fi = new Filter("listingId",indexs.get(i).getListingId(),"!=");
								bean.getFilters().add(fi);
								fi = new Filter("mutil.title",indexs.get(i).getMutil().getTitle(),"!=");
								bean.getFilters().add(fi);
								if(StringUtils.isNotBlank(indexs.get(i).getSpu())){
									fi = new Filter("spu",indexs.get(i).getSpu(),"!=");
									bean.getFilters().add(fi);
								}
							}
						}
					}
					String[] ks = bean.getKeyword().split("\\ ");
					if(ks != null && ks.length > 2){
						String ky = "";
						int fc = ks.length / 2;
						for (int i = 0; i < fc; i++) {
							ky += ks[i] + " ";
						}
						bean.setKeyword(ky);
					}
					bean = searchIndexImpl.queryMoreLikeThis(bean,"mutil.title");
				}
			}
		}else{
			//直接根据标题
			bean.setKeyword(index.getMutil().getTitle());
			bean = searchIndexImpl.queryMoreLikeThis(bean,null);
			indexs = bean.getIndexs();
		}
		return indexs;
	}

}
