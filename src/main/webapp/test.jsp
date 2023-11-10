<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<style>
#test {
	width: 300px;
	height: 300px;
	background-color: #999;
	cursor: pointer;
}

#one, #two {
	width: 50%;
	height: 100%;
}

#one {
	background-color: red;
}

#two {
	background-color: blue;
}
</style>
<script>
$(function(){
	$("#test").on("click", function() {
		console.log("클릭함");
		var mode = $(this).find("#two").data("value");
		console.log(mode);
	});
	
});
</script>
</head>
<body>
    <div id="test">
        <div id="one"></div>
        <div id="two" data-value="classic">테스트</div>
        
    </div>
</body>
</html>