package com.expenses.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.expenses.filter.CategoryFilter;
import com.expenses.filter.DateFilter;
import com.expenses.filter.DescriptionFilter;
import com.expenses.filter.FilterOption;
import com.expenses.filter.NoFilter;

@SessionScoped
public class FilterProvider implements Serializable{
	
	private List<FilterOption<? extends Object>> filterOptions;
	
	@Produces
	@Named("filters")
	public List<FilterOption<? extends Object>> getFilters(){
		return filterOptions;
	}
	
	@PostConstruct
	public void initFilterOptions(){
		filterOptions = new ArrayList<FilterOption<? extends Object>>(4);
		filterOptions.add(new NoFilter());
		filterOptions.add(new CategoryFilter());
		filterOptions.add(new DescriptionFilter());
		filterOptions.add(new DateFilter());
	}
}
