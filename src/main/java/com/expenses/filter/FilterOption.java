package com.expenses.filter;

import java.io.Serializable;
import java.util.List;

import com.expenses.model.Expense;

public abstract class FilterOption<T> implements Serializable{
	
	public abstract List<Expense> filter(List<Expense> expenses);
	public abstract void setFilterName(String name);
	public abstract String getFilterName(); 
	public abstract T getFilterBy();
	public abstract void setFilterBy(T obj);
}
