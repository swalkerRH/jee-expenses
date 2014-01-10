package com.expenses.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.event.ImageUploadEvent;
import com.expenses.model.ExpenseImage;

@SessionScoped
public class FileListProvider implements Serializable{
	
	@Inject
	private Logger log;
	
	private List<ExpenseImage> fileList;
	
	@PostConstruct
	public void initFileList(){
		fileList = new ArrayList<ExpenseImage>();
	}
	
	@Produces
	@Named
	public List<ExpenseImage> getFileList(){
		return fileList;
	}
	
	public void onFileEvent(@Observes ImageUploadEvent iuEvent){
		fileList.add(iuEvent.getExpenseImage());
		log.info("added a file to the filelist " + fileList.size());
	}
}
