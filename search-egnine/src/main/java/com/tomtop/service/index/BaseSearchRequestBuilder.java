package com.tomtop.service.index;

import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;

import com.tomtop.entity.Filter;
import com.tomtop.service.index.filters.Filters;

public  class BaseSearchRequestBuilder  {

	public SearchRequestBuilder searchBuilder;
	
	public List<Filter> filters;
	
	public BaseSearchRequestBuilder(SearchRequestBuilder searchBuilder){
		this.searchBuilder = searchBuilder;
	}
	
	
	public BaseSearchRequestBuilder(SearchRequestBuilder searchBuilder,List<Filter> filters){
		this.searchBuilder = searchBuilder;
		this.filters = filters;
	}

	
	public SearchRequestBuilder filters(){
		Filters filter = new Filters(this.getFilters());
		searchBuilder.setQuery(filter.buildQueryBuilderByFilters());
		return searchBuilder;
	}


	public List<Filter> getFilters() {
		return filters;
	}


	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	
	
	
	
}
