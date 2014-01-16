package com.expenses.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.expenses.model.Expense;

@SessionScoped
public class SelectedProvider implements Serializable {
	
	@Inject
	private Logger log;

	private Map<Integer, Boolean> selectedExpenses;
	
	@Inject
	private List<Expense> expenses;
	
	@PostConstruct
	private void init(){
		log.info("creating selectedProvider");
		selectedExpenses = new WrappedMap<Integer, Boolean>();
		for(Expense e: expenses){
			selectedExpenses.put(e.getId(), false);
		}
	}
	
	@Produces
	@Named
	public Map<Integer, Boolean> getSelectedExpenses(){
		return selectedExpenses;
	}
	
	
	public void onExpenseChange(@Observes Expense exp){
		for(Expense e: expenses){
			if(!selectedExpenses.containsKey(e.getId())){
				selectedExpenses.put(e.getId(), false);
			}
		}
	}
	
	class WrappedMap<K, V> extends HashMap<K, V> {

		private static final long serialVersionUID = 1L;

		public V put(K key, V value){
			V ret = super.put(key,value );
			log.info("put: " + key + ":" + value );
			return ret;
			
		}
	}
}
