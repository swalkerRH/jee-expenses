package com.expenses.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;

@ApplicationScoped
public class ExpenseDB {

	@Inject
	private EntityManager em;

	public List<Expense> getExpensesById() {
		return getEntityById(Expense.class);
	}

	public List<ExpenseCategory> getExpenseCategoriesById() {
		return getEntityById(ExpenseCategory.class);
	}

	private <T> List<T> getEntityById(Class<T> clazz) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.select(root).orderBy(builder.asc(root.get("id")));
		return em.createQuery(criteria).getResultList();
	}

	public List<Expense> getExpensesInCategory(ExpenseCategory category) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Expense> criteria = builder.createQuery(Expense.class);
		Root<Expense> root = criteria.from(Expense.class);
		criteria.select(root)
				.where(builder.equal(root.get("category"), category.getId()))
				.orderBy(builder.asc(root.get("id")));
		return em.createQuery(criteria).getResultList();
	}
}
