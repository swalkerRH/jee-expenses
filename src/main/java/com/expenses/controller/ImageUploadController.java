package com.expenses.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;
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
import com.expenses.service.FileListService;

@SessionScoped
@Named
public class ImageUploadController implements Serializable {
	
	@Inject
	private Logger log;
	
	@Inject
	private FileListService fileListService;
	
	@Inject
	private ExpenseService expenseService;
	
	@Inject
	private ExpenseDB db;
	
	
	@Inject
	private List<ExpenseImage> fileList;
	
	private Expense currentExpense;
	
	public void storeImage(FileUploadEvent fileEvent) throws Exception{
		UploadedFile file = fileEvent.getUploadedFile();
		ExpenseImage image = new ExpenseImage();
		image.setImageData(file.getData());
		fileListService.addFile(image);
		log.info("storeImage current expense is: " + currentExpense);
		assignImage(currentExpense);
	}
	
	public void renderImage(OutputStream out, Object obj) throws IOException{
		Integer imageId = (Integer) obj;
		ExpenseImage image = db.getExpenseImageById(imageId);
		BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(
				image.getImageData()));
		ImageIO.write(buffImage, "jpeg", out);
	}
	
	public void assignImage(Expense e){
		ExpenseImage image = fileList.get(0);
		fileList.remove(0);
		expenseService.updateExpenseImage(e, image);
	}

	public Expense getCurrentExpense() {
		return currentExpense;
	}

	public void setCurrentExpense(Expense currentExpense) {
		log.info("Set current Expense in image upload " + currentExpense);
		this.currentExpense = currentExpense;
	}
	
}
