package com.expenses.data;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.expenses.event.UserUpdateEvent;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseUser;

@ApplicationScoped
public class ExpenseDB {
	
	@Inject
	private Logger log;
	
	@Inject
	private Event<UserUpdateEvent> uuEventSrc;
	
	private ExpenseUser expenseUser;

	@Inject
	private EntityManager em;
	

	public List<Expense> getExpensesById() {
		if(expenseUser== null){
			uuEventSrc.fire(new UserUpdateEvent());
		}
		Query query = em.createQuery("select e from Expense e where e.expenseUser= :user_id ");
		query.setParameter("user_id", expenseUser);
		log.info("Got " + expenseUser.getUsername() + "'s expenses");
		return query.getResultList();
	}

	public List<ExpenseCategory> getExpenseCategoriesById() {
		if(expenseUser== null){
			uuEventSrc.fire(new UserUpdateEvent());
		}
		Query query = em.createQuery("select c from ExpenseCategory c where c.expenseUser= :user_id ");
		log.info("made query");
		query.setParameter("user_id", expenseUser);
		log.info("Got " + expenseUser.getUsername() + "'s expense categories");
		return query.getResultList();
	}

	private <T> List<T> getEntityById(Class<T> clazz) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(clazz);
		Root<T> root = criteria.from(clazz);
		criteria.select(root).where(builder.equal(root.get("userId"), expenseUser.getId()))
			.orderBy(builder.asc(root.get("id")));
		log.info(criteria.toString());
		return em.createQuery(criteria).getResultList();
	}
	
	public ExpenseUser getExpenseUser(String username){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ExpenseUser> criteria = builder.createQuery(ExpenseUser.class);
		Root<ExpenseUser> root = criteria.from(ExpenseUser.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("username"), username));
		expenseUser = em.createQuery(criteria).getSingleResult();
		System.out.println("found user");
		return expenseUser;
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
