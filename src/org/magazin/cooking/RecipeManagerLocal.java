package org.magazin.cooking;
import java.awt.Image;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.*;


@Local
public interface RecipeManagerLocal {
		void addRecipe (String name, String description, byte [] image);
		Recipe updateRecipe (Recipe recipe);
		void deleteRecipe (String name);
		Set<Ingredient> getIngredientListForRecipe(String recipeName);
		void addIngredient(String name, int price, String recipeName);
		void removeIngredient(String ingredientName, String recipeName);
}
