<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
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
<button onclick="register()">회원가입</button>
<button onclick="login()">로그인</button>
</body>
</html>