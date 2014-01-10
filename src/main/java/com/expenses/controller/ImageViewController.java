package com.expenses.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.model.ExpenseImage;

@RequestScoped
@Named
public class ImageViewController {

	@Inject
	private Logger log;

	private boolean assignView = false;
	private boolean uploadView = true;

	@Inject
	private List<ExpenseImage> filelist;

	public void createPreview(OutputStream out, Object obj)
			throws IOException {
		Integer id = (Integer) obj;
		for(ExpenseImage image: filelist){
			if(image.getId() == id.intValue()){
				BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(
						image.getImageData()));
				ImageIO.write(buffImage, "jpeg", out);
			}
		}
		log.info("created image preview of " + obj.toString());
	}

	public void gotoAssignView() {
		uploadView = false;
		assignView = true;
	}

	public void gotoUploadView() {
		assignView = false;
		uploadView = true;
	}

	public boolean isAssignView() {
		return assignView;
	}

	public void setAssignView(boolean assignView) {
		this.assignView = assignView;
	}

	public boolean isUploadView() {
		return uploadView;
	}

	public void setUploadView(boolean uploadView) {
		this.uploadView = uploadView;
	}

	public List<ExpenseImage> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<ExpenseImage> filelist) {
		this.filelist = filelist;
	}

}
