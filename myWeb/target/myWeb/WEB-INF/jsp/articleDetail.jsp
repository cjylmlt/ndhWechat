<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/js/myBlog.js"></script>
<script src="${pageContext.request.contextPath}/blog/js/marked.js"></script>
<script src="${pageContext.request.contextPath}/blog/js/myBlog.js"></script>
<script src="${pageContext.request.contextPath}/blog/js/comments.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/highlight.min.js"></script>
<c:if test="${!empty article}">
	<script type="text/javascript">
		$(function() {
			articleDetailEvent.init();
		});
	</script>
	<div id="blog_article">
		<ul class="list-group new-group">
			<li class="list-group-item new-item">
				<div class="panel panel-default">
					<div class="panel-body" style="padding: 12px">
						<div class="myblogs">
							<div class="myblog_title">
								<c:if test="${article.type} == 0">
									<span class="label label-success" style="font-size: 13px;">原</span>&nbsp;&nbsp;
		  				</c:if>
								<c:if test="${article.type} == 1">
									<span class="label label-warning" style="font-size: 13px;">转</span>&nbsp;&nbsp;
		  				</c:if>
								<c:if test="${article.type} == 2">
									<span class="label label-danger" style="font-size: 13px;">译</span>&nbsp;&nbsp;
		  				</c:if>
								<a href="javascript:;">${article.title}</a> <a
									href="javascript:;"
									style="float: right; font-size: 14px; margin-right: 5px;"
									id="goBack">返回</a>
							</div>
							<div>
								<div style="margin-bottom: 15px;">标签:${article.tag}</div>

								<div class="select_other">
									<div class="classification">文章所在分类:</div>
									<%-- <c:forEach items="${article.articleClasses}" var="myArticleClass">
			  					<div class="dropdown">
								    <button type="button" class="bbtn dropdown-toggle" >
								      ${myArticleClass.personalClassification.classificationName}
								      <span class="caret"></span>          
								    </button>
								    <ul class="dropdown-menu">
								    	<c:forEach items="${myArticleClass.personalClassification.articleClasses}" var="myClassArticle">
								    		<li>
								    			<a href="javascript:;" class="otherArticle" id="${myClassArticle.article.articleId}">
								    				${myClassArticle.article.articleTitle}
								    			</a>
								    		</li>
								    	</c:forEach>
								    </ul>
								</div> 
							</c:forEach>  --%>
								</div>
								<!-- 		  				<div class = "article_info"> -->
								<%-- 		  					<span><c:set value="${article.date.toString().substring(0,19) }"/></span> --%>
								<%-- 		  					&nbsp;&nbsp;&nbsp;<span>阅读(${article.readnum})</span> --%>
								<%-- 		  					&nbsp;&nbsp;&nbsp;<a href="${article.comments}">评论(<c:set value="${ article.comments.size()}"/>)</a>&nbsp;&nbsp;&nbsp;  --%>

								<%-- 		  					<c:if test="${!empty sessionScope.user && sessionScope.user.userId eq articleDetail.userByAuthorId.userId}"> --%>
								<%-- 								<a href="writeArticle.action?article.articleId=${article.id}" target="_blank">编辑</a>&nbsp;&nbsp;&nbsp;  --%>
								<%-- 								<a onclick="myBlogEvent.deleteArticle('${article.id}',1)">删除</a> --%>
								<%-- 							</c:if> --%>
								<%-- 							<c:set value="'no'" var="hasCollection"></c:set> --%>
								<%-- 							<c:forEach items="${article.collection}" var="collection"> --%>
								<%-- 								<c:if test="${collection.user.userId eq sessionScope.user.userId}"> --%>
								<%-- 									<a onclick="articleDetailEvent.cancelCollection('${collection.collectionId}')">取消收藏</a> --%>
								<%-- 									<c:set value="'yes'" var="hasCollection"></c:set> --%>
								<%-- 								</c:if> --%>
								<%-- 							</c:forEach> --%>
								<%-- 							<c:if test="${hasCollection eq 'no' && !(sessionScope.user.userId eq article.userByAuthorId.userId)}"> --%>
								<%-- 								<c:if test="${empty sessionScope.user}"> --%>
								<!-- 									<a href="loginView.action" target="_blank">收藏</a> -->
								<%-- 								</c:if> --%>
								<%-- 								<c:if test="${!empty sessionScope.user}"> --%>
								<%-- 									<a onclick="articleDetailEvent.collectArticle('${article.id}')">收藏</a> --%>
								<%-- 								</c:if> --%>
								<%-- 							</c:if> --%>
								<!-- 		  				</div> -->
							</div>
						</div>
					</div>
				</div>
			</li>
			<li class="list-group-item new-item">
				<div class="panel panel-default">
					<textarea hidden id="markedContent">${article.content}</textarea>
					<div class="panel-body articleDetailShow" id="contentDetail">
					</div>
				</div>
			</li>
			<!-- 			<li class="list-group-item new-item"> -->
			<!-- 				<div class="panel panel-default"> -->
			<!-- 					set集合无法获取当前文章的上下篇，所以转换成数组 -->
			<%-- 					<c:set var="articleArray" --%>
			<%-- 						value="#articleDetail.userByAuthorId.articlesForAuthorId.toArray()"></c:set> --%>
			<%-- 					<c:forEach items="${articleArray}" var="a" varStatus="i"> --%>
			<%-- 						<c:if test="${a.articleId eq articleDetail.articleId}"> --%>
			<!-- 							<div class="panel-body" style="padding: 12px"> -->
			<!-- 								上一篇:&nbsp;&nbsp;&nbsp; -->
			<%-- 								<c:if test="${empty articleArray[i.index-1]}"> --%>
			<!-- 									<span>没有上一篇了!</span> -->
			<%-- 								</c:if> --%>
			<%-- 								<c:if test="${!empty articleArray[i.index-1]}"> --%>
			<!-- 									<a href="javascript:;" class="otherArticle" -->
			<%-- 										id="${articleArray[i.index-1].articleId}"> --%>
			<%-- 										${articleArray[i.index-1].articleTitle} </a> --%>
			<%-- 								</c:if> --%>
			<!-- 							</div> -->
			<!-- 							<div class="panel-body" style="padding: 12px"> -->
			<!-- 								下一篇:&nbsp;&nbsp;&nbsp; -->
			<%-- 								<c:if test="${empty articleArray[i.index+1]}"> --%>
			<!-- 									<span>没有下一篇了!</span> -->
			<%-- 								</c:if> --%>
			<%-- 								<c:if test="${!empty articleArray[i.index+1]}"> --%>
			<!-- 									<a href="javascript:;" class="otherArticle" -->
			<%-- 										id="${articleArray[i.index+1].articleId}"> --%>
			<%-- 										${articleArray[i.index+1].articleTitle} </a> --%>
			<%-- 								</c:if> --%>
			<!-- 							</div> -->
			<%-- 						</c:if> --%>
			<%-- 					</c:forEach> --%>
			<!-- 				</div> -->
			<!-- 			</li> -->
			<li class="list-group-item new-item">
				<ul class="list-group" id="thisArticleComments">

					<%-- <%@ include file="articleComments.jsp" %>--%>

				</ul>
			</li>
			<!-- 			<li class="list-group-item new-item"> -->
			<!-- 				<ul class="list-group"> -->
			<!-- 					<li class="list-group-item first">发表评论</li> -->
			<!-- 					<li class="list-group-item" id="commentIntanceInstance"><input -->
			<!-- 						id="thisArticleId" name="commentArticle" hidden -->
			<%-- 						value="${articleDetail.articleId}" /> <textarea --%>
			<!-- 							id="commentContent" class="form-control" style="height: 100px"></textarea> -->
			<!-- 						<button type="button" class="btn btn-default" -->
			<!-- 							onclick="commentEvent.submitComment();" style="margin-top: 10px">提交</button> -->
			<!-- 					</li> -->
			<!-- 				</ul> -->
			<!-- 			</li> -->
		</ul>
		<!-- 		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" -->
		<!-- 			aria-labelledby="myModalLabel"> -->
		<!-- 			<div class="modal-dialog"> -->
		<!-- 				<div class="modal-content"> -->
		<!-- 					<div class="modal-header"> -->
		<!-- 						<button data-dismiss="modal" class="close" type="button"> -->
		<!-- 							<span aria-hidden="true">×</span><span class="sr-only">Close</span> -->
		<!-- 						</button> -->
		<!-- 						<h4 class="modal-title"> -->
		<!-- 							回复:<span id="replyUserName"></span> -->
		<!-- 						</h4> -->
		<!-- 					</div> -->
		<!-- 					<div class="modal-body"> -->
		<!-- 						<textarea id="replyContent" class="form-control" -->
		<!-- 							style="height: 100px"></textarea> -->
		<!-- 					</div> -->
		<!-- 					<div class="modal-footer"> -->
		<!-- 						<button data-dismiss="modal" class="btn btn-default" type="button">关闭</button> -->
		<!-- 						<button class="btn btn-primary" -->
		<!-- 							onclick="commentEvent.submitReply()" type="button">提交</button> -->
		<!-- 					</div> -->
		<!-- 				</div> -->
		<!-- 				/.modal-content -->
		<!-- 			</div> -->
		<!-- 			<!-- /.modal-dialog -->
		-->
		<!-- 		</div> -->
	</div>
</c:if>