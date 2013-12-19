package com.expenses.util;

import java.util.Date;

public class DateRange {
	private Date begin;
	private Date end;
	
	public DateRange(){
		begin = new Date();
		end = new Date();
	}
	
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public boolean contains(Date d){
		return (d.after(begin) && d.before(end)) || d.equals(begin) || d.equals(end);
	}
	
}
