package com.expenses.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.expenses.data.ExpenseDB;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseImage;
import com.expenses.service.ExpenseService;

@SessionScoped
@Named
public class ImageUploadController implements Serializable {
	
	@Inject
	private Logger log;
	
	@Inject
	private ExpenseService expenseService;
	
	@Inject
	private ExpenseDB db;
	
	private Expense currentExpense;
	

	
	public void storeImage(FileUploadEvent fileEvent) throws Exception{
		UploadedFile file = fileEvent.getUploadedFile();
		ExpenseImage image = new ExpenseImage();
		image.setImageData(file.getData());
		image.setMimeType(file.getContentType());
		log.info("Storing image with mime type: " + file.getContentType());
		expenseService.updateExpenseImage(currentExpense, image);
	}
	
	public void renderImage(OutputStream out, Object obj) throws IOException{
		log.info("Rendering image with id: " + obj);
		if(obj == null){
			log.warning("image ID was null, skipping rendering");
			return;
		}
		Integer imageId = (Integer) obj;
		ExpenseImage image = db.getExpenseImageById(imageId);
		BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(
				image.getImageData()));
		String mimeType = image.getMimeType();
		ImageIO.write(buffImage, mimeType.substring(mimeType.indexOf("/")+1), out);
	}

	public Expense getCurrentExpense() {
		return currentExpense;
	}

	public void setCurrentExpense(Expense currentExpense) {
		log.info("Set current Expense in image upload " + currentExpense);
		this.currentExpense = currentExpense;
	}
	
}
