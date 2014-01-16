package com.expenses.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.expenses.data.ExpenseDB;
import com.expenses.model.Expense;
import com.expenses.model.ExpenseImage;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@ConversationScoped
@Named
public class SelectionController implements Serializable {
	
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private Logger log;
	
	@Inject
	private Map<Integer, Boolean> selected;
	
	@Inject
	private ExpenseDB db;
	
	
	@PostConstruct
	public void initSelected(){
		conversation.begin();
	}
	
	public void getReport() throws DocumentException, MalformedURLException, IOException{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		String base = request.getContextPath();
		String filename = "expReport" + new Date().toString() + ".pdf";
		
		//retrieve the images
		List<ExpenseImage> images = new ArrayList<ExpenseImage>();
		for(Integer id: selected.keySet()){
			if(selected.get(id)){
				Expense selExpense = db.getExpenseById(id);
				ExpenseImage current = selExpense.getExpenseImage();
				if(current != null)
					images.add(current);
			}
		}
		
		//create the pdf
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(filename));
		doc.open();
		
		doc.left(0);
		doc.right(0);
		
		//Add the images
		for(ExpenseImage i: images){
			Image im = Image.getInstance(i.getImageData());
			float scaler = ((doc.getPageSize().getWidth() - doc.leftMargin()
		               - doc.rightMargin()) / im.getWidth()) * 100;
			im.scalePercent(scaler);
			doc.add(im);
		}
		doc.close();
		File file = new File(filename);
		//prepare the response header
		response.setHeader("Content-Type", "application/pdf");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
		InputStream input = new BufferedInputStream(new FileInputStream(file), 10240);
		OutputStream output = new BufferedOutputStream(response.getOutputStream(), 10240);
		
		 // Write file contents to response.
        byte[] buffer = new byte[10240];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        // Finalize task.
        output.flush();
		
        output.close();
        input.close();
        FacesContext.getCurrentInstance().responseComplete();
        
		conversation.end();
	}
	
	
}
