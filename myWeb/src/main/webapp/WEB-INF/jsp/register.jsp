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
					action="${pageContext.request.contextPath}/register.action" method="post">
					<h2 class="form-signin-heading">注册</h2>
					<div>
						<input class="form-control" type="text" name="username" placeholder="用户名" required autofocus>
					</div>
					<div>
						<input class="form-control" type="password" name="password" placeholder="密码" required autofocus>
					</div>
					<div>
						<input class="form-control" type="password" name="confirmPassword" placeholder="确认密码" required autofocus>
					</div>
					<div>
						<input class="form-control" type="text" name="checkCode" placeholder="验证码" required autofocus>
					</div>
					<img src="${pageContext.request.contextPath}/DrawImage.action" id="validateCodeImage" onclick="changeImg()">
					<button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>
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