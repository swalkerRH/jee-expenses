package com.expenses.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.expenses.annotation.CurrentFilter;
import com.expenses.filter.FilterOption;

@Stateless
public class FilterService {
	
	@Inject
	@CurrentFilter
	private Event<FilterOption> filterEventSrc;
	
	public void setFilterOption(FilterOption i){
		filterEventSrc.fire(i);
	}
}
