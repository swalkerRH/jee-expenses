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

import com.expenses.data.ExpenseDB;
import com.expenses.model.ExpenseImage;

@SessionScoped
@Named
public class ImageViewController implements Serializable{
	
	@Inject
	private Logger log;
	
	@Inject
	private ExpenseDB db;
	
	private Integer currentImageId;

	public void renderCurrentImage(OutputStream out, Object obj) throws IOException{
		log.info("Rendering current image with id: " + currentImageId);
		if(currentImageId == null){
			log.warning("image ID was null, skipping rendering");
			return;
		}
		ExpenseImage image = db.getExpenseImageById(currentImageId);
		BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(
				image.getImageData()));
		String mimeType = image.getMimeType();
		ImageIO.write(buffImage, mimeType.substring(mimeType.indexOf("/")+1), out);
	}
	
	public Integer getCurrentImageId() {
		return currentImageId;
	}

	public void setCurrentImageId(Integer currentImageId) {
		log.info("Set current image id in image upload " + currentImageId);
		this.currentImageId = currentImageId;
	}

}
