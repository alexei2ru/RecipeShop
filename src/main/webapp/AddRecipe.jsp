<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/recipelist.css">
<title>Add Recipe</title>
</head>
<body>

	<!-- <form action="RecipeController" enctype="multipart/form-data" method="post"> -->
	<form action="/RecipeController?action=addRecipe"
		enctype="multipart/form-data" method="post">
		<table style="with: 50%">
			<tr>
				<td>Recipe Name</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Recipe Description</td>
				<td><input type="text" name="description" /></td>
			</tr>
			<tr>
				<td>Image</td>
				<td><input type="file" name="image" size="50" /></td>
			</tr>
		</table>
		<input type="submit" value="Submit" />
	</form>

	<c:if test="${not empty message}">
		<h3>${message}</h3>
		<h3>${name}</h3>
	</c:if>
	<form action="Retete.jsp">

		<input type="submit" value="Done" />
	</form>
</body>
</html>