package org.magazin.cooking;
import javax.ejb.*;
import javax.persistence.*;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.magazin.helpers.JPAService;
import org.magazin.helpers.ImageConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecipeManagerBean extends Recipe implements RecipeManagerLocal{

	private Logger LOGGER = LogManager.getLogger(this.getClass().getSimpleName());
	private List<Recipe> recipeList;
	
	@PersistenceContext(unitName = "DBLink")
	private EntityManager em;
	
	@Resource
	private UserTransaction userTransaction;
	
	public RecipeManagerBean(){
		initialize();
	}
	@PostConstruct
	
	public void initialize(){
		em = JPAService.getEntityManager();
		LOGGER.info("entity manager initialization done");
		
	}
  
	public void addIngredientTest(String name, int price, String recipeName){
		Ingredient ingredient = new Ingredient();
		ingredient.setName(name);
		ingredient.setPrice(price);
		this.getRecipeList().get(0).getIngredientList().add(ingredient);
		
		em.getTransaction().begin();
		em.persist(ingredient); 
		em.getTransaction().commit();	
    }
	
	public void addIngredient(String name, int price, String recipeName){
		Ingredient ingredient = new Ingredient();
		Recipe recipe = new Recipe();
		recipe = this.findRecipeByName(recipeName);
		
		LOGGER.info("recipe  name" + recipe.getName());
		ingredient.setName(name);
		ingredient.setPrice(price);
		
		try {
	        userTransaction.begin();
	        em.joinTransaction();
	        LOGGER.info("Transaction begun");
	        recipe.getIngredientList().add(ingredient);
	        if (this.findIngredientByName(name) != null){
	        	ingredient.getRecipeList().add(recipe);
	        }
	        else{
	        	em.persist(ingredient);
	        }
	        
	        em.flush();
	        userTransaction.commit();
        }
        catch (Exception e) {
			LOGGER.error("Crap, this should not happen");
			e.printStackTrace();
		}
	
    }
	public Optional<Ingredient> findIngredientInRecipe (String ingredientName, String recipeName){
		Recipe recipe = this.findRecipeByName(recipeName);
		Set<Ingredient> ingredients = recipe.getIngredientList();
		return ingredients.stream().filter(i -> i.getName().equals(ingredientName)).findAny();
	}
	
	public void removeIngredient(String ingredientName, String recipeName){
		Ingredient ingredient = new Ingredient();
		Recipe recipe = new Recipe();
		ingredient = this.findIngredientByName(ingredientName);
        recipe = this.findRecipeByName(recipeName);
		try {
	        userTransaction.begin();
	        em.joinTransaction();
	        recipe.getIngredientList().remove(ingredient);
	        em.persist(recipe);
	        em.flush();
	        userTransaction.commit();
        }
        catch (Exception e) {
			LOGGER.error("Crap, this should not happen");
			e.printStackTrace();
		}
		
    }
	
	public String getImageByName (String name) {
		TypedQuery<Recipe> query = em.createNamedQuery("Recipe.findByName", Recipe.class)
				.setParameter("recipeName", name);
		byte[] image = query.getSingleResult().getImage();
		return Base64.getEncoder().encodeToString(image);
		
	}

	   
	public void addRecipe(String name, String description, byte[] image) {
		Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setImage(image);
        try {
	        userTransaction.begin();
	        em.joinTransaction();
	        em.persist(recipe);
	        em.flush();
	        userTransaction.commit();
        }
        catch (Exception e) {
			LOGGER.error("Crap, this should not happen");
			e.printStackTrace();
		}
    }
	
	public Set<Ingredient> getIngredientListForRecipe(String recipeName){
		Recipe recipe = new Recipe();
		try {
//			userTransaction.begin();
//	        em.joinTransaction();
			LOGGER.info("Getting ingredient list");
			recipe = this.findRecipeByName(recipeName);
//	        em.flush();
//	        userTransaction.commit();
//	        em.close();
//	        if (recipes == null || recipes.isEmpty()) {
//	        	LOGGER.error("Ingredient list is null");
//	        	
//	            return null;
//	        }
	        
//	        return  recipes.get(0).getIngredientList();
			Set<Ingredient> ingredientList = recipe.getIngredientList();
			LOGGER.info("Got ingredient list");
			return ingredientList;
		}
		catch (Exception e) {
			LOGGER.error("Crap, this should not happen");
			e.printStackTrace();
			return null;
		}
    }

	public void addRecipeTest(String name, String description, String imageFileName){
		Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setImage(ImageConverter.toByteArray(imageFileName));
        em.getTransaction().begin();
        em.persist(recipe); 
        em.getTransaction().commit();
        em.close();
    }
	
	public Recipe findRecipeByName (String recipeName){
		LOGGER.info("Searching for recipe:" + recipeName);
		TypedQuery<Recipe> query = em.createNamedQuery("Recipe.findByName", Recipe.class)
				.setParameter("recipeName", recipeName);
        List<Recipe> recipes = query.getResultList();
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        return (Recipe)recipes.get(0);
	}
	

	public Ingredient findIngredientByName (String ingredientName){
		LOGGER.info("Searching for ingredient:" + ingredientName);
		TypedQuery<Ingredient> query = em.createNamedQuery("Ingredient.findByName", Ingredient.class)
				.setParameter("ingredientName", ingredientName);
        List<Ingredient> ingredients = query.getResultList();
        if (ingredients == null || ingredients.isEmpty()) {
            return null;
        }
        return (Ingredient)ingredients.get(0);
	}
	
	public void deleteRecipe (String recipeName) {
		
		LOGGER.info("Deleting recipe:" + recipeName);
		 Recipe recipe = new Recipe();
		 recipe = (Recipe)this.findRecipeByName(recipeName);
		 LOGGER.info("Found recipe  name" + recipe.getName());
		 for (Ingredient ingredient: recipe.getIngredientList()){
			 	if (ingredient.getRecipeList().size() == 1){
			 		em.remove(ingredient);
			 	}
			 	else{
			 		ingredient.getRecipeList().remove(recipe);
			 	}
			 	
		 }
		 
		try {
	        userTransaction.begin();
	        em.joinTransaction();
	        em.remove(recipe);
	        em.flush();
	        userTransaction.commit();
        }
        catch (Exception e) {
			LOGGER.error("Crap, this should not happen");
			e.printStackTrace();
			
		}
	}
	
	
	public void deleteRecipeTest (String name) {
		em.getTransaction().begin();
		Recipe recipe = this.findRecipeByName(name);
		for (Ingredient ingredient : recipe.getIngredientList()) {
		    if (ingredient.getRecipeList().size() == 1) {
		        em.remove(ingredient);
		    } else {
		        ingredient.getRecipeList().remove(recipe);
		    }
		}
		em.remove(recipe);
		em.getTransaction().commit();
		em.close();
	}
	
	public List<Recipe> getRecipeList() {
		TypedQuery<Recipe> query = em.createNamedQuery("Recipe.findAll", Recipe.class);
		
        recipeList = (List<Recipe>)query.getResultList();
        
//        recipeList.forEach((x) -> {
//            LOGGER.info("Found Recipe: " + x.getName());
//            LOGGER.info("Found Recipe: " + x.getDescription());
//            
//        });
        return recipeList;
    }
	
	public void setRecipeList(List<Recipe> recipeList) {
		this.recipeList = recipeList;
	}
	
	public Recipe updateRecipe(Recipe recipe) {
        em.joinTransaction();
        em.merge(recipe);
        return recipe;
	}
	
	@PreDestroy
    public void cleanup() {
        if(em.isOpen()){
        	em.close();
        } 
	}
}
