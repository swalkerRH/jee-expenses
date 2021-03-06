package com.expenses.model;

// Generated Jan 11, 2014 4:35:16 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ExpenseImage generated by hbm2java
 */
@Entity
@Table(name = "expense_image", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "expense_id"))
public class ExpenseImage implements java.io.Serializable {

	private int id;
	private Expense expense;
	private byte[] imageData;
	private String mimeType;

	public ExpenseImage() {
	}

	public ExpenseImage(int id) {
		this.id = id;
	}

	public ExpenseImage(int id, Expense expense, byte[] imageData) {
		this.id = id;
		this.expense = expense;
		this.imageData = imageData;
	}

	@Id @GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "expense_id", unique = true)
	public Expense getExpense() {
		return this.expense;
	}

	public void setExpense(Expense expense) {
		this.expense = expense;
	}

	@Column(name = "image_data")
	public byte[] getImageData() {
		return this.imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	@Column(name = "mime_type", nullable = false)
	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

}
