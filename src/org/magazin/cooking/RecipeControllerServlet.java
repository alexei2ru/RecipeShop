package org.magazin.cooking;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.magazin.queue.MessageSenderBean;
import org.magazin.queue.MessageSenderLocal;
import org.magazin.shopping.BasketManager;

/**
 * Servlet implementation class CreateRecipeServlet
 */



@WebServlet("/RecipeController")
@MultipartConfig
public class RecipeControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB 
	private RecipeManagerLocal recipeManagerLocal;
	@EJB
	private BasketManager basketManager;
	@EJB
	private MessageSenderLocal messageSender;
	
	private Logger LOGGER = LogManager.getLogger(this.getClass().getSimpleName());   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecipeControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if (action.equals("delete")){
			String recipeName = request.getParameter("recipeName");
			LOGGER.info("Recipe name: " +recipeName);
			String message = "Recipe " + recipeName + " was deleted";
			
			recipeManagerLocal.deleteRecipe(recipeName);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Retete.jsp");
			 request.setAttribute("message", message);
			 dispatcher.forward(request, response);
		}
		
		 if (action.equals("removeIngredient")){
			 String ingredientName = request.getParameter("ingredientName");
			 LOGGER.info("ingredient name: " + ingredientName);
			 basketManager.removeItem(ingredientName);
			 HashMap<String, Integer> objectMap = basketManager.getContents(); //get basket contents
			 if (!objectMap.isEmpty()){
				 
				 request.setAttribute("contents", objectMap);
			 }
			 else{
				 String message = "Ingredient list is empty";
				 request.setAttribute("message", message);
			 }
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ListAdded.jsp");
			 dispatcher.forward(request, response);
		 }
		 
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getParameter("action");
		 
		 // view basket contents
		 if (action.equals("viewBasket")){
			 HashMap<String, Integer> objectMap = basketManager.getContents(); //get basket contents
			 if (!objectMap.isEmpty()){
				 
				 request.setAttribute("contents", objectMap);
			 }
			 else{
				 String message = "Ingredient list is empty";
				 request.setAttribute("message", message);
			 }
			 request.getRequestDispatcher("ListAdded.jsp").forward(request, response);
			 doGet(request, response);
			 
		  }
		 
		
		 //buy basket contents
		 if (action.equals("buy")){
			 LOGGER.info("Sending ingredient map to queue");
			
					HashMap<String, Integer> objectMap = basketManager.getContents();
					if (objectMap.isEmpty()){
						LOGGER.info("Object Map is null");
						 
					 }
					else{
						try {
							messageSender.sendMapMessage(objectMap);
							LOGGER.info("message sent to queue");
							String message = "Buy successful";
							basketManager.clearContents();
							request.setAttribute("message", message);
							request.getRequestDispatcher("Retete.jsp").forward(request, response);
							doGet(request, response);
						} 
						catch (JMSException e) {
						// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
					
			}
		
		 
		 
		 //Add recipe 
		 if (action.equals("addRecipe")){
		
			 String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
			 String name = request.getParameter("name");
			 Part filePart = request.getPart("image"); // Retrieves <input type="file" name="file">
			 InputStream fileContent = filePart.getInputStream(); 
			 byte[] fileAsByteArray = IOUtils.toByteArray(fileContent); // creates byte array from file
			 recipeManagerLocal.addRecipe(name, description, fileAsByteArray); 
			 String message = "Recipe added successfully";
			 request.setAttribute("name", name);
			 request.setAttribute("message", message);
			 request.getRequestDispatcher("/AddRecipe.jsp").forward(request, response);
			 doGet(request, response);
		 }
		 
		 // Add ingredient list to basket
		 if (action.equals("addToBasket")){
			 String recipeName = request.getParameter("recipeName");
			 String message = "Ingredients for recipe " + recipeName + "were added to basket";
			 
			 Set<Ingredient> ingredientList = recipeManagerLocal.getIngredientListForRecipe(recipeName);
			 if (ingredientList.isEmpty()){
				 LOGGER.error("Ingredient list is empty");
			 }
			 basketManager.addItemList(ingredientList);
			 LOGGER.info("Ingredients for recipe " + recipeName + "were added to basket");
			 HashMap<String, Integer> objectMap = basketManager.getContents(); //get basket contents
			 if (!objectMap.isEmpty()){
				 
				 LOGGER.info("Object map populated");
				 request.setAttribute("contents", objectMap);
			 }
			 
			 request.setAttribute("message", message);
			 request.getRequestDispatcher("ListAdded.jsp").forward(request, response);
			 doGet(request, response);
		 }
		 // Add ingredient to recipe
		 if (action.equals("addIngredient")){
			 String recipeName = request.getParameter("recipeName");
			 LOGGER.info("Recipe name: " + recipeName);
			 int ingredientPrice = Integer.parseInt(request.getParameter("price")); // Retrieves <input type="text" name="price">
			 String ingredientName = request.getParameter("name");
			 			 
			 Ingredient ingredient = new Ingredient();
			 ingredient.setName(ingredientName);
			 ingredient.setPrice(ingredientPrice);
			 recipeManagerLocal.addIngredient(ingredientName, ingredientPrice, recipeName);
			 LOGGER.info("Added ingredient " + ingredientName + " to recipe" + recipeName);
			 String message = "Ingredient " + ingredientName +  " for recipe " + recipeName + "were added to basket";
			 request.setAttribute("message", message);
			 request.setAttribute("recipeName", recipeName);
			 request.getRequestDispatcher("AddIngredients.jsp").forward(request, response);
			 doGet(request, response);
		 }
		 
		 if (action.equals("deleteIngredient")){
			 String ingredientName = request.getParameter("ingredientName");
			 LOGGER.info("Ingredient name: " + ingredientName);
			 String recipeName = request.getParameter("recipeName");
			 
			 recipeManagerLocal.removeIngredient(ingredientName, recipeName);
			 LOGGER.info("Remove ingredient " + ingredientName + " from recipe" + recipeName);
			 String message = "Ingredient " + ingredientName +  " for recipe " + recipeName + "was removed";
			 request.setAttribute("message", message);
			 request.setAttribute("recipeName", recipeName);
			 RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/AddIngredients.jsp");
			 dispatcher.forward(request, response);
			 doGet(request, response);
			 
		 }
		 
	}

}
