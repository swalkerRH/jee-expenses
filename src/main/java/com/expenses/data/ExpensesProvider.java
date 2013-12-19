package com.expenses.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.annotation.CurrentFilter;
import com.expenses.filter.FilterOption;
import com.expenses.model.Expense;

@RequestScoped
public class ExpensesProvider {
	@Inject
	private ExpenseDB db;
	
	//@Inject @CurrentFilter
	//private FilterOption<? extends Object> filter;
	
	private List<Expense> expenses;
	
	private float total;
	
	@PostConstruct
	public void initExpenses(){
		expenses = db.getExpensesById();
		calculateTotal();
	}
	
	private void calculateTotal(){
		total = 0;
		if(expenses.isEmpty())
			return;
		for(Expense e :expenses){
			total += e.getCost();
		}
	}
	
	@Produces
	@Named
	public List<Expense> getExpenses(){
		return expenses;
	}
	
	@Produces
	@Named
	public float getTotal(){
		return total;
	}
	
	public void onExpenseAdd(@Observes Expense exp){
		initExpenses();
	}
	
	public void onFilterChange(@Observes @CurrentFilter FilterOption filt){
		if(filt.getFilterName().equals("No Filter"))
		{
			initExpenses();
		} else {
			expenses = filt.filter(expenses);
			calculateTotal();
		}
	}
}
