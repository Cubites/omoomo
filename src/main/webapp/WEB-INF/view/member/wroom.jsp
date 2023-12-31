<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="room.*" %>
<%@ page import="java.util.List" %>
<%@ page import="user.UserVO" %>
<%
   RoomMap map = RoomMap.getRoomMap();
   request.setAttribute("map", map);
%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>

  <script src="https://code.jquery.com/jquery-latest.min.js"></script>

  <script type="text/javascript">
    $(function () {
      /*----------------장원 시작(1)--------------- */
      
      ajaxLoginUser("${login_user_name}");
      ajaxList("");
      $("#searchInputbox > input").keyup(function () {
        const name = $(this).val();
        if (name == "") {
          ajaxList(name);
        }
      })

      $("#searchInputbox > button").click(function () {
        const name = $("#searchInputbox > input").val();
        ajaxList(name);
      })

      /*-----------------장원 끝(1)---------------*/


      /*----------------강태연 시작(1)---------------*/
      //방 만들기 버튼 클릭시 모달 팝업 띄우기
      const modal = document.getElementById("modal") //modal이라는 id의 element를 가져옴->가장 상위 요소
      const btnModal = document.getElementById("roomMakeButton")//방만들기 버튼
      btnModal.addEventListener("click", e => {//방만들기 버튼 누르면 실행될 이벤트
        var roomCnt = $('.roomWrap').length;//방의 가장 상위 요소(.roomWrap)의 개수를 세서 현재 생성되어 있는 방의 개수를 가져옴
        if (roomCnt >= 8) {//방의 개수가 8개 이상이면 방 생성 금지
          alert("더 이상 방을 생성할 수 없습니다.")
        } else {
          modal.style.display = "flex"//8개 미만이면 방만들기 창 눌렀을 때, 모달창 display: none -> flex
        }
      })


      //모달 팝업에서 취소 클릭시 모달 팝업 없애기
      const closeBtn = modal.querySelector("#closeBtn") //방만들기(모달팝업) 내부 취소 버튼
      closeBtn.addEventListener("click", e => {//모달창 내에서 취소 버튼 누르면 실행될 이벤트
        modal.style.display = "none"//모달창 display: flex -> none으로 안보이게
      })

      //모달 팝업에서 확인 클릭시 방 정보 전송 -> roomVO로 방들의 정보를 업데이트하려고 보냄 
      $("#sendRoomInfo").on("click", function () {//방만들기(모달팝업) 내부 확인 버튼 누르면 실행될 이벤트
        var roomname = $("#input-roomname").val();//input으로 받은 방이름 
        var mode = $('input[name="mode"]:checked').val();//선택된 라디오 버튼의 값
        $.ajax({
          url: 'updateRoom.do', 
          type: "post",
          data: {
            "roomname": roomname,
            "mode": mode
          },
          success: function (data) {
            console.log(roomname)
            console.log(mode)
            alert("방 정보 전송 완");
            location.reload(true);
            modal.style.display = "none" //모달창 안보이게
          },
          error: function (request, status, error) {
            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
          }
        });
      });

      $(".roomWrap").on("click", function () {//방 요소 중 하나가 클릭되면 실행될 이벤트
        var userCntTxt = $(this).find("#roomUserNumber").text().replace(" / 2", ""); //방에 참가하고 있는 인원수를 방div에서 추출
        var userCnt = parseInt(userCntTxt, 10);//10진수값으로 파싱
        console.log(userCnt);
        
        if(userCnt==2){//화면에 표시된 참가자의 수가 2이면 입장하지 못하게 막음
            alert("입장할 수 없습니다.")
        }else{//참가자가 2명보다 적다면 방에 입장가능 
          var num = $(this).find("#num").val(); //방의 정보를 추출
          var mode = $(this).find("#rules").data("value"); //div에서 특정 값을 가져오기 위해 dic에 data-value로 설정해놓음
          //var mode = $("#rules", this).data("value");
          
          //세션에 저장된 현재 로그인 된 유저의 이름을 가져옴
          var username="${login_user_name}";
          alert(username);
          console.log(mode + num);
          //roomInfo로 Get방식으로 방의 번호, 입장하는 사용자의 이름을 전송
          location.href = "/omoomo/roomInfo?num=" + num + "&mode=" + mode+"&username="+username;
            
            
        }

        // location.href = "/omoomo/roomInfo?num=" + num;
        // $.ajax({
        //   type: "POST",
        //   url: "roomInfo",
        //   data: {
        //     "num": num
        //   },
        //   success: function (res) {
        //     console.log(res);
        //   },
        //   error: function (error) {
        //     console.log(error);
        //   }
        // });
      });
      /*-----------------강태연 끝(1)---------------*/


    }); /*$(function(){}) 끝*/

    /*----------------장원 시작(2)---------------*/
    function ajaxLoginUser(loginUser) {
	
     //post방식으로 ajax요청 로그인한 멤버 값 DB에서 가져옴
      $.ajax({
        type: 'post',
        url: "<c:url value='/getMemberList.do'/>",
        //json으로 return받음
        dataType: 'JSON',
        data: { 'name': loginUser },
        success: function (result) {
        	//return받은 json (result)에는 1개의 json객체가 들어 있음(배열) -> 배열접근을 통해 값 꺼내기
          console.log(result);
          $("#namebox").text(result[0].userName);
          $("#win").text(result[0].win);
          $("#lose").text(result[0].lose);
          $("#rankNum").text(result[0].rank);


        }
      });
    }
    //랭킹 검색한 json불러오기
    function ajaxList(name) {

      $.ajax({
        type: 'post',
        url: "<c:url value='/getMemberList.do'/>",
        dataType: 'JSON',
        data: { 'name': name },
        success: function (result) {
		
        
          $("#rakingshow").html("");
          
          //결과 알아보기 쉽게 member에 담음 
         
          var member = result;
          var html = "";
          var idx;
          if (member.length > 7) {
            idx = 7;
          } else {
            idx = member.length
          }
          //for문을 돌면서 이름과 rank를 출력 
          for (let i = 0; i < idx; i++) {
            html += "<tr class='rankfont'><td>" + member[i].userName + "</td>"
            html += "<td>" + member[i].rank + "등</td></tr>"
          }

          $("#rakingshow").html(html);
          console.log(html);
        }

      });
    }
    /*-----------------장원 끝(2)---------------*/

  </script>

  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    .textCenter {
      display: flex;
      justify-content: center;
      align-items: center;
    }

    /* 전체 페이지 */
    #container {
      display: flex;
      flex-direction: row;
      width: 100%;
      min-width: 1800px;
      height: 100vh;
      min-height: 900px;
      background-image: url('./images/waitingroomimage.jpg');
      background-repeat: no-repeat;
      background-size: cover;
      background-position: 0 -500px;
    }

    /** 유저 정보 및 랭킹 **/
    #userbox {
      width: 20%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
    }

    #userWhiteBox {
      width: 90%;
      height: 95%;
      background-color: rgba(255, 255, 255, .7);
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      border-radius: 30px;
    }

    /*** 유저 박스 ***/
    #userInfo {
      width: 90%;
      height: 30%;
      background-color: #C3A69A;
      border: 3px solid #573A2E;
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
      border-radius: 10px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      padding: 20px;
    }

    #namebox {
      width: 100%;
      height: 30%;
      border: 3px solid #573A2E;
      border-radius: 10px;
      background-color: #f0f0f0;
      box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -8px -8px 10px rgba(255, 255, 255, .25);
      font-size: 36px;
      font-weight: bold;
    }

    #record {
      width: 100%;
      height: 30%;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      font-size: 36px;
      font-weight: bold;
    }

    .score {
      width: 45%;
      height: 100%;
      border: 3px solid #573A2E;
      border-radius: 10px;
      background-color: #f0f0f0;
      box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -8px -8px 10px rgba(255, 255, 255, .25);
    }

    #rank {
      display: flex;
      flex-direction: row;
      justify-content: center;
    }

    #rankNum {
      margin-left: 20px;
      font-size: 50px;
      color: #fff;
      font-weight: 900;
      -webkit-text-stroke: 2px #573A2E;
    }

    /*** 랭킹 박스 ***/
    #ranking {
      width: 90%;
      height: 60%;
      background-color: #C3A69A;
      border: 3px solid #573A2E;
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
      border-radius: 10px;
    }


    /*장원작업 */
    #search {
      border-radius: 10px;
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);

      border: 3px solid #573A2E;
      background-color: #C3A69A;
      width: 90%;
      height: 15%;


    }

    .rankfont {
      display: inline-block;

      margin-top: 20px;
      margin-left: 70px;
      font-size: 25px;
      font-weight: bold;
    }

    #search>.fonts {
      top: 10px;
      position: relative;
      font-weight: bold;
      color: white;
      font-size: 20px;
      text-align: center;
      text-shadow: -1px 0 #573A2E, 0 2px #573A2E, 1px 0 #573A2E, 0 -1px #573A2E;
    }

    #searchInputbox {
      border-radius: 10px;
      background-color: transparent;
      margin-left: 10px;

      width: 90%;
      height: 20%;
      margin-top: 40px;
      left: 20px;
      border-radius: 5px;

    }

    #searchInputbox>input {
      display: inline-block;

      margin-left: 20px;
      height: 100%;
      width: 60%;
      box-sizing: content-box;
      border: 2px solid #79655C;
      background: #f0f0f0;
      text-align: center;
      border-radius: 5px;
      box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -8px -8px 10px rgba(255, 255, 255, .25);
    }

    #searchInputbox>button {
      margin-left: 20px;
      background-color: #f0f0f0;
      border-radius: 5px;
      box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -8px -8px 10px rgba(255, 255, 255, .25);
      width: 20%;
      height: 100%;
      font-weight: bold;
      color: black;
      font-size: 15px;

      border: 2px solid #79655C;
    }

    /*장원작업 */
    /** 방 정보 **/
    /** 방 정보 **/
    #roombox {
      width: 80%;
      height: 100vh;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }

    #roomboxWrap {
      width: 90%;
      height: 95%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      align-items: center;
    }

    /*** 방 목록 ***/
    #roomlist {
      width: 100%;
      height: 80%;
      background-color: rgba(255, 255, 255, .7);
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
      border-radius: 10px;
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      padding: 20px;
    }

    /**** 방 디자인 ****/
    #room {
      margin: 0px 0px 50px 50px;
      width: 45%;
      height: 20%;
      border: 3px solid #573A2E;
      border-radius: 20px;
      background-color: #916D5E;
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
    }

    .roomWrap {
      width: 95%;
      height: 90%;
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    }

    #roomInfo {
      width: 420px;
      height: 100%;
      border-radius: 10px;
      border: 3px solid #573A2E;
    }

    #roomname {
      width: 100%;
      height: 65%;
      background-color: #EACABD;
      padding: 15px;
      display: flex;
      flex-direction: row;
      justify-content: start;
      align-items: center;
      border-top-left-radius: 10px;
      border-top-right-radius: 10px;
    }

    #roomInfoBottom {
      width: 100%;
      height: 35%;
      background-color: #573A2E;
      color: #fff;
      display: flex;
      padding: 0 10px;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
    }

    #roomUserNumber {
      margin: 10px 0;
      width: 15%;
      text-align: center;
      background-color: #EACABD;
      color: #000;
      font-weight: bold;
      border-radius: 10px;
    }

    /*** 버튼 박스 ***/
    #roombuttons {
      width: 100%;
      height: 20%;
      display: flex;
      flex-direction: row;
      justify-content: end;
      align-items: end;
    }

    #roomMakeButton {
      width: 240px;
      height: 80px;
      background-color: #79655C;
      border: 3px solid #573A2E;
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
      border-radius: 20px;
      font-size: 36px;
      font-weight: 900;
      color: #fff;
      -webkit-text-stroke: 2px #573A2E;
      cursor: pointer;
    }

    #roomMakeButton:hover {
      background-color: #5a483f;
    }

    #roomEnterButton {
      width: 380px;
      height: 120px;
      background-color: #C3A69A;
      border: 3px solid #573A2E;
      box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
      border-radius: 20px;
      margin-left: 20px;
      font-size: 48px;
      font-weight: 900;
      color: #fff;
      -webkit-text-stroke: 2px #573A2E;
      cursor: pointer;
    }

    #roomEnterButton:hover {
      background-color: #a08478;
    }

    /* <!--flex -> none --> */
    #modal.modal-overlay {
      width: 100%;
      height: 100%;
      position: absolute;
      left: 0;
      top: 0;
      display: none;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      background: rgba(255, 255, 255, 0.25);
      box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
      backdrop-filter: blur(1.5px);
      -webkit-backdrop-filter: blur(1.5px);
      border-radius: 10px;
      border: 1px solid rgba(255, 255, 255, 0.18);
    }

    /* <!--안쪽거 세로 가운데 정렬하려 부모 display 설정 -> none--> */
    #modal .modal-window {
      background: #C8ADA2;
      box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
      box-shadow: -15px -15px 20px 0px rgba(0, 0, 0, 0.25) inset, 15px 15px 20px 0px rgba(255, 255, 255, 0.25) inset;
      backdrop-filter: blur(13.5px);
      -webkit-backdrop-filter: blur(13.5px);
      border-radius: 10px;
      border: 1px solid rgba(255, 255, 255, 0.18);
      width: 400px;
      height: 500px;
      position: relative;
      top: -100px;
      padding: 10px;
      display: flex;
    }

    /* <!--none 추가--> */
    #modal .modal-window-cover {
      justify-content: center;
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
      /* display:none; */
      border-radius: 10px;
      /* 	border: 1px solid rgba( 255, 255, 255, 0.18 ); */
      /* border: 3px solid #cac6c6; */
      margin: auto;
      background: #fff1f1;
      width: 350px;
      height: 450px;
      position: relative;
    }

    #modal .title {
      padding-left: 10px;
      display: inline;
      text-shadow: 1px 1px 2px gray;
      color: #454545;

    }

    #modal .title h2 {
      color: black;
      display: inline;
    }

    #modal .close-area {
      display: inline;
      float: right;
      padding-right: 10px;
      cursor: pointer;
      text-shadow: 1px 1px 2px gray;
      color: white;
    }

    #modal .content {
      margin-top: 20px;
      padding: 0px 10px;
      text-shadow: 1px 1px 2px gray;
      color: #454545;
    }

    .radio_box {
      display: inline-block;
      *display: inline;
      *zoom: 1;
      position: relative;
      padding-left: 25px;
      margin-right: 10px;
      cursor: pointer;
      font-size: 14px;
      -webkit-user-select: none;
      -moz-user-select: none;
      -ms-user-select: none;
      user-select: none;
    }

    /* 기본 라디오 버튼 숨기기 */
    .radio_box input[type="radio"] {
      display: none;
    }

    /* 선택되지 않은 라디오 버튼 스타일 꾸미기 */
    .on {
      width: 20px;
      height: 20px;
      background: #ddd;
      border-radius: 50%;
      position: absolute;
      top: 0;
      left: 0;
    }

    /* 선택된 라디오 버튼 스타일 꾸미기 */
    .radio_box input[type="radio"]:checked+.on {
      background: #f23a5d;
    }

    .on:after {
      content: "";
      position: absolute;
      display: none;
    }

    .radio_box input[type="radio"]:checked+.on:after {
      display: block;
    }

    .on:after {
      width: 10px;
      height: 10px;
      background: #fff;
      border-radius: 50%;
      position: absolute;
      left: 5px;
      top: 5px;
    }

    input {
      width: 300px;
      height: 30px;
      font-size: 20px;
    }

    .button-area {
      height: 100px;
      line-height: 100px;
    }

    .popupBtn {
      background-color: #eb9487;
      color: white;
      border: none;
      border-radius: 10px;
      padding: 10px 30px;
    }
  </style>

  </script>
