
import static org.junit.Assert.*;
import org.magazin.cooking.*;
import org.junit.Test;

public class DeleteRecipeTest {
	RecipeManagerBean bean = new RecipeManagerBean();
	private final String recipeName = "Mamaliga cu branza";
	
	@Test
	public void testing_deleteRecipeMethod_shouldBeSuccessfull(){
		bean.deleteRecipeTest(recipeName);
		Recipe recipe = bean.findRecipeByName(recipeName);
		assertTrue(recipe == null);
	}	

}
