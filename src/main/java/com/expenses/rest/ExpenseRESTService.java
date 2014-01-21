package com.expenses.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
	private Validator validator;
	
	@Inject
	private ExpenseService expenseService;

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Expense> getExpenses(
			@FormParam("username") String username,
			@FormParam("password") String password) {
		ExpenseUser expUser = db.authenticate(username, password);
		if(expUser == null){
			return null;
		}
		List<Expense> expenseList = db.getExpensesById(expUser);
		log.info("Rest service got " + expenseList.size() + " results");
		return expenseList;
	}

	@POST
	@Path("/{category:.*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Expense> expensesByCategory(
			@PathParam("category") String categoryName) {
		ExpenseCategory category = db.getExpenseCategoryByName(new ExpenseUser(), categoryName);
		return db.getExpensesInCategory(new ExpenseUser(), category);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create/")
	public Response createExpense(Expense expense){
		Response.ResponseBuilder builder = null;
		
		try{
			validate(expense);
			expenseService.addExpense(expense);
			builder = Response.ok();
			
		} catch (ConstraintViolationException e){
			builder = createViolationResponse(e.getConstraintViolations());
		}
		
		return builder.build();
		
	}

	private <T> void validate(T entity) throws ConstraintViolationException{
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<T>> violations = validator.validate(entity);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }
}
