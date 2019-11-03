package org.magazin.shopping;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.magazin.cooking.Ingredient;
import org.magazin.cooking.Recipe;

@Stateful
public class BasketManagerBean implements BasketManager {
	private Logger LOGGER = LogManager.getLogger(this.getClass().getSimpleName());
	
	@PersistenceContext(unitName = "DBLink")
	private EntityManager em;
	HashMap<String, Integer> contents; 
	Set<Ingredient> ingredientList;  
	
	public BasketManagerBean(){
		initialize();
		
    }

	public void initialize(){
		
		contents = new HashMap<String, Integer>();
	}

	
	public void addItemList(Set<Ingredient> ingredientList) {
		
		for (Ingredient ingredient : ingredientList) {
			contents.put(ingredient.getName(), contents.getOrDefault(ingredient.getName(), 0) + 1);
        }
    }

	public void addItem(String ingredientName) {
		
		contents.put(ingredientName, contents.getOrDefault(ingredientName, 0) + 1);
        
    }
	
	public void removeItem(String ingredientName){
		if (contents.get(ingredientName) == 1) {
			contents.remove(ingredientName);
		}
		else {
			contents.put(ingredientName, contents.get(ingredientName) - 1);
		}
	}
	
	public void clearContents(){
		contents.clear();
	}
	
	public HashMap<String, Integer>  getContents() {
        return contents;
	}
	
	public  void setContents(HashMap<String, Integer> contents) {
        this.contents = contents;
	}
	
    @Remove
    public void remove() {
        contents = null;
    }
}
