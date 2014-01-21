package com.expenses.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.expenses.data.ExpenseDB;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseImage;
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
	 * @param expenseId
	 * @param mimeType
	 * @return
	 * @throws OverwriteImageException
	 */
	@POST
	@Produces("application/json")
	public Map<String, String> requestImageSpace(
			@FormParam("expense_id") Integer expenseId,
			@FormParam("mime_type") String mimeType
			) throws OverwriteImageException, UnsupportedMimeTypeException{
		if(!mimeType.equals("image/jpeg") && 
				!mimeType.equals("image/png") &&
				!mimeType.equals("image/gif")){
			throw new UnsupportedMimeTypeException("Unsupported Mime Type:" + mimeType +", must be image/[jpeg|png|gif]");
		}
		
		Expense exp = db.getExpenseById(expenseId);
		if(exp.getExpenseImage() != null){
			throw new OverwriteImageException("Tryed to overwrite the image for expense " + expenseId);
		}
		ExpenseImage im = new ExpenseImage();
		im.setExpense(exp);
		im.setMimeType(mimeType);
		expenseService.updateExpenseImage(exp, im);
		Map<String, String> response = new HashMap<String, String>();
		response.put("image_id", String.valueOf(im.getId()));
		response.put("expense_id", String.valueOf(expenseId));
		response.put("mime_type", mimeType);
		return response;
	}
	
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public void putImage(
			@PathParam("id") Integer imageId,
			InputStream stream) throws IOException{
		ExpenseImage im = db.getExpenseImageById(imageId);
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream(10240);
		while(stream.read(buffer, 0, buffer.length) != -1){
			out.write(buffer);
		}
		log.info("recieving the last bytes");
		out.write(buffer);
		stream.close();
		out.close();
		
		im.setImageData(out.toByteArray());
		expenseService.updateExpenseImage(im);
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
