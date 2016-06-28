package com.tomtop.service.index.filters;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.tomtop.common.DateUtil;
import com.tomtop.entity.Constant;
import com.tomtop.entity.Filter;

public class Filters  {
	
	
	
	//多个过滤条件
	public List<Filter> filters ;
	//单个过滤条件
	public Filter filter;
	
	public BoolQueryBuilder boolQuery =  QueryBuilders.boolQuery();
	
	public Filters (List<Filter> filters){
		this.filters = filters;
	}
	
	public Filters(Filter filter){
		this.filter = filter;
	}
	
	
	public void addFilter(Filter filter){
		if(filter==null || !filter.isFilter()){
			return ;
		}
		String proName = filter.getPropetyName();
		if(StringUtils.equals("tagsName.tagName",proName)){
			if(StringUtils.equals(filter.getPropertyValue().toString().toLowerCase(), "onsale")){
				//onsale代表价格有促销
				boolQuery.must(QueryBuilders.rangeQuery("promotionPrice.beginDate").format(Constant.PATTERN).lte(DateUtil.getUTCTimeStr()))
						 .must(QueryBuilders.rangeQuery("promotionPrice.endDate").format(Constant.PATTERN).gte(DateUtil.getUTCTimeStr()));
			}else if(StringUtils.equals(filter.getPropertyValue().toString().toLowerCase(), "freeshipping")){
				//freeshipping allfreeshipping 任何一个都匹配都可以
				boolQuery.must(QueryBuilders.termsQuery("tagsName.tagName", "freeshipping","allfreeshipping"));
			}
		}else{
			String proValues[]={};
			Object proValue = filter.getPropertyValue();
			if(proValue!=null && !(proValue instanceof Boolean)){
				proValues = proValue.toString().split(",");
			}
			if(StringUtils.equals(Filter.character.and.get(),filter.getExpress()) ||StringUtils.equals(Filter.character.eq.get(),filter.getExpress()) ){
				//判断是一个属性对应多个值，如果是则用should，且必须满足一个条件
				if(proValues.length>1){
					for (String pvalue : proValues) {
						boolQuery.should(QueryBuilders.termQuery(proName,pvalue));
					}
					boolQuery.minimumNumberShouldMatch(1);
				}else{
						//如果单个属性过滤，则用must匹配
						boolQuery.must(QueryBuilders.termQuery(proName,proValue));
				}
			}else if(StringUtils.equals(Filter.character.or.get(),filter.getExpress())){
				if(proValues.length>1){
					for (String pvalue : proValues) {
						boolQuery.should(QueryBuilders.termQuery(proName,pvalue));
					}
				}else{
					boolQuery.should(QueryBuilders.termQuery(proName,filter.getPropertyValue()));
				}
			}else if(StringUtils.equals(Filter.character.not.get(),filter.getExpress())){
				if(proValues.length>1){
					for (String pvalue : proValues) {
						boolQuery.mustNot(QueryBuilders.termQuery(proName,pvalue));
					}
				}else{
					boolQuery.mustNot(QueryBuilders.termQuery(filter.getPropetyName(),filter.getPropertyValue()));
				}
			}else if(StringUtils.equals(Filter.character.ge.get(),filter.getExpress())){
				 boolQuery.filter(QueryBuilders.rangeQuery(filter.getPropetyName()).gte(filter.getPropertyValue()));
			}else if(StringUtils.equals(Filter.character.gt.get(),filter.getExpress())){
				 boolQuery.filter(QueryBuilders.rangeQuery(filter.getPropetyName()).gt(filter.getPropertyValue()));
			}else if(StringUtils.equals(Filter.character.lt.get(),filter.getExpress())){
				 boolQuery.filter(QueryBuilders.rangeQuery(filter.getPropetyName()).lt(filter.getPropertyValue()));
			}else if(StringUtils.equals(Filter.character.le.get(),filter.getExpress())){
				 boolQuery.filter(QueryBuilders.rangeQuery(filter.getPropetyName()).lte(filter.getPropertyValue()));
			}
		}
	}
	
	
	
	/**
	 * 添加过滤条件
	 * @param list 过滤条件
	 * @return
	 */
	public BoolQueryBuilder buildQueryBuilderByFilters(){
		if(this.getFilters()==null || this.getFilters().size()<0){
			return null;
		}
		this.getFilters().forEach(filter -> {
			this.addFilter(filter);
		});
		return boolQuery;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public BoolQueryBuilder getBoolQuery() {
		return boolQuery;
	}

	public void setBoolQuery(BoolQueryBuilder boolQuery) {
		this.boolQuery = boolQuery;
	}
	
	
}
