/**
 * 评论交互页面
 * 2017-03-03
 * author:jyh
 */

var comment = {
		
}

var reply = {
		
}

var commentEvent = {
	init:function(){
		
		//限制标题字数
		$('#commentContent').bind("keyup keydown change",function(){
			if($('#commentContent').val().length >= 200){
				$('#commentContent').val($('#commentContent').val().substring(0,200));
			}
		});
		$('#replyContent').bind("keyup keydown change",function(){
			if($('#replyContent').val().length >= 200){
				$('#replyContent').val($('#replyContent').val().substring(0,200));
			}
		});
		
		//点击提交按钮提交评论
		$('#submitComment').click(function(){
			commentEvent.submitComment();
		});
		
		//点击弹窗的提交，提交回复
		$("#replySubmit").click(function() {
			commentEvent.submitReply();
		});

	},
	
	//点击回复
	reply:function(floor,userId,userName){
		reply['comment.userByReplyUser.userId'] = userId;	
		reply['comment.floor'] = parseInt(floor) - 1;	
		$('#replyUserName').html(userName);
		$("#myModal").modal('show');
	},
	
	// 提交评论
	submitComment:function(){
		var commentUser = $('#commentUser').val();	
		var commentContent = $('#commentContent').val();
		if(commentUser == ""){
			alert("请先登录!");
			return;
		}
		if(commentContent == ""){
			alert("请输入评论!");
			return;
		}
		var floor = parseInt($("#thisArticleComments li.list-group-item:last-child").attr('id'));
		if(isNaN(floor) ||  typeof(floor)=="undefined" || floor == null){
			floor = 0;
		}
		comment['comment.commentArticle.articleId'] = $('#thisArticleId').val();//获取文章id
		comment['comment.floor'] = floor;										//获取最后一层的层数
		comment['comment.commentContent'] = commentContent;						//获取评论内容
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/saveComment.action',
			dataType : 'json',
			data:comment,
			success : function(data) {
				if (data.state == "0") {
					$('#commentContent').val("");
					$('#thisArticleComments').empty();
					$('#thisArticleComments').load("getArticleComments.action?article.articleId=" + $('#thisArticleId').val());
				} else if (data.state == "2") {
					alert(data.message);
				} 
			}
		})
	},
	
	submitReply:function(){
		var commentUser = $('#commentUser').val();	
		var replyContent = $('#replyContent').val();
		if(commentUser == ""){
			alert("请先登录!");
			return;
		}
		if(replyContent == ""){
			alert("请输入回复内容!");
			return;
		}
		reply['comment.commentArticle.articleId'] = $('#thisArticleId').val();//获取文章id
		reply['comment.commentContent'] = replyContent;						//获取回复内容
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/saveComment.action',
			dataType : 'json',
			data:reply,
			success : function(data) {
				if (data.state == "0") {
					$('#replyContent').val("");
					$("#myModal").modal('hide');
					$('#thisArticleComments').empty();
					$('#thisArticleComments').load("getArticleComments.action?article.articleId=" + $('#thisArticleId').val());
				} else if (data.state == "2") {
					alert(data.message);
				} 
			}
		})
	},
	
	//删除评论
	deleteComment:function(cid){
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/deleteComment.action?comment.commentId='+cid,
			dataType : 'json',
			success : function(data) {
				if(data.state == "0"){
					$('#thisArticleComments').empty();
					$('#thisArticleComments').load("getArticleComments.action?article.articleId=" + $('#thisArticleId').val());
				}else
					alert(data.message);
			},
			error:function(){
				alert("删除失败!");
			}
		})
	}
}
