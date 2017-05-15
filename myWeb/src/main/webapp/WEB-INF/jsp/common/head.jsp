<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/frame/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/css/index.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/frame/bootstrap/js/bootstrap.js"></script>F
	<div class="head">
		<div class="head_main">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/blog/images/user.jpg"> <a id="gohome"
					href="${pageContext.request.contextPath}/index">CJY博客</a>
			</div>
			<div class="head_search">
				<form action="searchAllArticle.action" method="post" target="_blank">
					<div class="input-group">
						<input id="searchKey" name="queryCondition" type="text" class="form-control" placeholder="请输入搜索内容">
						<span class="input-group-btn">
							<button id="globalSearch" class="btn btn-default" type="submit">搜索</button>
						</span>
					</div>
				</form>
			</div>
			<div class="option">
				<ul class="nav nav-pills">
						<c:if test="${empty sessionScope.userId}">
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/loginView">登录</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/registerView">注册</a></li>
						</c:if>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/index/${sessionScope.userId}/1">我的博客</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/personalInfo.action?userId=${sessionScope.user.userId}">个人中心</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/blogManage.action">博客管理</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/writeArticle"
							target="_blank">写博客</a></li>
						<li role="presentation"><a href="${pageContext.request.contextPath}/logout">退出</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div id="main" class="main">