<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        .textCenter{
            display: flex;
            justify-content: center;
            align-items: center;
        }
		/* 전체 페이지 */
		#container{
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
        #userbox{
            width: 20%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        #userWhiteBox{
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
        #userInfo{
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
        #namebox{
            width: 100%;
            height: 30%;
            border: 3px solid #573A2E;
            border-radius: 10px;
            background-color: #f0f0f0;
            box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -8px -8px 10px rgba(255, 255, 255, .25);
            font-size: 36px;
            font-weight: bold;
        }
        #record{
            width: 100%;
            height: 30%;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;
            font-size: 36px;
            font-weight: bold;
        }
        .score{
            width: 45%;
            height: 100%;
            border: 3px solid #573A2E;
            border-radius: 10px;
            background-color: #f0f0f0;
            box-shadow: inset 8px 8px 10px rgba(0, 0, 0, .25), inset -8px -8px 10px rgba(255, 255, 255, .25);
        }
        #rank{
            display: flex;
            flex-direction: row;
            justify-content: center;
        }
        #rankNum{
            margin-left: 20px;
            font-size: 50px;
            color: #fff;
            font-weight:900;
            -webkit-text-stroke: 2px #573A2E;
        }
        /*** 랭킹 박스 ***/
        #ranking{
            width: 90%;
            height: 60%;
            background-color: #C3A69A;
            border: 3px solid #573A2E;
            box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
            border-radius: 10px;
        }
        /** 방 정보 **/
        #roombox{
            width: 80%;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        #roomboxWrap{
            width: 90%;
            height: 95%;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: center;
        }
        /*** 방 목록 ***/
        #roomlist{
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
        #room{
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
        #roomWrap{
            width: 95%;
            height: 90%;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;
        }
        #roomInfo{
            width: 420px;
            height: 100%;
            border-radius: 10px;
            border: 3px solid #573A2E;
        }
        #roomname{
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
        #roomInfoBottom{
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
        #roomUserNumber{
            margin: 10px 0;
            width: 15%;
            text-align: center;
            background-color: #EACABD;
            color: #000;
            font-weight: bold;
            border-radius: 10px;
        }
        /*** 버튼 박스 ***/
        #roombuttons{
            width: 100%;
            height: 20%;
            display: flex;
            flex-direction: row;
            justify-content: end;
            align-items: end;
        }
        #roomMakeButton{
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
        #roomMakeButton:hover{
            background-color: #5a483f;
        }
        #roomEnterButton{
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
        #roomEnterButton:hover{
            background-color: #a08478;
        }
    </style>
</head>
<body>
    <div id="container">
        <!-- s -->
        <div id="userbox">
            <div id="userWhiteBox">
                <div id="userInfo">
                    <div id="namebox" class="textCenter">GUEST1105</div>
                    <div id="record">
                        <div id="win" class="score textCenter">43</div>
                        <div id="lose" class="score textCenter">12</div>
                    </div>
                    <div id="rank">
                        <img src="./images/rank.png">
                        <div id="rankNum">354등</div>
                    </div>
                </div>
                <div id="ranking">

                </div>
            </div>
        </div>
        <div id="roombox">
            <div id="roomboxWrap">
                <div id="roomlist">
                    <!-- 방 디자인(반복) -->
                    <div id="room">
                        <div id="roomWrap">
                            <img src="./images/notstart.png">
                            <div id="roomInfo">
                                <div id="roomname">초보만</div>
                                <div id="roomInfoBottom">
                                    <div id="rules">33금지 / 44금지</div>
                                    <div id="roomUserNumber">2 / 2</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /방 디자인(반복) -->
                </div>
                <div id="roombuttons">
                    <div id="roomMakeButton" class="textCenter">방 만들기</div>
                    <div id="roomEnterButton" class="textCenter">빠른 시작</div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>