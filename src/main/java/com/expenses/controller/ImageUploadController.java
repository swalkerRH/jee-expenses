package com.expenses.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import com.expenses.model.ExpenseImage;
import com.expenses.service.FileListService;

@RequestScoped
@Named
public class ImageUploadController {
	
	@Inject
	private FileListService fileListService;
	
	public void storeImage(FileUploadEvent fileEvent) throws Exception{
		UploadedFile file = fileEvent.getUploadedFile();
		ExpenseImage image = new ExpenseImage();
		image.setImageData(file.getData());
		fileListService.addFile(image);
	}
}
