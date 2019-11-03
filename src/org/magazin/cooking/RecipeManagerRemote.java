package org.magazin.cooking;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.*;


@Remote
public interface RecipeManagerRemote {
		void addRecipe (String name, String description, byte [] image);
		Recipe updateRecipe (Recipe recipe);
		void deleteRecipe (String name);
		Set<Ingredient> getIngredientListForRecipe(String recipeName);
}
