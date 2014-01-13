package com.expenses.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.annotation.CurrentUser;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseImage;
import com.expenses.model.ExpenseUser;
import com.expenses.service.ExpenseService;

@RequestScoped
@Named
public class ExpenseController {
	
	@Inject
	private Logger log;
	
	@Inject
	private ExpenseService expenseService;
	
	@Inject @CurrentUser
	private ExpenseUser expenseUser;
	
	private Expense expense;
	
	@PostConstruct
	private void initExpense(){
		expense = new Expense();
		expense.setCost(new Float(0.00));
		expense.setExpenseUser(expenseUser);
	}
	
	public void addExpense(){
		expense.setEntered(new Date());
		expenseService.addExpense(expense);
	}
	
	public void remove(Expense e){
		expenseService.removeExpense(e);
	}
	
	public Expense getExpense() {
		return expense;
	}
	public void setExpense(Expense expense) {
		log.info("set Expense");
		this.expense = expense;
	}
	public ExpenseCategory getExpenseCategory() {
		return expense.getExpenseCategory();
	}
	public void setExpenseCategory(ExpenseCategory expenseCategory) {
		expense.setExpenseCategory(expenseCategory);
	}
	
	public String getDescription() {
		return expense.getDescription();
	}
	public void setDescription(String description) {
		expense.setDescription(description);
	}
	public float getCost() {
		return expense.getCost();
	}
	public void setCost(float cost) {
		expense.setCost(cost);
	}
	
	
}
