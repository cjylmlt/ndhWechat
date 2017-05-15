<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		$('.pageList').click(function(){
			var nn = $('#isArticleClass').val();
			if($('#isArticleClass').val() == "1"){//如果是1则是对全部文章分页
				$('#contentShow').load('getArticlesList.action?pageNumber=' + $(this).attr('pageNum'));
			}else if($('#isArticleClass').val() == "0"){//如果是0则是对分类文章分页
				$('#contentShow').load('articleClass.action?pageNumber=' + $(this).attr('pageNum'));
			}
			
		});
		
		$('.userinfo').click(function(){
			window.location.href="myBlogView.action?userId=" + $(this).attr('userId');
		});
	});
</script>
<c:if test="${empty page.records}">
	<div class="panel-body"><font color="red" size="20px;">还没有文章!</font></div>
</c:if>
<c:if test="${!empty page.records}">
<div>
	<ul class="list-group new-group">
		<c:forEach items="#page.records" var="article">
			<c:set value="#article" var="article"></c:set>
			<li class="list-group-item new-item">
			  	<div class="panel panel-default">
				  	<div class="panel-body" style = "padding:12px">
				  		<div class = "user">
				    		<div class="userimg"><a href="#"><img userId="${article[6]}" class="userinfo" src="${article[7]}"></a></div>
							<div class="userName"><a href="myBlogView.action?user.userId=${article[6]}">${article[8]}</a></div>
				  		</div>
				  		<div class = "blog_content">
				  			<div class = "blog_title"><a href="articleDetail.action?article.articleId=${article[0]}">${article[1]}</a></div>
				  			<div class = "article_content">${article[2]}</div>
				  			<div class = "article_info">
				  			<span>
				  				<%--<c:date name="#article[3]" format="yyyy-MM-dd HH:mm" timezone="GMT+00:00"/>--%>
			  				</span>
				  			&nbsp;&nbsp;&nbsp;<a href="articleDetail.action?article.articleId=${article[0]}">阅读(${article[4]})</a>
				  			&nbsp;&nbsp;&nbsp;<a href="articleDetail.action?article.articleId=${article[0]}#articleComments">评论(${article[5]})</a></div>
				  		</div>
				  	</div>
				</div>
			  </li>
		</c:forEach>
	</ul>
</div>
<%-- <%@ include file="page.jsp" --%>  --%>
</c:if>
