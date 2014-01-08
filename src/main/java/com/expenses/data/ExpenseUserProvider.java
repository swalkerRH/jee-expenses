package com.expenses.data;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.annotation.CurrentUser;
import com.expenses.event.UserUpdateEvent;
import com.expenses.model.ExpenseUser;

@SessionScoped
public class ExpenseUserProvider implements Serializable {
	
	@Inject
	private Logger log;
	
	@Inject @CurrentUser
	private String username;
	
	private ExpenseUser user;
	
	@Inject
	private ExpenseDB db;
	
	@PostConstruct
	private void updateUser(){
		user = db.getExpenseUser(username);
		log.info("User Updated to: " + username );
	}
	
	@Produces @CurrentUser
	@Named(value="expenseUser")
	public ExpenseUser getExpenseUser(){
		return user;
	}
	
	public void updateNeeded(@Observes UserUpdateEvent event){
		updateUser();
	}

}
