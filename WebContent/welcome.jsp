<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<script src="js/jquery/jquery-1.12.0.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>剪刀石头布</title>
</head>

<body>
<div id="opponent" style="width: 30%;height: 30%;margin:auto;text-align:center;">
</div>
<p style="width: 10%;height: 20%;margin:auto;text-align:center;">VS</p>
<div id="me" style="width: 30%;height: 30%;margin:auto;text-align:center;display: none">
		<input type="button" value="剪刀" style="margin: 10px ;background: #ddd;border-width: 0px;width: 50px;height: 50px;cursor:pointer;" onclick="start('1')" onmousemove="this.style.background='#e2e2e2'" onmouseout="this.style.background='#ddd'">
		<input type="button" value="石头" style="margin: 10px ;background: #ddd;border-width: 0px;width: 50px;height: 50px;cursor:pointer;" onclick="start('2')" onmousemove="this.style.background='#e2e2e2'" onmouseout="this.style.background='#ddd'">
		<input type="button" value="布" style="margin: 10px ;background: #ddd;border-width: 0px;width: 50px;height: 50px;cursor:pointer;" onclick="start('3')" onmousemove="this.style.background='#e2e2e2'" onmouseout="this.style.background='#ddd'">
</div>
<div id="enterButton">
		<input type="button" value="进入桌子" style="margin: 10px ;background: #ddd;border-width: 0px;width: 50px;height: 50px;cursor:pointer;" onclick="enterTable()" onmousemove="this.style.background='#e2e2e2'" onmouseout="this.style.background='#ddd'">
</div>		
<div id="result" style="width: 30%;height: 30%;margin:auto;text-align:center;">
</div>
</body>
<script type="text/javascript">
		var victory=0;
		var Failure=0;
		var draw=0;
		var vOrF;
		var ws;
		var opponentNum;
	/****测试几率****/
	/*(function (){
		for(var i=0;i<10000;i++){
			start(parseInt(3*Math.random()+1));
			if(vOrF=="成功"){victory+=1};
			if(vOrF=="失败"){Failure+=1};
			if(vOrF=="平局"){draw+=1};
			result.innerHTML="成功："+victory+"失败："+Failure+"平局："+draw;
		}
	}());*/
	/****结果算法****/
	function start(num){
		ws.sendMessage(num);
		if(opponentNum=="1"||opponentNum=="2"||opponentNum=="3"){
		var opponent = document.getElementById("opponent");
		var result = document.getElementById("result");
		if(opponentNum=="1"){
		opponent.innerHTML = "剪刀";
		}
		if(opponentNum=="2"){
		opponent.innerHTML = "石头";
		}
		if(opponentNum=="3"){
		opponent.innerHTML = "布";
		}
		if(opponentNum!==num){
		if(num=="1"&&opponentNum=="3"){opponentNum=0};
		if(num=="3"&&opponentNum=="1"){num=0};
			vOrF =(opponentNum<num)?"成功":"失败";
			result.innerHTML=vOrF;
		}else{
			vOrF="平局";
			result.innerHTML=vOrF;
		}
		}
		ws.close();

	}
	function enterTable(){
		ws = new WebSocket("ws://localhost:8080/sun/websocket1");
		ws.onmessage = function(evt){ 
		//接收到0为对方进入桌子
		if(evt.data=="0"){
			$("#result").html("");	
		}
		//接收到123为对方出招
		if(evt.data=="1"||evt.data=="2"||evt.data=="3"){
			opponent.innerHTML="对方出招，该你了";
		}
			opponentNum=evt.data;
		};
		ws.sendMessage = (function(num) {
            var message = num;
            if (message != '') {
                ws.send(message);
            }
        });
		ws.onclose = function () {
			
		};
		$.ajax({
			url: "enterTable",
				data: {},
				dataType:"json",
				type: "POST",
				success: function(data, textStatus){
					$("#me").show();
					$("#enterButton").hide();
					$("#result").html(data.message=="0"?"等待对方进桌":"");
				}
			});
	}
	
	
</script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</html>