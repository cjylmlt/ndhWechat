<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		$('.pageList').click(function(){
				window.location.href='${pageContext.request.contextPath}/'+'index/'+'${authorId}/'+$(this).attr('pageNum');
				
		});
		
	});
</script>
<c:if test="${empty page.records}">
	<div class="panel-body"><font color="red" size="20px;">还没有文章!</font></div>
</c:if>
<c:if test="${!empty page.records}">
<div>
	<ul class="list-group new-group">
		<c:forEach items="${page.records}" var="article">

			<li class="list-group-item new-item">
			  	<div class="panel panel-default">
				  	<div class="panel-body" style = "padding:12px">
				  		<div class = "user">
				    		<%-- <div class="userimg"><a href="#"><img userId="${article.authorId}" class="userinfo" src="${article[7]}"></a></div>--%>
							<%-- <div class="userName"><a href="myBlogView.action?user.userId=${article.authorId}">${article[8]}</a></div>--%>
				  		</div>
				  		<div class = "blog_content">
				  			<div class = "blog_title"><a href="${pageContext.request.contextPath}/articlePage/${article.id}">${article.title}</a></div>
				  			<div class = "article_content">${article.summary}</div>
				  			<div class = "article_info">
				  			<span>
				  			<fmt:formatDate value="${article.date }" pattern="yyyy-MM-dd"/>
				  				<%--<c:date name="#article[3]" format="yyyy-MM-dd HH:mm" timezone="GMT+00:00"/>--%>
			  				</span>
				  			&nbsp;&nbsp;&nbsp;<a href="articleDetail.action?article.id=${article.id}">阅读(${article.readNum})</a>
				  			&nbsp;&nbsp;&nbsp;<a href="articleDetail.action?article.id=${article.id}#articleComments">评论(${article.commentNum})</a></div>
				  		</div>
				  	</div>
				</div>
			  </li>
		</c:forEach>
	</ul>
</div>
</c:if>
<%@ include file="page.jsp" %>
