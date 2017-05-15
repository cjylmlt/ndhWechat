<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>主页</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/css/home.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/css/myblog.css">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/css/index.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap-3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap-3.3.7/css/bootstrap.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7/css/mystyle.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-3.3.7/css/style.css" />

<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

</head>
<body>
	<%@ include file="common/head.jsp"%>
	<div class="mytitle">
		<h1>CJY博客</h1>
		<h3>DEBUG!!!!</h3>
	</div>
	<div class="main_content">
		<div class="main_left">
			<%@ include file="leftPage.jsp"%>

		</div>
		<div class="main_right" id="contentShow">
			<%@ include file="articleDetail.jsp"%>
		</div>

	</div>
	<%@ include file="common/tail.jsp"%>
</body>

</html>