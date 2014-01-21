package com.expenses.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.annotation.CurrentUser;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseUser;

@RequestScoped
public class CategoriesProvider {

	@Inject
	ExpenseDB db;
	
	@Inject @CurrentUser
	private ExpenseUser expenseUser;
	
	private List<ExpenseCategory> categories;
	
	@PostConstruct
	public void initCategories(){
		categories = db.getExpenseCategoriesById(expenseUser);
	}
	
	@Produces
	@Named
	public List<ExpenseCategory> getCategories(){
		return categories;
	}
	
	public void onCategoriesChange(@Observes ExpenseCategory cat){
		initCategories();
	}
}
