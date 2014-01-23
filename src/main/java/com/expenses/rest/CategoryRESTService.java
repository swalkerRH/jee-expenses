package com.expenses.rest;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.expenses.data.ExpenseDB;
import com.expenses.model.ExpenseCategory;
import com.expenses.model.ExpenseUser;
import com.expenses.service.CategoryService;

@Path("/category")
public class CategoryRESTService {

	@Inject
	private ExpenseDB db;
	
	@Inject
	private CategoryService categoryService;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories(
			ExpenseUser inUser
			){
		ExpenseUser expUser = db.authenticate(inUser.getUsername(), inUser.getPassword());
		if(expUser == null){
			return Response.serverError().entity("{\"message\":\"Authentication Error\"}").status(401).build();
		}
		List<ExpenseCategory> categories = db.getExpenseCategoriesById(expUser);
		return Response.ok().entity(categories).build();
	}
	
	/**
	 * 
	 * @param catMap
	 * 		needs to be in the form of:
	 * 			user:{username:username, password:password},
	 * 			category:{name:name, description:desc}
	 * @return
	 */
	@POST
	@Path("/create/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCategory(
			Map<String, Map<String, String>> catMap
			){
		String username = null, password = null;
		String catName = null, catDesc = null;
		try{
			username = catMap.get("user").get("username");
			password = catMap.get("user").get("password");
			catName = catMap.get("category").get("name");
			catDesc = catMap.get("category").get("description");
		} catch (Exception e) {
			return Response.serverError().entity("{\"message\":\"Format Error\"}").build();
		}
		ExpenseUser expUser = db.authenticate(username, password);
		if(expUser == null){
			return Response.serverError().entity("{\"message\":\"Authentication Error\"}").status(401).build();
		}
		ExpenseCategory cat = new ExpenseCategory();
		cat.setDescription(catDesc);
		cat.setName(catName);
		cat.setExpenseUser(expUser);
		categoryService.addCategory(cat, false);
		return Response.ok().entity("{\"message\":\"Succesfully Added Category\"}").build();
	}
}
