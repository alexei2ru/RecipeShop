
import static org.junit.Assert.*;
import java.util.List;
import org.magazin.cooking.*;
import org.junit.Test;


public class RecipeTest  {

	RecipeManagerBean bean = new RecipeManagerBean();
	private final String recipeName = "Mamaliga cu branza";
	
	
	@Test
	public void testing_addRecipeMethod_shouldBeSuccessfull(){
		
        bean.addRecipeTest(recipeName, "Mamaliga cu branza si smantana a la Cluj", "mamaligacubranza.jpg");     
        List<Recipe> recipes = bean.getRecipeList();
                       
        assertTrue(recipes.size()!=0 && recipes.get(0).getName().equals(recipeName));

	}
	

	
	@Test
	public void testing_addIngredientMethod_shouldBeSuccessfull(){
		
		
        bean.addIngredient("Malai", 1, recipeName);    
        bean.addIngredient("Branza", 10, recipeName);
        bean.addIngredient("Smantana", 25, recipeName);
        
        Recipe recipe = bean.findRecipeByName(recipeName);             
        assertTrue(recipe.getIngredientList().size() != 0);
               
        recipe.getIngredientList().forEach((x) -> {
        assertTrue(x.getName().equals("Malai") || x.getName().equals("Branza") || x.getName().equals("Smantana"));        	
        });
	}

}
