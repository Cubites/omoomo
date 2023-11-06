<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://code.jquery.com/jquery-latest.min.js"></script>

<script type="text/javascript">
	function goRoom(roomNum) {
		//location.href="<c:url value='/enterRoom.do'/>?num="+roomNum;
		location.href = "/omoomo/enterRoom.do?num=" + roomNum;
	}

	function openPopup() {
		window.open("createRoom.do", "방 만들기",
				"width=400, height=550, top=200, left=650");
	}
</script>

<style>
#sideBar {
	float: left;
	border: 1px solid black;
	height: 950px;
	width: 20%;
	box-sizing: border-box;
}

#roomList {
	float: right;
	border: 1px solid black;
	height: 800px;
	width: 78%;
	box-sizing: border-box;
}

#buttons {
	float: right;
}

#userInfo {
	border: 1px solid black;
	height: 150px;
}

#searchRank {
	border: 1px solid black;
	height: 125px;
}

.room {
	border: 1px solid black;
	height: 100px;
}
</style>

</script>
</head>
<body>
	<div id="container">

		<div id="sideBar">

			<div id="userInfo">
				<div>유저01</div>
				<br>
				<div id="winlose">
					<span>승 35</span> <span>패 60</span>
				</div>
			</div>

			<div id="searchRank">
				<input type="text" id="searchName"><br>
				<button>찾기</button>
				<div id="searchResult"></div>
			</div>

			<div id="rankingBoard">
				1위 망곰 <br> 2위 마루 <br> 3위 춘식 <br>
			</div>


		</div>

		<div id="container">
			<div id="roomList">


				<c:forEach var="room" items="${applicationScope.roomSet}">

					<div class="room">
						<div class="roomName">${room.name }</div>

						<c:if test="${room.mode =='classic'}">
							<div class="roomMode">기본 오목</div>
						</c:if>
						<c:if test="${room.mode =='33'}">
							<div class="roomMode">33 금지 오목</div>
						</c:if>
						<c:if test="${room.mode =='44'}">
							<div class="roomMode">44 금지 오목</div>
						</c:if>

					</div>

				</c:forEach>

			</div>
			<div id="buttons">
				<button id="createRoom" onclick="openPopup()">방 만들기</button>
				<button id="quickStart">빠른 시작</button>
			</div>
		</div>
</body>
</html>