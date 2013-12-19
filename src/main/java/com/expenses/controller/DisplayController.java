package com.expenses.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named
public class DisplayController implements Serializable{
	
	private boolean viewAddCategory;
	private boolean viewAddExpense;
	private boolean viewFilter;
	
	@PostConstruct
	public void initViews(){
		viewAddExpense = true;
		viewAddCategory = false;
		viewFilter = false;
	}



	
	public void viewFilter(){
		viewFilter = true;
		viewAddCategory = false;
		viewAddExpense = false;
	}
	
	public void viewAddCategory(){
		viewFilter = false;
		viewAddCategory = true;
		viewAddExpense = false;
	}
	
	public void viewAddExpense(){
		viewFilter = false;
		viewAddCategory = false;
		viewAddExpense = true;
	}

	public boolean isViewAddCategory() {
		return viewAddCategory;
	}

	public void setViewAddCategory(boolean viewAddCategory) {
		this.viewAddCategory = viewAddCategory;
	}

	public boolean isViewAddExpense() {
		return viewAddExpense;
	}

	public void setViewAddExpense(boolean viewAddExpense) {
		this.viewAddExpense = viewAddExpense;
	}

	public boolean isViewFilter() {
		return viewFilter;
	}

	public void setViewFilter(boolean viewFilter) {
		this.viewFilter = viewFilter;
	}
	
	
	
}
