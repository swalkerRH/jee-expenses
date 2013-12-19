package com.expenses.filter;

import java.util.ArrayList;
import java.util.List;

import com.expenses.model.Expense;

public class DescriptionFilter extends FilterOption<String> {
	
	private String filterName = "Description";
	private String filterBy = "";
	
	public void setFilterName(String name){
		filterName = name;
	}
	
	public String getFilterName(){
		return filterName;
	}

	@Override
	public List<Expense> filter(List<Expense> expenses) {
		List<Expense> filtered = new ArrayList<Expense>();
		for(Expense e : expenses){
			if(e.getDescription().toLowerCase().contains(((String)filterBy).toLowerCase()))
				filtered.add(e);
		}
		return filtered;
	}

	@Override
	public String getFilterBy() {
		return filterBy;
	}

	@Override
	public void setFilterBy(String obj) {
		filterBy = obj;
		
	}

}
