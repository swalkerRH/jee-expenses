package com.expenses.controller;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named
public class LogoutController {

	public String logout(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().invalidate();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You've been logged out"));
		return "login";
	}
}
