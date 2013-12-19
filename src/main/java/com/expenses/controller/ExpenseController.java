package com.expenses.controller;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;
import com.expenses.service.ExpenseService;

@RequestScoped
@Named
public class ExpenseController {
	
	@Inject
	private ExpenseService expenseService;
	
	private Expense expense;
	
	@PostConstruct
	private void initExpense(){
		expense = new Expense();
		expense.setCost(new Float(0.00));
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
