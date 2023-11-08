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
		.dragNo{
			-webkit-user-select:none;
			-moz-user-select:none;
			-ms-user-select:none;
			user-select:none;
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
			min-width: 1280px;
			height: 100%;
			background-color: #ddd;
			display: flex;
			flex-direction: row;
			justify-content: center;
			align-items: center;
		}
		#boardback{
			width: 810px;
			min-width: 810px;
			height: 810px;
			min-height: 810px;
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
		/*** cover ***/
		#cover{
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: rgba(0, 0, 0, .45);
			z-index: 999;
			border-radius: 20px;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		#cover>div{
			background-color: #C3A69A;
			color: #3d2c25;
			text-align: center;
			width: 600px;
			height: 200px;
			border-radius: 20px;
			font-weight: bold;
			font-size: 25px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
		}
		/*** stone bowl design ***/
		.bowl{
			width: 20%;
			height: 100%;
			display: flex;
			flex-direction: column;
			align-items: center;
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
			min-width: 170px;
			height: 10vw;
			min-height: 170px;
			border-radius: 120px;
			box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -6px -6px 10px rgba(255, 255, 255, .25);
		}
		/*** userNickName ***/
		.userNickName{
			width: 12v;
			min-width: 200px;
			height: 50px;
			border-radius: 10px;
			background-color: #f9f9f9;
			border: 3px solid #101010;
			box-shadow: inset 4px 4px 10px rgba(0, 0, 0, .25), inset -6px -6px 10px rgba(255, 255, 255, .25);
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			margin: 20px 0;
		}
		/** chat area **/
		#chatpage{
			width: 20%;
			min-width: 320px;
			height: 100%;
			background-color: #999;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			align-items: center;
			padding: 10px;
		}
		/*** button area ***/
		#readyExitBox{
			width: 90%;
			height: 10%;
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;
		}
		.readyButton{
			width: 60%;
			height: 90%;
			background-color: #ddd;
			border-radius: 30px;
			font-size: 30px;
			background-color: #C3A69A;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			font-weight: 900;
			color: #61493f;
			border: 3px solid #3d2c25;
			box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
			cursor: pointer;
		}
		.readyButton:hover{
			background-color: #61493f;
			color: #C3A69A;
			box-shadow: inset 4px 4px 10px rgba(0, 0, 0, .25), inset -6px -6px 10px rgba(255, 255, 255, .25);
		}
		.readyButtonActivate{
			background-color: #61493f;
			color: #C3A69A;
			box-shadow: inset 4px 4px 10px rgba(0, 0, 0, .25), inset -6px -6px 10px rgba(255, 255, 255, .25);
		}
		#exit{
			width: 30%;
			height: 90%;
			background-color: #666;
			border-radius: 30px;
			border: 3px solid #333;
			font-size: 20px;
			font-weight: bold;
			display: flex;
			justify-content: center;
			align-items: center;
			cursor: pointer;
		}
		#exit:hover{
			background-color: #333;
			color: #fff;
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
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="container">
		<!-- 오목판 영역 -->
		<div id="gamepage">
			<div class="bowl" style="justify-content: start;">
				<div class="bowlImage" style="background-color: #e8e8e8;">
					<div class="bowlImageInside" style="background-color: #bbbbbb;"></div>
				</div>
				<div class="userNickName">

				</div>
			</div>
			<div id="boardback">
				<div id="boardfront">
					<div id="stoneboard">
						<c:forEach var="i" begin="0" end="18">
							<div class="rowStoneBox">
								<c:forEach var="j" begin="0" end="18">
									<div class="stoneCell">
										<div id="p${i}-${j}" class="stone" data-h="${i}" data-v="${j}"></div>
									</div>
								</c:forEach>
							</div>
						</c:forEach>
					</div>
				</div>
				<div id="cover" class="dragNo">
					<div>
						<p>게임을 시작하려면 준비를 눌러주세요.</p>
						<p>양측 모두 준비를 누르면 바로 게임이 시작됩니다.</p>
					</div>
				</div>
			</div>
			<div class="bowl" style="justify-content: end;">
				<div class="userNickName">
					<%=session.getAttribute("username") %>
				</div>
				<div class="bowlImage" style="background-color: #252525">
					<div class="bowlImageInside" style="background-color: #101010"></div>
				</div>
			</div>
		</div>
		<!-- /오목판 영역 -->
		<!-- 채팅 창 영역 -->
		<div id="chatpage">
			<div id="readyExitBox">
				<div id="" class="readyButton dragNo">게임 준비</div>
				<div id="exit" class="dragNo">나가기</div>
			</div>
			<textarea id="messagesTextArea" readonly="readonly" rows="30"></textarea><br>
			<div id="inputbox">
				<input type="text" id="messageText" size="50">
				<input type="button" value="Send" class="dragNo" onclick="sendMessage();">
			</div>
		</div>
		<!-- /채팅 창 영역 -->
	</div>
	<script>
		// 소켓 연결 요청
		const websocket = new WebSocket("ws://localhost:8090/omoomo/chatroomServerEndpoint");
		let userReady = false;
		let gameStartConstant = false;
		
		websocket.onopen = function (message) {
			var message = {
				sign: "init"
			}
			websocket.send(JSON.stringify(message));
		}

		// 소켓 서버에서 오는 메세지 - 메시지
		websocket.onmessage = function processMessage(message) {
			var jsonData = JSON.parse(message.data);
			if(jsonData.sign == "init"){
				// 초기 설정(돌 색상, 준비 여부, 돌통 색)
				Array.from(document.getElementsByClassName("stone")).forEach(stone => {
					stone.style.backgroundColor = jsonData.c == -1 ? "black" : "white";
				});
				document.getElementsByClassName("bowlImage")[0].style.backgroundColor = jsonData.c == -1 ? "#e8e8e8" : "#252525";
				document.getElementsByClassName("bowlImage")[1].style.backgroundColor = jsonData.c == -1 ? "#252525" : "#e8e8e8";
				document.getElementsByClassName("bowlImageInside")[0].style.backgroundColor = jsonData.c == -1 ? "#bbbbbb" : "#101010";
				document.getElementsByClassName("bowlImageInside")[1].style.backgroundColor = jsonData.c == -1 ? "#101010" : "#bbbbbb";
			} else if(jsonData.sign == "match") {
				// 유저가 입장했을 때 서로 상대 유저에게 유저 닉네임 표시
				document.getElementsByClassName("userNickName")[0].innerText = jsonData.matchUserNickname;
			} else if(jsonData.sign == "ready"){
				// 준비 여부 체크
				userReady = jsonData.value;
				let readyButton = document.getElementsByClassName("readyButton")[0];
				readyButton.innerText = userReady ? "준비 완료" : "게임 준비";
				if(userReady){
					readyButton.classList.add("readyButtonActivate");
				} else {
					readyButton.classList.remove("readyButtonActivate");
				}
			} else if(jsonData.sign == "start"){
				// 게임 시작
				if(jsonData.value){
					gameStartConstant = jsonData.value;
					document.getElementById("cover").style.display = "none";
					document.getElementsByClassName("readyButton")[0].innerText = "게임 중";
				}
			} else if(jsonData.sign == "chat"){
				// 채팅
				if(jsonData.message != null) messagesTextArea.value += jsonData.username + " : " + jsonData.message + "\n";
			} else if(jsonData.sign == "game"){
				// 놓은 돌을 상대 판에도 표시
				console.log("game");
				console.log("v: ", typeof jsonData.v, jsonData.v);
				console.log("h: ", typeof jsonData.h, jsonData.h);
				document.getElementById("p" + jsonData.h + "-" + jsonData.v).style.backgroundColor = jsonData.c == -1 ? "black" : "white";
				document.getElementById("p" + jsonData.h + "-" + jsonData.v).style.opacity = 1;
			} else if(jsonData.sign == "gameEnd" || jsonData.sign == "run"){
				// 게임 끝
				$.ajax({
					type: "post",
					async: true,
					url: "http://localhost:8090/onoono/waitingRoom.jsp",
					dataType: "text", 
					data: {
						username: <%=session.getAttribute("username") %>,
						result: jsonData.win == <%=session.getAttribute("username") %> ? "win" : "lose"
					},
					success: function(data, textStatus){
						if(jsonData.sign == "run"){
							location.href = "./waitingRoom.jsp";
						}
					}
				});
			}
		}
		// 소켓 서버에서 오는 메시지 - 에러
		websocket.onerror = function(e){
			console.log(e);
		}
		
		// 채팅 보내는 함수
		function sendMessage() {
			if(messageText.value != ""){
				var message = {
					sign: "chat",
					m: messageText.value
				}
				websocket.send(JSON.stringify(message)); // JSON 객체를 String으로 변환하여 전송
				messageText.value = "";
			}
		}

		// 게임 준비
		document.getElementsByClassName("readyButton")[0].addEventListener("click", (e) => {
			if(!gameStartConstant){
				var message = {
					sign: "ready",
					value: !userReady
				}
				websocket.send(JSON.stringify(message));
			}
		});

		// 방 나가기
		document.getElementById("exit").addEventListener("click", e => {
			if(confirm("정말 나가시겠습니까?" + (gameStartConstant ? " 지금 나가시면 패배 처리됩니다." : ""))){
				if(gameStartConstant){
					var message = {
						sign: "run",
						username: document.getElementsByClassName("userNickName")[1].innerHTML
					}
					websocket.send(JSON.stringify(message));
				} else {
					location.href = "./waitingRoom.jsp";
				}
			}
		});

		Array.from(document.getElementsByClassName("stone")).forEach((stone) => {
			// 돌을 눌렀을 때
			stone.addEventListener('click', (e) => {
				// 돌이 놓여있지 않은 위치에만 놓을 수 있음
				if(e.target.style.opacity != 1){
					e.target.style.opacity = 1;
					var message = {
						sign: "stone",
						h: e.target.dataset.h,
						v: e.target.dataset.v
					}
					websocket.send(JSON.stringify(message)); // JSON 객체를 String으로 변환하여 전송
				}
			});
			// 돌에 마우스를 올렸을 때 반투명하게 표시
			stone.addEventListener('mouseenter', (e) => {
				if(e.target.style.opacity != 1){
					e.target.style.opacity = 0.5;
				}
			});
			// 돌에서 마우스가 벗어나면 원래상태(숨김)로 되돌림
			stone.addEventListener('mouseleave', (e) => {
				if(e.target.style.opacity != 1){
					e.target.style.opacity = 0;
				}
			});
		});
	</script>
</body>
</html>