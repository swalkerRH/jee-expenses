package com.expenses.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.annotation.CurrentUser;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseUser;
import com.expenses.service.CategoryService;

@RequestScoped
@Named
public class CategoryController {
	
	@Inject @CurrentUser
	private ExpenseUser expenseUser;
	
	@Inject
	private CategoryService categoryService;
	
	private ExpenseCategory expenseCategory;
	
	@PostConstruct
	public void initExpenseCategory(){
		expenseCategory = new ExpenseCategory();
		expenseCategory.setExpenseUser(expenseUser);
	}
	
	public void addCategory(){
		categoryService.addCategory(expenseCategory);
	}
	
	public ExpenseCategory getExpenseCategory() {
		return expenseCategory;
	}
	public void setExpenseCategory(ExpenseCategory expenseCategory) {
		this.expenseCategory = expenseCategory;
	}
	public String getName() {
		return expenseCategory.getName();
	}
	public void setName(String name) {
		expenseCategory.setName(name);
	}
	public String getDescription() {
		return expenseCategory.getDescription();
	}
	public void setDescription(String description) {
		expenseCategory.setDescription(description);
	}
	
	
}
