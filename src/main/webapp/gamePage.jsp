<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="game_page.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="container">
		<!-- 오목판 영역 -->
		<div id="gamepage">
			<!-- 장식용 오목돌 통 -->
			<div class="bowl" style="justify-content: start;">
				<div class="bowlImage" style="background-color: #e8e8e8;">
					<div class="bowlImageInside" style="background-color: #bbbbbb;"></div>
				</div>
				<div class="userNickName">

				</div>
				<div class="resultBox">
					
				</div>
			</div>
			<!-- /장식용 오목돌 통 -->
			<!-- 오목판 -->
			<div id="boardback">
				<div id="boardfront">
					<div id="stoneboard">
						<!-- 돌이 놓일 위치에 돌을 미리 놓아 둠 -->
						<c:forEach var="i" begin="0" end="18">
							<div class="rowStoneBox">
								<c:forEach var="j" begin="0" end="18">
									<div class="stoneCell">
										<div id="p${i}-${j}" class="stone" data-h="${i}" data-v="${j}"></div>
									</div>
								</c:forEach>
							</div>
						</c:forEach>
						<!-- /돌이 놓일 위치에 돌을 미리 놓아 둠 -->
					</div>
				</div>
				<!-- 게임 시작 전, 오목판 가림막 -->
				<div id="cover" class="dragNo">
					<div>
						<p>게임을 시작하려면 준비를 눌러주세요.</p>
						<p>양측 모두 준비를 누르면 바로 게임이 시작됩니다.</p>
					</div>
				</div>
				<!-- 게임 종료 후, 승패 표시 -->
				<div id="resultBoard" class="dragNo">
					<div id="resultText">
						
					</div>
				</div>
			</div>
			<!-- /오목판 -->
			<!-- 장식용 오목돌 통 -->
			<div class="bowl" style="justify-content: end;">
				<input type="hidden" id="resultValue" value="">
				<div class="resultBox">

				</div>
				<div class="userNickName">
					<%=session.getAttribute("login_user_name") %>
				</div>
				<div class="bowlImage" style="background-color: #252525">
					<div class="bowlImageInside" style="background-color: #101010"></div>
				</div>
			</div>
			<!-- /장식용 오목돌 통 -->
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

		// 뒤로 가기로 이동할 페이지에 현재 페이지 url을 넣음
		history.pushState(null, null, location.href); 

		// 뒤로가기 이벤트감지 -> 현재페이지로 이동
		window.onpopstate = function() { 
			history.go(1); 
		}
		
		// websocket.onopen - 웹 소켓이 연결되었을 때 실행할 소스를 적는 곳
		websocket.onopen = function (message) {
			var message = {
				sign: "init"
			}
			websocket.send(JSON.stringify(message));
		}

		// websocket.ommessage - 소켓 서버에서 오는 메세지 - 메시지
		websocket.onmessage = function processMessage(message) {
			var jsonData = JSON.parse(message.data);
			if(jsonData.sign == "init"){
				/* 방에 입장한 경우 */
				// 오목판에 놓은 돌 색을 유저 돌 색으로 변경
				Array.from(document.getElementsByClassName("stone")).forEach(stone => {
					stone.style.backgroundColor = jsonData.c == -1 ? "black" : "white";
				});
				// 화면의 돌 통의 색을 유저 색에 맞게 변경
				document.getElementsByClassName("bowlImage")[0].style.backgroundColor = jsonData.c == -1 ? "#e8e8e8" : "#252525";
				document.getElementsByClassName("bowlImage")[1].style.backgroundColor = jsonData.c == -1 ? "#252525" : "#e8e8e8";
				document.getElementsByClassName("bowlImageInside")[0].style.backgroundColor = jsonData.c == -1 ? "#bbbbbb" : "#101010";
				document.getElementsByClassName("bowlImageInside")[1].style.backgroundColor = jsonData.c == -1 ? "#101010" : "#bbbbbb";
			} else if(jsonData.sign == "noSeat") {
				/* 방에 자리가 없는 경우 */
				// 들어온 유저를 다시방으로 보냄
				location.href = "wroom.do";
			} else if(jsonData.sign == "match") {
				/* 유저가 있는 방에 다른 유저가 입장했을 때 */
				// 상대 유저 화면에 유저 이름 표시
				document.getElementsByClassName("userNickName")[0].innerText = jsonData.matchUserNickname;
				// 클라이언트의 승패 결과 값을 초기화
				Array.from(document.getElementsByClassName("resultBox")).forEach(resultBox => {
					resultBox.style.display = "none";
				});
			} else if(jsonData.sign == "ready"){
				/* 준비 버튼을 누른 경우 */
				// 준비 값 저장
				userReady = jsonData.value;
				var readyButton = document.getElementsByClassName("readyButton")[0];
				// 준비 버튼을 준비 완료 상태로 변경
				readyButton.innerText = userReady ? "준비 완료" : "게임 준비";
				if(userReady){
					readyButton.classList.add("readyButtonActivate");
				} else {
					readyButton.classList.remove("readyButtonActivate");
				}
			} else if(jsonData.sign == "start"){
				/* 게임이 시작된 경우(양쪽 모두 준비를 누른 경우) */
				if(jsonData.value){
					// 게임 시작 여부를 시작(true)로 변경
					gameStartConstant = jsonData.value;
					// 오목판 가림막 해제
					document.getElementById("cover").style.display = "none";
					// 게임 준비 버튼을 게임 중으로 변경
					document.getElementsByClassName("readyButton")[0].innerText = "게임 중";
					var result = document.getElementById("resultValue").value;
					if(result){
						if(result == 1){
							Array.from(document.getElementsByClassName("stone")).forEach(stone => {
								stone.style.backgroundColor = "white";
							});
						} else {
							Array.from(document.getElementsByClassName("stone")).forEach(stone => {
								stone.style.backgroundColor = "black";
							});
						}
					}
					// 놓여있던 오목돌 제거(숨김)
					Array.from(document.getElementsByClassName("stone")).forEach(stone => {
						stone.style.opacity = 0;
					});
				}
			} else if(jsonData.sign == "chat"){
				/* 채팅을 입력한 경우 */
				// 양쪽 채팅창에 둘 다 표시
				if(jsonData.message != null) messagesTextArea.value += jsonData.username + " : " + jsonData.message + "\n";
			} else if(jsonData.sign == "game"){
				/* 돌을 놓은 경우 */
				// 양쪽 오목판에 둘 다 표시
				document.getElementById("p" + jsonData.h + "-" + jsonData.v).style.backgroundColor = jsonData.c == -1 ? "black" : "white";
				document.getElementById("p" + jsonData.h + "-" + jsonData.v).style.opacity = 1;
			} else if(jsonData.sign == "gameEnd" || jsonData.sign == "run"){
				/* 게임이 끝난 경우(한 쪽이 승리했거나, 한 명이 게임을 나간 경우) */
				// 게임 시작 여부를 false로 변경
				gameStartConstant = false;
				
				// 게임 준비 여부를 false로 변경
				userReady = false;
				
				// 게임 준비 버튼을 처음 상태로 되돌림
				var readyButton = document.getElementsByClassName("readyButton")[0];
				readyButton.innerText = "게임 준비";
				document.getElementById("cover").style.display = "flex";
				readyButton.classList.remove("readyButtonActivate");

				// 승패 결과를 보냄
				$.ajax({
					type: "post",
					async: true,
					url: "winAndLose",
					dataType: "text", 
					data: {
						username: '${login_user_name}',
						result: jsonData.win == '${login_user_name}' ? "win" : "lose"
					},
					success: function(data, textStatus){
						console.log('response: ' + data);
					}
				});
				
				// 게임 중에 나가기를 누른 경우, 대기방으로 이동(패배 처리)
				if(jsonData.sign == "run" && jsonData.lose == '${login_user_name}'){
					location.href = "wroom.do";
				}
				
				var resultBox = document.getElementsByClassName("resultBox");
				
				if('<%=session.getAttribute("login_user_name") %>' == jsonData.win){
					// 클라이언트가 승자인 경우, 클라이언트 쪽에 승 표시 및 흰색 돌 배정
					resultBox[0].innerText = "패";
					resultBox[0].style.backgroundColor = "#C3A69A";
					resultBox[0].style.display = "flex";
					resultBox[1].innerText = "승";
					resultBox[1].style.backgroundColor = "#61493f";
					resultBox[1].style.display = "flex";
					document.getElementsByClassName("bowlImage")[0].style.backgroundColor = "#252525";
					document.getElementsByClassName("bowlImage")[1].style.backgroundColor = "#e8e8e8";
					document.getElementsByClassName("bowlImageInside")[0].style.backgroundColor = "#101010";
					document.getElementsByClassName("bowlImageInside")[1].style.backgroundColor = "#bbbbbb";
					document.getElementById("resultValue").value = 1;
				} else {
					// 클라이언트가 패자인 경우, 클라이언트 족에 패 표시 및 검은색 돌 배정
					resultBox[0].innerText = "승";
					resultBox[0].style.backgroundColor = "#61493f";
					resultBox[0].style.display = "flex";
					resultBox[1].innerText = "패";
					resultBox[1].style.backgroundColor = "#C3A69A";
					resultBox[1].style.display = "flex";
					document.getElementsByClassName("bowlImage")[0].style.backgroundColor = "#e8e8e8";
					document.getElementsByClassName("bowlImage")[1].style.backgroundColor = "#252525";
					document.getElementsByClassName("bowlImageInside")[0].style.backgroundColor = "#bbbbbb";
					document.getElementsByClassName("bowlImageInside")[1].style.backgroundColor = "#101010";
					document.getElementById("resultValue").value = 0;
				}
				
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

		// 게임 준비를 누르면 소켓에 전송
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
					// 게임 중 나간 경우, 패배 처리
					var message = {
						sign: "run",
						username: document.getElementsByClassName("userNickName")[1].innerHTML
					}
					websocket.send(JSON.stringify(message));
				} else {
					// 게임 중이 아닐 때 나간 경우, 페이지만 이동
					location.href = "wroom.do";
				}
			}
		});

		Array.from(document.getElementsByClassName("stone")).forEach((stone) => {
			// 돌을 눌렀을 때
			stone.addEventListener('click', (e) => {
				// 돌이 놓여있지 않은 위치에만 놓을 수 있음
				if(e.target.style.opacity != 1){
					var message = {
						sign: "game",
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