package com.expenses.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.expenses.event.ImageUploadEvent;
import com.expenses.model.ExpenseImage;

@Stateless
public class FileListService {
	
	@Inject
	Event<ImageUploadEvent> imageUploadSrc;
	
	public void addFile(ExpenseImage image){
		ImageUploadEvent iuEvent = new ImageUploadEvent();
		iuEvent.setExpenseImage(image);
		imageUploadSrc.fire(iuEvent);
	}
}
