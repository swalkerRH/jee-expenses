package com.expenses.data;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.expenses.annotation.CurrentUser;

@Stateless
@Named
public class UsernameProvider{

	@Resource
	private SessionContext context;
	
	@Produces @CurrentUser
	@Named("username")
	public String username(){
		return context.getCallerPrincipal().getName();
	}
}
