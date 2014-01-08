package com.expenses.event;

public class UserUpdateEvent {

	private String payload;
	
	public void setPayload(String pl){
		payload = pl;
	}
	
	public String getPayload(){
		return payload;
	}
}
