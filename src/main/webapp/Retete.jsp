<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.magazin.cooking.*"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="styles/recipelist.css">
<title>Recipe List</title>
</head>

<h3>Lista de retete</h3>
<body style="height: 334px;">
    <c:remove var="displayBean" scope="session"/>
	<jsp:useBean id="displayBean" class="org.magazin.cooking.RecipeManagerBean" scope="session" />

	<form action="/RecipeController?action=viewBasket" method="post">

		<input type="submit" value="View basket" />
	</form>
	<br>
	<table>
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Ingredients
			<th>
		</tr>
		<c:forEach items="${displayBean.recipeList}" var="recipe">
			<tr>
				<td>${recipe.name}</td>
				<td>${recipe.description}</td>
				<td><c:if test="${not empty recipe.ingredientList}">

						<c:forEach items="${recipe.ingredientList}" var="ingredient">
                  				${ingredient.name} 
                  	    <br>
						</c:forEach>
					</c:if> <c:if test="${empty recipe.ingredientList}">

						<form
							action="/AddIngredients.jsp?recipeName=<c:out value='${recipe.name}' />"
							method="post">
							<input type="submit" value="Add Ingredient" />
						</form>
					</c:if></td>
				<td><a
					href="/AddIngredients.jsp?recipeName=<c:out value='${recipe.name}' />">Edit</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <a
					href="/RecipeController?action=delete&recipeName=<c:out value='${recipe.name}' />">Delete</a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<form
						action="/RecipeController?action=addToBasket&recipeName=<c:out value='${recipe.name}' />"
						method="post">
						<input type="submit" value="Add to basket" />
					</form></td>
			<tr>
				<td colspan="3" align="center"><img
					src="data:image/jpg;base64,${recipe.base64Image}" alt="No image"
					width="160" height="120"></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<form action="AddRecipe.jsp">

		<input type="submit" value="Add recipe" />
	</form>

	<c:if test="${not empty message}">
		<h3>${message}</h3>
	</c:if>
</body>
</html>