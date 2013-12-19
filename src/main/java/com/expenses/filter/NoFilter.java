package com.expenses.filter;

import java.util.List;

import javax.enterprise.inject.Default;

import com.expenses.model.Expense;

@Default
public class NoFilter extends FilterOption<Object> {

	private String filterName = "None";
	
	public void setFilterName(String name){
		filterName = name;
	}
	
	public String getFilterName(){
		return filterName;
	}
	
	@Override
	public List<Expense> filter(List<Expense> expenses) {
		return expenses;
	}

	@Override
	public Object getFilterBy() {
		return "";
	}

	@Override
	public void setFilterBy(Object obj) {
		// Intentionally left blank
		
	}
	
}
