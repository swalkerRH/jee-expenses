package com.expenses.filter;

import java.util.ArrayList;
import java.util.List;

import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;

public class CategoryFilter extends FilterOption<ExpenseCategory> {

	private String filterName ="Category";
	private ExpenseCategory filterBy;
	
	public void setFilterName(String name){
		filterName = name;
	}
	
	public String getFilterName(){
		return filterName;
	}
	
	@Override
	public List<Expense> filter( List<Expense> expenses) {
		List<Expense> filtered = new ArrayList<Expense>();
		for(Expense e : expenses){
			ExpenseCategory expcat = e.getExpenseCategory();
			ExpenseCategory filtcat = (ExpenseCategory) filterBy;
			if(filtcat.getName().equals(expcat.getName()))
				filtered.add(e);
		}
		return filtered;
	}
	
	public ExpenseCategory getFilterBy(){
		return filterBy;
	}
	
	public void setFilterBy(ExpenseCategory cat){
		filterBy = cat;
	}
	

}
