<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/recipelist.css">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${not empty message}">
		<h3>${message}</h3>
	</c:if>
	<c:if test="${not empty contents}">
		<table>

			<c:forEach items="${contents}" var="ingredient">
				<tr>
					<td><c:out value="${ingredient.key}" /></td>
					<td><c:out value="${ingredient.value}" /></td>
					<td><a href="/RecipeController?action=removeIngredient&ingredientName=<c:out value='${ingredient.key}' />" >Delete</a></td>
				</tr>
			</c:forEach>

		</table>

		<form action="/RecipeController?action=buy" method="post">

			<input type="submit" value="Buy" />
		</form>
	</c:if>
	<br>
	<form action="Retete.jsp">

		<input type="submit" value="back" />
	</form>

</body>
</html>