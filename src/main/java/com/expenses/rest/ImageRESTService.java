package com.expenses.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bouncycastle.util.encoders.Base64;

import com.expenses.data.ExpenseDB;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseImage;
import com.expenses.model.ExpenseUser;
import com.expenses.service.ExpenseService;
import com.expenses.util.OverwriteImageException;
import com.expenses.util.UnsupportedMimeTypeException;

@Path("/image")
public class ImageRESTService {
	
	@Inject
	private Logger log;
	
	@Inject
	private ExpenseDB db;
	
	@Inject
	private ExpenseService expenseService;

	/**
	 * Takes an expenseId and mime_type and allocates the image id
	 * returns a map with the image id added to the request
	 * TODO Change to multipart
	 * @param inMap
	 * 			needs to be in the format:
	 * 					user: {username:username, password:password},
	 * 					meta_data: {mime_type:mimetype, expense_id:expense_id}
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response requestImageSpace(
			Map<String,Map<String, Object>> inMap
			){
		String username = null, password = null;
		String mimeType = null;
		Integer expenseId;
		try{
			username = (String) inMap.get("user").get("username");
			password = (String) inMap.get("user").get("password");
			mimeType = (String) inMap.get("meta_data").get("mime_type");
			expenseId = (Integer) inMap.get("meta_data").get("expense_id");
		} catch(Exception e){
			return Response.serverError().entity("Wrong request format").build();
		}
		
		if(db.authenticate(username, password) == null)
			return Response.serverError().entity("Authentication error").build();
		
		
		if(!mimeType.equals("image/jpeg") && 
				!mimeType.equals("image/png") &&
				!mimeType.equals("image/gif")){
			return Response.serverError().entity("bad mime type").build();
		}
		
		Expense exp = db.getExpenseById(expenseId);
		if(exp.getExpenseImage() != null){
			return Response.serverError().entity("Trying to overwrite image").build();
		}
		ExpenseImage im = new ExpenseImage();
		im.setExpense(exp);
		im.setMimeType(mimeType);
		expenseService.updateExpenseImage(exp, im);
		Map<String, String> response = new HashMap<String, String>();
		response.put("image_id", String.valueOf(im.getId()));
		response.put("expense_id", String.valueOf(expenseId));
		response.put("mime_type", mimeType);
		return Response.ok().entity(response).build();
	}
	
	/**
	 * 
	 * @param imageId
	 * takes the format of
	 * 		user: { username:username, password:password},
	 * 		image: base64_image_string
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putImage(
			@PathParam("id") Integer imageId,
			Map<String, Object> imageMap) throws IOException{
		ExpenseImage im = db.getExpenseImageById(imageId);
		String username = null, password=null, image_str=null;
		ExpenseUser exp = db.authenticate(username, password);
		if(exp == null)
			return Response.serverError().entity("Authentication Error").build();
		
		try{
			username = ((Map<String,String>) imageMap.get("user")).get("username");
			password = ((Map<String,String>) imageMap.get("user")).get("password");
			image_str = (String) imageMap.get("image");
		} catch(Exception e){
			return Response.serverError().entity("Wrong argument format").build();
		}
		byte[] buffer = Base64.decode(image_str);
		im.setImageData(buffer);
		expenseService.updateExpenseImage(im);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"image/jpeg", "image/png", "image/gif"})
	public byte[] getImage(
			@PathParam("id") Integer imageId){
		ExpenseImage im = db.getExpenseImageById(imageId);
		return im.getImageData();
	}
	
}
