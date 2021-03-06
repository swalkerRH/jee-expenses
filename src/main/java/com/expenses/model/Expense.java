package com.expenses.model;

// Generated Jan 11, 2014 4:35:16 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Expense generated by hbm2java
 */
@Entity
@Table(name = "expense", schema = "public")
public class Expense implements java.io.Serializable {

	private int id;
	private ExpenseUser expenseUser;
	private ExpenseCategory expenseCategory;
	private String description;
	private float cost;
	private Date entered;
	private ExpenseImage expenseImage;

	public Expense() {
	}

	public Expense(int id, ExpenseUser expenseUser, String description,
			float cost, Date entered) {
		this.id = id;
		this.expenseUser = expenseUser;
		this.description = description;
		this.cost = cost;
		this.entered = entered;
	}

	public Expense(int id, ExpenseUser expenseUser,
			ExpenseCategory expenseCategory, String description, float cost,
			Date entered, ExpenseImage expenseImage) {
		this.id = id;
		this.expenseUser = expenseUser;
		this.expenseCategory = expenseCategory;
		this.description = description;
		this.cost = cost;
		this.entered = entered;
		this.expenseImage = expenseImage;
	}

	@Id @GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public ExpenseUser getExpenseUser() {
		return this.expenseUser;
	}

	public void setExpenseUser(ExpenseUser expenseUser) {
		this.expenseUser = expenseUser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category")
	public ExpenseCategory getExpenseCategory() {
		return this.expenseCategory;
	}

	public void setExpenseCategory(ExpenseCategory expenseCategory) {
		this.expenseCategory = expenseCategory;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "cost", nullable = false, precision = 8, scale = 8)
	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "entered", nullable = false, length = 13)
	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "expense")
	public ExpenseImage getExpenseImage() {
		return this.expenseImage;
	}

	public void setExpenseImage(ExpenseImage expenseImage) {
		this.expenseImage = expenseImage;
	}

}
