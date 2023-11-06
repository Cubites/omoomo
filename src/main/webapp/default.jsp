<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="http://localhost:8090/omoomo/UserNameServlet">
		<span>Please supply a name:</span><br>
		<input type="text" name="username" size="20">
		<input type="text" name="roomNumber" size="20">
		<input type="submit" value="Enter">
	</form>
</body>
</html>