<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script>
	$(function() {
		$("#sendRoomInfo").on("click", function() {
			var roomname = $("#roomname").val();
			var mode = $('input[name="mode"]:checked').val();
			$.ajax({
				url:'updateRoom.do',
				type:"post",
				data:{
					"roomname":roomname,
					"mode":mode
				},
				success:function(data){
					alert("방 정보 전송 완");
					opener.location.reload();
					window.close();
				},
				error:function(request,status,error){
			        alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			    }
			});
			
			//console.log(roomname);
			//console.log(mode);
			//window.close();
		})
	})
</script>

</head>
<body>
	방이름 <input type="text" id="roomname"><br> 
	모드 선택 <br>
	<input type="radio" name="mode" value="classic" />기본 오목 
	<input type="radio" name="mode" value="33" />33 금지 
	<input type="radio" name="mode" value="44" />44 금지 <br>
	<button id="sendRoomInfo">확인</input>
	<button onclick="self.close()">취소</button>

</body>
</html>