import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.magazin.cooking.Ingredient;

import org.magazin.cooking.RecipeManagerBean;

public class DeleteIngredientTest {
	RecipeManagerBean bean = new RecipeManagerBean();
	private final String recipeName = "Mamaliga cu branza";
	private final String ingredientName = "Malai";
	
	@Test
	public void testing_deleteIngredient_shouldBeSuccessfull() {           
        Optional<Ingredient> ingredient = bean.findIngredientInRecipe(ingredientName, recipeName);
        assertTrue(ingredient.isPresent());
        bean.removeIngredient(ingredientName, recipeName);
		
	}

}
