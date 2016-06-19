<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery/jquery-1.12.0.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<script>
		$.ajax({
			url: "enterTable",
				data: {"dd":"5","ddd":"4"},
				dataType:"json",
				type: "POST",
				success: function(data, textStatus){
					
				}
		})
	
	</script>
</body>
</html>