</head>

<body>
  <div id="container">
    <!--모달팝업 시작-->
    <div id="modal" class="modal-overlay">
      <div class="modal-window">
        <div class="modal-window-cover">
          <div class="title" align="center">
            <h1>방 만들기</h1><br>
          </div>
          <div class="content" align="center">
            <div class="nameinput">
              <h3>방이름</h3> <br>
              <input type="text" id="input-roomname"><br>
            </div>
            <br>
            <br>
            <div class="modeDiv">
              <h3>모드 선택</h3> <br>
              <label for="mode1" class="radio_box">
                기본 오목
                <input type="radio" id="mode1" name="mode" value="classic" checked="checked" />
                <span class="on"></span>
              </label>
              <label for="mode2" class="radio_box">
                33 금지
                <input type="radio" id="mode2" name="mode" value="33" />
                <span class="on"></span>
              </label>
              <!-- 
              <label for="mode3" class="radio_box">
                44 금지
                <input type="radio" id="mode3" name="mode" value="44" />
                <span class="on"></span>
              </label>
               -->
            </div>
            <br>
          </div>
          <div class="button-area" align="center">
            <button class="popupBtn" id="sendRoomInfo">확인</button>
            <button class="popupBtn" id="closeBtn">취소</button>
          </div>
        </div>
      </div>
    </div>
    <!--모달팝업 끝-->

    <!-- side -->
    <!--장원-->
    <!-- s -->
    <div id="userbox">
      <div id="userWhiteBox">
        <div id="userInfo">
          <div id="namebox" class="textCenter"></div>
          <div id="record">
            <div id="win" class="score textCenter"></div>
            <div id="lose" class="score textCenter"></div>
          </div>
          <div id="rank">
            <img src="./images/rank.png">
            <div id="rankNum"></div>
          </div>
        </div>
        <div id="search">
          <div class="fonts">
            랭킹 찾기
          </div>
          <div id="searchInputbox">
            <input type="text" name="user_name" class="inputBox">
            <button style="display: inline-block;" class="inputB">버튼</button>
          </div>


        </div>
        <div id="ranking">
          <table id="rakingshow">

          </table>
        </div>
      </div>
    </div>

    <!--장원-->
    <!-- side -->


    <!-- room -->
    <div id="roombox">
      <div id="roomboxWrap">
        <div id="roomlist">
          <!-- 방 디자인(반복) -->
          <c:forEach var="key" items="${map.keySet()}">
            <div id="room">
              <div class="roomWrap">
                <input type="hidden" id="num" value="${roomMap.get(key).num}">
                <img src="./images/notstart.png">
                <div id="roomInfo">
                  <div id="roomname">${key}</div>
                  <div id="roomInfoBottom">
                    <!-- 모드 값에 따라 모드 출력 -->
                    <c:if test="${map.get(key).mode =='classic'}">
                       <div id="rules" data-value="classic">기본 오목</div>
                    </c:if>
                    <c:if test="${map.get(key).mode =='33'}">
                       <div id="rules" data-value="33">33 금지</div>
                    </c:if>
                    <!-- 모드 값에 따라 모드 출력 -->

                    <!-- 인원수 출력  뒤는 고정 앞은 현재 방(세션)접속 인원 받아와서 업데이트-->
                    <div id="roomUserNumber">${map.get(key).userlist.size()} / 2</div>
                    <!-- 인원수 출력 -->
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>
          <!-- <c:forEach var="key" items="${applicationScope.keySet}">
            <div id="room">
              <div class="roomWrap">
                <input type="hidden" id="num" value="${roomMap.get(key).num}">
                <img src="./images/notstart.png">
                <div id="roomInfo">
                  <div id="roomname">${roomMap.get(key).name}</div>
                  <div id="roomInfoBottom">
                    모드 값에 따라 모드 출력
                    <c:if test="${ roomMap.get(key).mode =='classic'}">
                       <div id="rules" data-value="classic">기본 오목</div>
                    </c:if>
                    <c:if test="${ roomMap.get(key).mode =='33'}">
                       <div id="rules" data-value="33">33 금지</div>
                    </c:if>
                    모드 값에 따라 모드 출력

                    인원수 출력  뒤는 고정 앞은 현재 방(세션)접속 인원 받아와서 업데이트
                    <div id="roomUserNumber">${ roomMap.get(key).peopleNum} / 2</div>
                    인원수 출력
                  </div>
                </div>
              </div>
            </div>
          </c:forEach> -->
          <!-- /방 디자인(반복) -->
        </div>
        <div id="roombuttons">
          <div id="roomMakeButton" class="textCenter">방 만들기</div>
          <!--  <div id="roomEnterButton" class="textCenter">빠른 시작</div> -->
        </div>
      </div>
    </div>
  </div>
</body>

</html>