<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>用户登录</title>
<link rel="stylesheet"
	href="resource/bootstrap-3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="resource/bootstrap-3.3.7/css/mystyle.css" />
<link rel="stylesheet"
	href="resource/bootstrap-3.3.7/css/style.css" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
<script type="text/javascript">
	function changeImg() {
		document.getElementById("validateCodeImage").src = "${pageContext.request.contextPath}/DrawImage?"
				+ Math.random();
	}
</script>
<body>
	<div class="container">
		<div class="row myCenter">
			<div class="col-xs-6 col-md-4 col-center-block">
				<form class="form-signin"
					action="${pageContext.request.contextPath}/login" method="post">
					<h2 class="form-signin-heading">请登录</h2>
					<div>
						<input type="text"  name="username" class="form-control" placeholder="用户名" required autofocus> 
					</div>
					<div>
						<input type="password"  name="password" class="form-control" placeholder="密码" required> 
					</div>
					<div>
						<input type="text"  name="checkCode" class="form-control" placeholder="验证码" required> 
					</div>
					
					<img src="${pageContext.request.contextPath}/DrawImage.action" id="validateCodeImage" onclick="changeImg()">
					<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
					<button class="btn btn-lg btn-primary btn-block" type="reset">重置</button>
				</form>
			</div>
		</div>
	</div>
</body>
	<div class="col-center-block">
    <jsp:include page="common/footer.jsp" flush="false" />
    </div>
</html>