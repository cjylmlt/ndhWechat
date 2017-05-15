<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册成功</title>
</head>
<body>
	<script type="text/javascript">
		function changePage(){
			//window.setTimeout("changeLocation()","3000");
			var time = 5;
			window.setTimeout(function(){changeTime(time);},"1000");
			
		}
		function changeLocation(){
			window.location.replace("${pageContext.request.contextPath}/welcome.jsp");
		}
		function changeTime(time){
			document.getElementById("dis").innerHTML="注册成功,"+time+"秒后自动跳转到首页";
			window.setTimeout(function(){changeTime(time);},"1000");
			time--;
			if(time<0){
				changeLocation();
			}
		}
	</script>

	<div id="dis"></div>
	<script>
		changePage();
	</script>
</body>
</html>