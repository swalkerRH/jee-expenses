package com.expenses.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.expenses.model.Expense;
import com.expenses.model.ExpenseImage;

@Stateless
public class ExpenseService {
	
	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Expense> expenseEvtSrc;

	public void addExpense(Expense e) {
		em.persist(e);
		expenseEvtSrc.fire(e);
	}
	
	public void updateExpenseImage(Expense e, ExpenseImage i) {
		log.info("Trying to merge " + e + ": " + e.getId());
		e = em.merge(e);
		i.setExpense(e);
		em.persist(i);
	}

	public void updateExpenseImage(ExpenseImage i) {
		em.merge(i);
		log.info("Updated image");
	}
	public void removeExpense(Expense expense) {
		String removeExpenseString = "delete from Expense where id=:ID";
		String removeImageString = "delete from ExpenseImage where id=:ID";
		if (expense.getExpenseImage() != null){
			Query imageQuery = em.createQuery(removeImageString).setParameter("ID", expense.getExpenseImage().getId());
			imageQuery.executeUpdate();
		}
		Query expenseQuery = em.createQuery(removeExpenseString).setParameter("ID", expense.getId());
		expenseQuery.executeUpdate();
		expenseEvtSrc.fire(expense);
	}
}
