package com.expenses.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.expenses.data.ExpenseDB;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseUser;
import com.expenses.service.ExpenseService;

@Path("/expense")
@RequestScoped
public class ExpenseRESTService {

	@Inject
	private ExpenseDB db;

	@Inject
	private Logger log;
	
	@Inject
	private ExpenseService expenseService;

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getExpenses( //List<Expense>
			ExpenseUser inUser) {
		ExpenseUser expUser = db.authenticate(inUser.getUsername(), inUser.getPassword());
		if(expUser == null){
			return Response.serverError().entity("{\"message\":\"Authentication Error\"}").status(401).build();
		}
		List<Expense> expenseList = db.getExpensesById(expUser);
		log.info("Rest service got " + expenseList.size() + " results");
		return Response.ok().entity(expenseList).build();
	}

	@POST
	@Path("/{category:.*}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response expensesByCategory(
			ExpenseUser inUser,
			@PathParam("category") String categoryName
			) {
		ExpenseUser expUser = db.authenticate(inUser.getUsername(), inUser.getPassword());
		if(expUser == null){
			return Response.serverError().entity("{\"message\":\"Authentication Error\"}").status(401).build();
		}
		try{
			ExpenseCategory category = db.getExpenseCategoryByName(expUser, categoryName);
			List<Expense> expenses = db.getExpensesInCategory(expUser, category);
			return Response.ok().entity(expenses).build();
		} catch(NoResultException nre){
			return Response.ok().entity(new ArrayList<Expense>()).build();
		}
	}

	/**
	 * @param expenseMap
	 * 		needs to be in the form of
	 * 				user : { username: name, password: password},
	 * 				expense: {description: desc, cost: cost, category: category}
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create/")
	public Response createExpense(
			Map<String, Map<String, Object>> expenseMap
			){
		String username = null, password = null, description = null, category = null;
		Float cost = (float) 0.0;
		try{
			username = (String) expenseMap.get("user").get("username");
			password = (String) expenseMap.get("user").get("password");
			description = (String) expenseMap.get("expense").get("description");
			category = (String) expenseMap.get("expense").get("category");
			cost = Float.valueOf((String) expenseMap.get("expense").get("cost"));
		} catch(Exception exp){
			return Response.serverError().entity("{\"message\":\"Could not translate your request, format error\"}").build();
		}
		ExpenseUser expUser = db.authenticate(username, password);
		ExpenseCategory cat = null;
		if(expUser == null){
			return Response.serverError().entity("{\"message\":\"Authentication Error\"}").status(401).build();
		}
		try {
			cat = db.getExpenseCategoryByName(expUser, category);
		} catch(Exception e){
			return Response.serverError().entity("{\"message\":\"Problem with category, does it exist?\"}").build();
		}
		Expense exp = new Expense();
		exp.setCost(cost);
		exp.setDescription(description);
		exp.setExpenseCategory(cat);
		exp.setEntered(new Date());
		exp.setExpenseUser(expUser);
		try {
			expenseService.addRESTExpense(exp);
		} catch (Exception e){
			return Response.serverError().entity("{\"message\":\"There was a problem with adding the expense:" +e.getMessage() + "\"}").build();
		}
		return Response.ok().entity("{\"message\":\"Successfully Added User\"}").build();	
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/remove/{id:[0-9][0-9]*}")
	public Response removeExpense(
			ExpenseUser inUser,
			@PathParam("id") int id){
		log.info("trying to delete");
		try{
		ExpenseUser expUser = db.authenticate(inUser.getUsername(), inUser.getPassword());
		if(expUser == null){
			log.info("Authentication failure for " + inUser.getUsername());
			return Response.serverError().entity("{\"message\":\"Authentication Error\"}").status(401).build();
		}
		Expense expToRemove = db.getExpenseById(id);
		if(!expToRemove.getExpenseUser().getUsername().equals(inUser.getUsername())){
			log.info(inUser.getUsername() + " tried to remove unowned expense");
			return Response.serverError().entity("{\"message\":\"Tried to Delete Non-owned Expense\"}").build();
		}
		expenseService.removeExpense(expToRemove, false);
		return Response.ok().entity("{\"message\":\"Successfully removed expense\"}").build();
		} catch (Exception e){
			return Response.serverError().entity("{\"message\":\"Unknown Error\"}").build();
		}
	}
}
