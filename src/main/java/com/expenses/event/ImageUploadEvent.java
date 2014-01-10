package com.expenses.event;

import com.expenses.model.ExpenseImage;

public class ImageUploadEvent {
	
	private ExpenseImage image;
	
	public ExpenseImage getExpenseImage(){
		return image;
	}
	
	public void setExpenseImage(ExpenseImage i){
		image = i;
	}
}
