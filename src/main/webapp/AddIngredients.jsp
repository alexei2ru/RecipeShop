<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles/recipelist.css">
<title>Add Ingredients</title>
</head>

<body>
<c:remove var="findBean" scope="session"/>
<jsp:useBean id="findBean" class="org.magazin.cooking.RecipeManagerBean" scope="session" />

<c:set var="recipeName" value='${param["recipeName"]}' />
<br>
<h3>Ingredient list for: ${recipeName}</h3>
<table>
	<tr>
		<th>Name</th>
		<th>Price</th>
	</tr>
	<c:forEach items="${findBean.recipeList}" var="recipe">
		<c:if test="${recipe.name.equals(recipeName)}">
			<c:forEach items="${recipe.ingredientList}" var="ingredient">
				<tr>
					<td>${ingredient.name}</td>
					<td>${ingredient.price}</td>
					<td><form action="/RecipeController?action=deleteIngredient&ingredientName=<c:out value='${ingredient.name}' />
					&recipeName=<c:out value='${recipeName}' />" method="post">
					<input type="submit" value="delete" />
					</form>
				</tr>
			</c:forEach>
			<tr>
				<td>
					<form
						action="/RecipeController?action=addIngredient&recipeName=<c:out value='${recipeName}' />"
						method="post">
						<table style="with: 50%">
							<tr>
								<td>Ingredient Name</td>
								<td><input type="text" name="name" /></td>
							</tr>
							<tr>
								<td>Ingredient Price</td>
								<td><input type="text" name="price" /></td>
							</tr>
						</table>
						<input type="submit" value="Add" />
					</form>
				</td>
			</tr>
		</c:if>


	</c:forEach>
</table>
<form action="Retete.jsp">

	<input type="submit" value="Done" />
</form>
</body>
</html>