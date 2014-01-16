package com.expenses.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.filter.FilterOption;
import com.expenses.service.FilterService;

@SessionScoped
@Named
public class FilterController implements Serializable{
	
	private FilterOption<? extends Object> selected;
	
	@Inject
	private FilterService filterService;
	
	@Inject
	private List<FilterOption<? extends Object>> filters;
	
	@Inject
	private Map<Integer, Boolean> selectedExpenses;
	
	private List<Boolean> renderedViews;
	
	@PostConstruct
	public void initSelected(){
		selected = filters.get(0);
		renderedViews = new ArrayList<Boolean>(filters.size());
		for(FilterOption<? extends Object> f: filters){
			renderedViews.add(false);
		}
		updateViews();
	}
	
	public void updateViews(){
		int index = filters.indexOf(selected);
		for(int i = 0; i < filters.size(); i++){
			renderedViews.set(i, i == index);
		}
	}
	
	public void applyFilter(){
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage("Warning: expense report generation from the filter view is not supported yet."));
		filterService.setFilterOption(selected);
	}
	
	public FilterOption<? extends Object> getSelected(){
		return selected;
	}
	
	public void setSelected(FilterOption<? extends Object> sel){
		selected = sel;
	}

	public List<Boolean> getRenderedViews() {
		return renderedViews;
	}

	public void setRenderedViews(List<Boolean> renderedViews) {
		this.renderedViews = renderedViews;
	}
}
