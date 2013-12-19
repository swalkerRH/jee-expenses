package com.expenses.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.expenses.model.Expense;

@Stateless
public class ExpenseService {

	@Inject
	private EntityManager em;

	@Inject
	private Event<Expense> expenseEvtSrc;

	public void addExpense(Expense e) {
		em.persist(e);
		expenseEvtSrc.fire(e);
	}

	public void removeExpense(Expense expense) {
		String removeString = "delete from Expense where id=:ID";
		Query query = em.createQuery(removeString).setParameter("ID", expense.getId());
		query.executeUpdate();
		expenseEvtSrc.fire(expense);
	}
}
