<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

User
<s:property value="uname" />
was not found or password incorrect!
<form action="Login.jsp">

	<input type="submit" value="back" />
</form>

</body>
</html>