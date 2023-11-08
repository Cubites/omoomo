<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% 
String result = request.getParameter("isLogOn");
request.setAttribute("result", result);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function register() {
	//location.href="<c:url value='/register.do'/>";
	location.href="/omoomo/register.do";
}
function login() {
	//location.href="<c:url value='/login.do'/>";
	location.href="/omoomo/login.do";
}

</script>

</head>

<body>
<h1>메인홈화면입니다.</h1>
<form method="post" action="login.do" encType="UTF-8">
  <p> 아이디  <input type="text" name="user_name"><br>
  <p> 비밀번호 <input type="password" name="user_pw">
  <input type="submit" value="로그인" >
</form>
<button onclick="register()">회원가입</button>

<c:if test="${result =='false'}">
	<script type="text/javascript">
		alert("다시로그인해주셔유~");
	</script>
</c:if>
</body>
</html>