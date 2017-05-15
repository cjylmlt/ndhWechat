<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆成功</title>
<link rel="stylesheet"
	href="resource/bootstrap-3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="resource/bootstrap-3.3.7/css/mystyle.css" />
<link rel="stylesheet"
	href="resource/bootstrap-3.3.7/css/style.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}//bootstrap-3.3.7/js/bootstrap.js"></script>
</head>
<body>
<div>欢迎 ${ loginUsername} <img  class="user_img userinfo" src="${userImage}"/></div>
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
			document.getElementById("dis").innerHTML="登陆成功,"+time+"秒后自动跳转到登录面";
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