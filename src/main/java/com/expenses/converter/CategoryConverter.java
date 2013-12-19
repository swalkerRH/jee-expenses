package com.expenses.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.model.ExpenseCategory;

@Named
@FacesConverter(value="categoryConverter")
public class CategoryConverter implements Converter{

	@Inject
	private List<ExpenseCategory> categories;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		for(ExpenseCategory cat: categories){
			if(cat.getName().equals(arg2)){
				return cat;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		return categories.get(categories.indexOf(arg2)).getName();
	}

}
