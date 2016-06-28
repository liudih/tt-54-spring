package com.tomtop.service.index.aggs;

import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.RangeBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomtop.common.BaseServiceUtil;
import com.tomtop.common.DateUtil;
import com.tomtop.entity.Filter;
import com.tomtop.entity.RangeAggregation;
import com.tomtop.entity.RemoteAttributeEntity;

public class BuildAggregation {

	/**isAgg 为true，则进行聚合*/
	private List<Filter> filters;
	
	/**价格聚合对象*/
	private RangeAggregation rangeAgg;
	
	private List<RangeAggregation> rangeAggs;
	
	private RangeBuilder rangeBuilder;
	
	private int productTypeId;
	
	private SearchRequestBuilder searchBuilder;
	
	public BuildAggregation(List<Filter>  filters,int productTypeId){
		this.filters = filters;
		this.productTypeId = productTypeId;
	}
	
	public BuildAggregation(RangeAggregation rangeAgg){
		this.rangeAgg = rangeAgg;
	}
	
	public BuildAggregation(List<Filter>  filters,RangeAggregation rangeAgg,int productTypeId){
		this.filters = filters;
		this.rangeAgg = rangeAgg;
		this.productTypeId = productTypeId;
	}

	
	/**
	 * 聚合
	 * @param requestBuilder
	 * @param filters
	 * @return
	 */
	public SearchRequestBuilder addAggreationgBuilders(SearchRequestBuilder requestBuilder,List<Filter> filters,List<RangeAggregation> aggs,int productTypeId){
		if(filters==null || filters.size()<1){
			return requestBuilder;
		}
		for (Filter filter : filters) {
			if(filter.isAgg()){
				if(filter.getPropetyName().equals("mutil.items.value")){
					//根据类目找到所有的key
					Map<String,List<RemoteAttributeEntity>> map = BaseServiceUtil.getAllShowKeyByProductTypeId(productTypeId);
					//所有的value
					Map<String,List<String>> valuesMap = BaseServiceUtil.getAllShowValues();
					if(map!=null && map.size()>0){
						//遍历所有的key进行聚合
						for (Map.Entry<String, List<RemoteAttributeEntity>> entry : map.entrySet()) {
							if(entry.getValue()!=null){
								Object obj = entry.getValue();
								JSONArray arr = (JSONArray)obj;
								for (Object object : arr) {
									String type = ((JSONObject)object).get("attributeValue").toString();
									//根据key找到所有value进行聚合
									List<String> values = valuesMap.get(type);
									if(values!=null && values.size()>0){
										FiltersAggregationBuilder aggregation =  AggregationBuilders.filters(type);
										for (String v : values) {
											aggregation.filter(v, QueryBuilders.termQuery("mutil.items.value", v));
										}
										requestBuilder.addAggregation(aggregation);
									}
								}
							}
						}
					}
				}else if (filter.getPropetyName().equals("tagsName.tagName")){
					
					String currentDate = DateUtil.getUTCTimeStr();
					String pattern = "yyyy-MM-dd HH:mm";
					BoolQueryBuilder bool = QueryBuilders.boolQuery();
					bool.must(QueryBuilders.rangeQuery("promotionPrice.beginDate").format(pattern).lte(currentDate))
						.must(QueryBuilders.rangeQuery("promotionPrice.endDate").format(pattern).gte(currentDate));
					
					FiltersAggregationBuilder aggregation =  AggregationBuilders.filters("tagsName.tagName");
					aggregation.filter("onSale", bool);
					aggregation.filter("freeshipping",QueryBuilders.termsQuery(filter.getPropetyName(),"freeshipping","allfreeshipping"));
					requestBuilder.addAggregation(aggregation);
					
					
//					FilterAggregationBuilder filterBuilder = AggregationBuilders.filter("onSale").filter(bool);
//					requestBuilder.addAggregation(filterBuilder);
//					
//					//tagsName标签
//					TermsBuilder builder = AggregationBuilders.terms(filter.getPropetyName()).field(filter.getPropetyName()).order(Terms.Order.term(true)).size(2000);
//					requestBuilder.addAggregation(builder);

					
				}else{
					TermsBuilder builder = AggregationBuilders.terms(filter.getPropetyName()).field(filter.getPropetyName()).order(Terms.Order.term(true)).size(2000);
					requestBuilder.addAggregation(builder);
				}
			}
		}
		
		

		
		return requestBuilder;
	}
	
	
	
	public SearchRequestBuilder handlerMutilValue(Filter filter){
		if(!filter.getPropetyName().equals("mutil.items.value")){
			return  searchBuilder ;
		}
		
		//根据类目找到所有的key
		Map<String,List<RemoteAttributeEntity>> map = BaseServiceUtil.getAllShowKeyByProductTypeId(this.getProductTypeId());
		//所有的value
		Map<String,List<String>> valuesMap = BaseServiceUtil.getAllShowValues();
		if(map!=null && map.size()>0){
			//遍历所有的key进行聚合
			for (Map.Entry<String, List<RemoteAttributeEntity>> entry : map.entrySet()) {
				if(entry.getValue()!=null){
					Object obj = entry.getValue();
					JSONArray arr = (JSONArray)obj;
					arr.forEach( a ->{
						String type = ((JSONObject)a).get("attributeValue").toString();
						//根据key找到所有value进行聚合
						List<String> values = valuesMap.get(type);
						if(values!=null && values.size()>0){
							FiltersAggregationBuilder aggregation =  AggregationBuilders.filters(type);
							for (String v : values) {
								aggregation.filter(v, QueryBuilders.termQuery("mutil.items.value", v));
							}
							this.searchBuilder.addAggregation(aggregation);
						}
					});
					 
				}
			}
		}
		return  searchBuilder ;
	}
	
	
	
	
	
	public SearchRequestBuilder addRangeAggs(List<RangeAggregation> aggs){
		if(aggs==null || !aggs.isEmpty()){
			return this.searchBuilder;
		}
		aggs.forEach(agg ->{
			this.addRangeAgg(agg);
		});
		return this.searchBuilder;
	}
	
	
	public SearchRequestBuilder addRangeAgg(RangeAggregation rangeAgg){
		//价格聚合
		Double from = rangeAgg.getFrom()!=null?rangeAgg.getFrom():null;
		Double to = rangeAgg.getTo()!=null?rangeAgg.getTo():null;
		String key = rangeAgg.getAliasName();
		RangeBuilder builder = AggregationBuilders.range(key).field(rangeAgg.getName());
		if(from!=null && to!=null){
			builder.addRange(from, to);
		}else if(from!=null &&( to==null || to==0)){
			builder.addUnboundedFrom(from);
		}
		return this.searchBuilder.addAggregation(builder);
	}
	
	
	
	public List<RangeAggregation> getRangeAggs() {
		return rangeAggs;
	}

	public void setRangeAggs(List<RangeAggregation> rangeAggs) {
		this.rangeAggs = rangeAggs;
	}

	public RangeBuilder getRangeBuilder() {
		return rangeBuilder;
	}

	public void setRangeBuilder(RangeBuilder rangeBuilder) {
		this.rangeBuilder = rangeBuilder;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public RangeAggregation getRangeAgg() {
		return rangeAgg;
	}

	public void setRangeAgg(RangeAggregation rangeAgg) {
		this.rangeAgg = rangeAgg;
	}

	public SearchRequestBuilder getSearchBuilder() {
		return searchBuilder;
	}

	public void setSearchBuilder(SearchRequestBuilder searchBuilder) {
		this.searchBuilder = searchBuilder;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}
	
	
}
