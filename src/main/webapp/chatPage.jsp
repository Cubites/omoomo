<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		*{
			margin: 0;
			padding: 0;
			box-sizing: border-box;
		}
		/* total page */
		#container{
			display: flex;
			flex-direction: row;
			width: 100%;
			min-width: 1600px;
			height: 100vh;
			min-height: 900px;
		}
		/** game area **/
		#gamepage{
			width: 80%;
			height: 100%;
			background-color: #ddd;
			display: flex;
			flex-direction: row;
			justify-content: center;
			align-items: center;
		}
		#boardback{
			width: 810px;
			height: 810px;
			/* border: 1px solid #000; */
			background-color: #999;
			border-radius: 20px;
			box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25), 8px 8px 10px rgba(0, 0, 0, .45);
			background-color: #916D5E;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			position: relative;
		}
		#boardfront{
			width: 754px;
			height: 754px;
			background-color: #C3A69A;
			background-image: url('./images/lines.png');
			background-position: -3px -3px;
			/* background-size: cover; */
			background-size: 754px 754px;
			box-sizing: border-box;
			border: 3px solid #000;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
		}
		/*** stone area ***/
		#stoneboard{
			width: 810px;
			height: 810px;
			background-color: transparent;
			display: flex;
			flex-direction: column;
			justify-content: center;
			position: absolute;
			top: 0;
			left: 0;
		}
		/**** stone design ****/
		.rowStoneBox{
			width: 100%;
			height: 42px;
			display: flex;
			flex-direction: row;
			justify-content: center;
			align-items: center;
		}
		.stoneCell{
			width: 42px;
			height: 42px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
		}
		.stone{
			width: 38px;
			height: 38px;
			border-radius: 19px;
			background-color: #101010;
			box-shadow: inset 4px 4px 5px rgba(255, 255, 255, .25), inset -4px -4px 5px rgba(0, 0, 0, .25);
			cursor: pointer;
			opacity: 0;
		}
		/*** stone bowl design ***/
		.bowl{
			width: 20%;
			height: 100%;
			display: flex;
			flex-direction: row;
			justify-content: center;
			padding: 30px 0;
		}
		.bowlImage{
			width: 12vw;
			min-width: 200px;
			height: 12vw;
			min-height: 200px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			border-radius: 150px;
			box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
		}
		.bowlImageInside{
			width: 10vw;
			height: 10vw;
			border-radius: 120px;
			box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -6px -6px 10px rgba(255, 255, 255, .25);
		}
		/** chat area **/
		#chatpage{
			width: 20%;
			height: 100%;
			background-color: #999;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			align-items: center;
			padding: 10px;
		}
		/*** user list area ***/
		#userlist{
			width: 90%;
			height: 10%;
			background-color: #ddd;
			border-radius: 10px;
			font-size: 30px;
		}
		/*** chat message area ***/
		#messagesTextArea{
			width: 90%;
			height: 80%;
			border-radius: 10px;
			padding: 10px;
		}
		/*** chat input area ***/
		#inputbox{
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			width: 90%;
			height: 5%;
		}
		#messageText{
			width: 75%;
			height: 100%;
			border-radius: 10px;
			padding: 5px;
			border: none;
		}
		#messageText+input{
			width: 20%;
			height: 100%;
			border: none;
			border-radius: 10px;
			box-shadow: inset 4px 4px 10px rgba(255, 255, 255, .25), inset -4px -4px 10px rgba(0, 0, 0, .25);
			background-color: #C3A69A;
		}
		#messageText+input:hover{
			background-color: #a78a7d;
			cursor: pointer;
		}
	</style>
</head>
<body>
	<div id="container">
		<!-- 오목판 영역 -->
		<div id="gamepage">
			<div class="bowl" style="align-items: start;">
				<div class="bowlImage" 
					style="background-color: #e8e8e8;">
					<div class="bowlImageInside" 
						style="background-color: #bbbbbb;"></div>
				</div>
			</div>
			<div id="boardback">
				<div id="boardfront">
					<div id="stoneboard">
						<c:forEach var="i" begin="0" end="18">
							<div class="rowStoneBox">
								<c:forEach var="j" begin="0" end="18">
									<div class="stoneCell">
										<div class="stone" data-h="${i}" data-v="${j}"></div>
									</div>
								</c:forEach>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="bowl" style="align-items: end;">
				<div class="bowlImage" style="background-color: #252525">
					<div class="bowlImageInside" style="background-color: #101010"></div>
				</div>
			</div>
		</div>
		<!-- /오목판 영역 -->
		<!-- 채팅 창 영역 -->
		<div id="chatpage">
			<div id="userlist">
				<p id="usermine"></p>
				<p id="userother"></p>
			</div>
			<span>username: <%=session.getAttribute("username") %></span><br>
			<textarea id="messagesTextArea" readonly="readonly" rows="30"></textarea><br>
			<div id="inputbox">
				<input type="text" id="messageText" size="50">
				<input type="button" value="Send" onclick="sendMessage();">
			</div>
		</div>
		<!-- /채팅 창 영역 -->
	</div>
	<script>
		// 소켓 연결 요청
		const websocket = new WebSocket("ws://localhost:8090/omoomo/chatroomServerEndpoint");
		// 소켓 서버에서 오는 메세지 - 메시지
		websocket.onmessage = function processMessage(message) {
			var jsonData = JSON.parse(message.data);
			if(jsonData.message != null) messagesTextArea.value += jsonData.message + "\n";
		}
		// 소켓 서버에서 오는 메시지 - 에러
		websocket.onerror = function(e){
			console.log(e);
		}
		
		// 채팅 보내는 함수
		function sendMessage() {
			var message = {
				sign: "chat",
				m: messageText.value
			}
			websocket.send(JSON.stringify(message)); // JSON 객체를 String으로 변환하여 전송
			messageText.value = "";
		}

		// 돌을 놓은 좌표를 보내는 함수
		document.getElementById("boardfront").addEventListener('click', (e) => {
			var message = {
				sign: "stone",
				x: e.offsetX,
				y: e.offsetY
			}
			websocket.send(JSON.stringify(message)); // JSON 객체를 String으로 변환하여 전송
		});

		Array.from(document.getElementsByClassName("stone")).forEach((stone) => {
			stone.addEventListener('click', (e) => {
				console.log("h: " + e.target.dataset.h);
				console.log("v: " + e.target.dataset.v);
			});
			stone.addEventListener('mouseenter', (e) => {
				e.target.style.opacity = 0.5;
			});
			stone.addEventListener('mouseleave', (e) => {
				if(e.target.style.opacity != 1){
					e.target.style.opacity = 0;
				}
			});
			stone.addEventListener('click', (e) => {
				e.target.style.opacity = 1;
			});
		})
	</script>
</body>
</html>