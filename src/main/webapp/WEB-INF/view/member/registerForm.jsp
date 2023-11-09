<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오목게임 회원가입 </title>
<style>
p#correct {
	display: none;
}
p#incorrect {
	display: none;
}
</style>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
function checkName(){
    var _id=$("#t_id").val();
    if(_id==''){
   	 alert("ID를 입력하세요");
   	 return;
    }
    $.ajax({
       type:"post",
       async:true,  
       url:"checkName",
       dataType:"text",
       data: {id:_id},
       success:function (data,textStatus){
    	   
          if(data=='usable'){
        	  alert("사용할 수 있는 ID입니다.")
       	   	$('#btnDuplicate').prop("disabled", true);
          }else{
        	  alert("사용할 수 없는 ID입니다. 다시 입력해주세요.")
          }
       },
       error:function(data,textStatus){
          alert("에러가 발생했습니다.");ㅣ
       },
       complete:function(data,textStatus){
          //alert("작업을완료 했습니다");
       }
    });  //end ajax	 
 }	

function checkPw() {
	console.log("1번", $("#pw_1").val())
	console.log("2번", $("#pw_2").val())
	if (($("#pw_1").val()) != $("#pw_2").val()) {
		//alert("비밀번호가 다릅니다. 다시 입력해주세요.")
		$('#correct').hide();
		$('#incorrect').show();
		$('#btnRegister').prop("disabled", true);
	} else {
		//alert("비밀번호가 일치합니다.")
		$('#correct').show();
		$('#incorrect').hide();
		$('#btnRegister').prop("disabled", false);
	}
}

 
function goHome() {
	//location.href="<c:url value='/login.do'/>";
	location.href="/omoomo/home.do";
}

</script>
<link href="signup_css.css" rel="stylesheet">
</head>
<body>
    <div id="container">
        <div id="wrapper">
            <div id="signup_logo"></div>
            <div id="signup_main">
                <form action="register.do" method="post" id="signup_form">

                    <div id="signup">회원가입</div>
                    <div id="signup_id">
                        <input type="text" name="user_name" id="t_id" placeholder="아이디를 입력하세요" />
                        <input type="button" id="btnDuplicate" value="중복확인" onClick="checkName()" />
                    </div>
                    <input type="password" name="user_pw" id="pw_1" placeholder="비밀번호를 입력하세요" />
                    <input type="password" name="user_pw_repeat" id="pw_2" oninput="checkPw()"
                        placeholder="비밀번호를 입력하세요" />
                    <p id="correct"> 비밀번호가 일치합니다.</p>
                    <p id="incorrect"> 비밀번호가 일치하지 않습니다.</p>
                    <p>
                        <input type="button" value="돌아가기" onClick="goHome()" id="back_btn">
                        <input type="submit" value="가입하기" id="btnRegister">
                </form>
            </div>
        </div>
    </div>
</body>

</html>