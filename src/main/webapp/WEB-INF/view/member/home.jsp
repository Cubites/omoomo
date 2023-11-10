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
	<link href="login_css.css" rel="stylesheet">
	<script type="text/javascript">
		function register() {
			//location.href="<c:url value='/register.do'/>";
			location.href="/omoomo/register.do";
		}
		function login() {
			//location.href="<c:url value='/login.do'/>";
			location.href="/omoomo/login.do";
		}
        if(<%=result%> == false) {
            alert("다시 로그인 해주세요.");
        }
	</script>
</head>
<body>
    <div id="container">
        <div id="main_logo"></div>
        <div id="main">
            <div id="first"></div>
            <div id="second">

                <form method="post" action="login.do" encType="UTF-8" id="login_form">
                    <div id="name_pw">
                        <input type="text" name="user_name" id="user_name" placeholder="아이디를 입력하세요"><br>
                        <input type="password" name="user_pw" id="user_pw" placeholder="비밀번호를 입력하세요">
                    </div>
                    <div id="loginBtn_div">
                        <input type="submit" value="로그인" id="login_btn">
                    </div>
                </form>
                <div id="signup_link">
                    <span>아직 회원이 아니신가요?</span>
                    <a href="/omoomo/register.do">회원가입 하러가기</a>
                </div>
            </div>
        </div>
    </div>

</body>
</html>