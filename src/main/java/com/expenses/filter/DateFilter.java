package com.expenses.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.expenses.model.Expense;
import com.expenses.util.DateRange;

public class DateFilter extends FilterOption<DateRange> {
	
	private String filterName = "Date";
	private DateRange filterBy = new DateRange();
	
	@Override
	public List<Expense> filter(List<Expense> expenses) {
		List<Expense> ret = new ArrayList<Expense>();
		for(Expense e: expenses) {
			Date entered = e.getEntered();
			if(filterBy.contains(entered)){
				ret.add(e);
			}
		}
		return ret;
	}

	@Override
	public void setFilterName(String name) {
		filterName = name;

	}

	@Override
	public String getFilterName() {
		return filterName;
	}

	@Override
	public DateRange getFilterBy() {
		return filterBy;
	}

	@Override
	public void setFilterBy(DateRange obj) {
		filterBy = obj;
	}

}
