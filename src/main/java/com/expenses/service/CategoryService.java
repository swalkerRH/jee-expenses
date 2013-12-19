package com.expenses.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;

@Stateless
public class CategoryService {

	@Inject
	private EntityManager em;
	
	@Inject
	private Event<ExpenseCategory> categoryEvtSrc;

	public void addCategory(ExpenseCategory c){
		em.persist(c);
		categoryEvtSrc.fire(c);
	}
}
