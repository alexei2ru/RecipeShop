package org.magazin.shopping;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.*;

import org.magazin.cooking.Ingredient;


@Local 
public interface BasketManager {
	void addItem (String ingredientName);
	void removeItem (String ingredientName);
	void addItemList( Set<Ingredient> ingredientList);
	HashMap<String, Integer> getContents();
	void clearContents();
	
}
