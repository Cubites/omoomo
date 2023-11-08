<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style type="text/css">

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
    position: relative;
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
    height: 400px;
    background-color: #C3A69A;
    border: 3px solid #573A2E;
    box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);
    border-radius: 10px;
    
   
}
/*장원작업 */
#search{
    border-radius: 10px;
    box-shadow: inset 8px 8px 10px rgba(255, 255, 255, .25), inset -8px -8px 10px rgba(0, 0, 0, .25);

    border: 3px solid #573A2E;
    background-color: #C3A69A;
    width: 90%;
    height: 15%;
   
   
}
#search > .fonts{
    top: 10px;
    position: relative;
    font-weight: bold;
    color: white;
    font-size: 20px;
    text-align: center;
    text-shadow: -1px 0 #573A2E, 0 2px #573A2E, 1px 0 #573A2E, 0 -1px #573A2E;
}
#searchInputbox{
    border-radius: 10px;
    background-color: transparent;
    margin-left: 10px;
    
    width: 90%;
    height: 20%;
    margin-top: 40px;
    left: 20px;
    border-radius: 5px;
    
}
#searchInputbox > input{
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
#searchInputbox > button{
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
<!--  <link href="./images/waitRoom.css" rel="stylesheet" type="text/css" /> -->
<title>Insert title here</title>
<!-- 장원 작업 -->
    <style >
    	.rankfont{
    		display: inline-block;
    		
    		margin-top: 10px;
    		margin-left: 60px;
    		font-size: 25px;
    		font-weight: bold;
    	}
    </style>
    <script>
    
    	function ajaxLoginUser(loginUser){
    		alert(loginUser);
    		$.ajax({
    			type : 'post',
    			url : "<c:url value='/getMemberList.do'/>",
    			dataType:'JSON',
    			data : {'name': loginUser},
    			success : function(result){
    				console.log(result);
    				$("#namebox").text(result[0].userName);
    				$("#win").text(result[0].win);
    				$("#lose").text(result[0].lose);
    				$("#rankNum").text(result[0].rank);
    				
    			
    			}
    		});
    	}
        function ajaxList(name) {
        	
		$.ajax({
			type : 'post',
			url : "<c:url value='/getMemberList.do'/>",
			dataType:'JSON',
			data : {'name': name},
			success : function(result){
				
				$("#rakingshow").html("");
				var member = result;
				var html ="";
				var idx;
				if(member.length >8){
					idx =8;
				}else{
					idx=member.length
				}
				for(let i=0; i<idx; i++){
					html += "<tr class='rankfont'><td>"+member[i].userName+"</td>"
					html += "<td>"+"  "+member[i].rank+"등</td></tr>"
				}
			//	member.forEach(function(index){
					
				//	html += "<tr class='rankfont'><td>"+index["userName"]+"</td>"
				//	html += "<td>"+index.rank+"</td></tr>"
					
			//	})
				$("#rakingshow").html(html);
				console.log(html);
			}
			
		});
	}
     
    
        
    $(function(){
    	
    	ajaxLoginUser("${user_name}");
    	ajaxList("");
        $("#searchInputbox > input").keyup(function(){
        const name = $(this).val();
        ajaxList(name);
    })

    $("#searchInputbox > button").click(function(){
        const name = $("#searchInputbox > input").val();
        ajaxList(name);
    })

    })
   
    </script>
 <!-- 장원 작업 -->
</head>
<body>
    <div id="container">
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