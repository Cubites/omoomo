<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목게임 회원가입</title>
<link href="signup_css.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	// 아이디 중복 확인 함수
	function checkName() {
		var _id = $("#t_id").val();
		if (_id == '') {
			alert("ID를 입력하세요");
			return;
		}
		$.ajax({
			type : "post",
			async : true,
			url : "checkName",
			dataType : "text",
			data : {
				id : _id
			},
			success : function(data, textStatus) {
				// 정상적으로 통신이 이뤄졌을 때, 받아온 값 확인
				if (data == 'usable') {
					alert("사용할 수 있는 ID입니다.")
				} else {
					alert("사용할 수 없는 ID입니다. 다시 입력해주세요.")
				}
			},
			error : function(data, textStatus) {
				alert("에러가 발생했습니다.");
			}
		}); //end ajax	 
	}
	
	// 비밀번호 확인 함수
	function checkPw() {
		console.log("1번", $("#pw_1").val())
		console.log("2번", $("#pw_2").val())
		
		// 사용자가 입력한 두 비밀번호가 동일한지 확인
		if (($("#pw_1").val()) != $("#pw_2").val()) {
			$('#correct').hide();
			$('#incorrect').show();
			$('#btnRegister').prop("disabled", true);
		} else {
			$('#correct').show();
			$('#incorrect').hide();
			$('#btnRegister').prop("disabled", false);
		}
	}
	
	// 돌아가기 버튼 클릭 시 홈으로 이동
	function goHome() {
		location.href = "/omoomo/home.do";
	}
</script>
</head>

<body>
	<div id="container">
		<div id="wrapper">
			<div id="signup_logo"></div>
			<div id="signup_main">
			
				<!-- 아이디, 비밀번호 입력 후 register.do로 정보 보냄 -->
				<form action="register.do" method="post" id="signup_form">
					<div id="signup">회원가입</div>
					<!-- 아이디 입력창 -->
					<div id="signup_id">
						<input type="text" name="user_name" id="t_id"
							placeholder="아이디를 입력하세요" /> <input type="button"
							id="btnDuplicate" value="중복확인" onClick="checkName()" />
					</div>
					<!-- /아이디 입력창 -->

					<!-- 비밀번호 입력창 -->
					<input type="password" name="user_pw" id="pw_1"
						placeholder="비밀번호를 입력하세요" /> <input type="password"
						name="user_pw_repeat" id="pw_2" oninput="checkPw()"
						placeholder="비밀번호를 입력하세요" />
					<p id="correct">비밀번호가 일치합니다.</p>
					<p id="incorrect">비밀번호가 일치하지 않습니다.</p>
					<!-- /비밀번호 입력창 -->

					<div id="btns">
						<input type="button" value="돌아가기" onClick="goHome()" id="back_btn">
						<input type="submit" value="가입하기" id="btnRegister">
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>