package com.expenses.data;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseImage;
import com.expenses.model.ExpenseUser;
@ApplicationScoped
public class ExpenseDB{
	
	@Inject
	private Logger log;

	@Inject
	private EntityManager em;
	

	public List<Expense> getExpensesById(ExpenseUser expenseUser) {
		Query query = em.createQuery("select e from Expense e where e.expenseUser= :user_id ");
		query.setParameter("user_id", expenseUser);
		log.info("Got " + expenseUser.getUsername() + "'s expenses");
		return query.getResultList();
	}

	public List<ExpenseCategory> getExpenseCategoriesById(ExpenseUser expenseUser) {
		Query query = em.createQuery("select c from ExpenseCategory c where c.expenseUser= :user_id ");
		log.info("made query");
		query.setParameter("user_id", expenseUser);
		log.info("Got " + expenseUser.getUsername() + "'s expense categories");
		return query.getResultList();
	}
	
	public ExpenseUser getExpenseUser(String username){
		log.info("getting " + username);
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<ExpenseUser> criteria = builder.createQuery(ExpenseUser.class);
		Root<ExpenseUser> root = criteria.from(ExpenseUser.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("username"), username));
		ExpenseUser expenseUser = em.createQuery(criteria).getSingleResult();
		return expenseUser;
	}

	public List<Expense> getExpensesInCategory(ExpenseUser expenseUser, ExpenseCategory category) {
		Query query = em.createQuery("select e from Expense e where e.expenseUser= :user_id and e.expenseCategory = :cat");
		query.setParameter("user_id", expenseUser);
		query.setParameter("cat", category);
		log.info("Got " + expenseUser.getUsername() + "'s expense categories");
		return query.getResultList();
	}
	
	public ExpenseCategory getExpenseCategoryByName(ExpenseUser expenseUser, String name){
		Query query = em.createQuery("select c from ExpenseCategory c where c.name = :name and c.expenseUser = :user_id");
		query.setParameter("name", name);
		query.setParameter("user_id", expenseUser);
		return (ExpenseCategory) query.getSingleResult();
	}
	
	public ExpenseUser authenticate(String username, String password){
		log.info("Authenticating "+ username);
		try{
			ExpenseUser dbUser = getExpenseUser(username);
			if(dbUser.getPassword().equals(password)){
				return dbUser;
			}
		} catch(Exception e){
			return null;
		}
		return null;
	}
	
	public ExpenseImage getExpenseImageById(Integer id){
		return em.find(ExpenseImage.class, id);
	}

	public Expense getExpenseById(Integer id) {
		return em.find(Expense.class, id );
	}

}
