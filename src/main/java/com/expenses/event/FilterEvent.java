package com.expenses.event;

import com.expenses.filter.FilterOption;

public class FilterEvent {

	private FilterOption<? extends Object> payload;

	public FilterOption<? extends Object> getPayload() {
		return payload;
	}

	public void setPayload(FilterOption<? extends Object> payload) {
		this.payload = payload;
	}
	
	
}
