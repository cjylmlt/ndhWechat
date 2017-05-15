/**
 * 我的博客
 * 2017-03-04
 * jyh
 */

//浏览历史记录的栈
var historyLinksStack = {  
	
	historyLinks : new Array(),

    //压栈操作  
	push : function(element){  
		this.historyLinks.push(element);  
	},  
	
	//退栈操作  
	pop : function(){  
		return this.historyLinks.pop();  
	},  
	
	//获取栈顶元素  
	top : function(){  
		return this.historyLinks[this.historyLinks.length-1];  
	},  
	
	//获取栈长  
	size : function(){  
		return this.historyLinks.length;  
	},  
	
	//清空栈  
	clear : function(){  
		this.historyLinks = new Array();  
		return true;  
	},  
  
	toString : function(){  
		return this.historyLinks.toString();  
	} 
};  

var myBlogMarked = "0";


//我的博客
var myBlogEvent = {
	init:function(){
		
		//点击查询
		$('#searchArticle').click(function(){
			if($('#articleName').val() == ""){
				alert("请输入搜索内容");
			}
			else{
				myBlogMarked = "1";				//设置为1，直到点击其它按钮改变它的值之前，翻页一直都是对搜索出来的文章的翻页
				var link = "searchArticle.action?article.articleTitle=" + $('#articleName').val();
				historyLinksStack.push(link);	//将浏览过的链接加入栈中去
				$('#right').empty();
				$('#right').load(link);
			}
		});
		
		//点击分类
		$('.myArticleClass').click(function(){
			myBlogMarked = "2";		
			var link = "myClassArticle.action?myArticleClassId=" + $(this).attr('personalClass');
			historyLinksStack.push(link);	//将浏览过的链接加入栈中去
			$('#right').empty();
			$('#right').load(link);
		});
		
		//点击存档
		$('.dateClass').click(function(){
			myBlogMarked = "3";		
			var link = "myArticleGroupByDate.action?dateClass=" + $(this).attr('myDateGroup');
			historyLinksStack.push(link);
			$('#right').empty();
			$('#right').load(link);
		});
		
		//点击文章标题进入文章详情页面
		$('.articleDetail').click(function(){
			var link = "myArticleDetail.action?article.articleId=" + $(this).attr('articleId');
			historyLinksStack.push(link);
			$('#right').empty();
			$('#right').load(link);
		});
		
	},
	
	//删除文章 i为0表示在列表中删除的，i为1表示在文章详情中删除的，可以根据这个决定刷新什么
	//在列表中删除刷新当前列表，在文章详情中删除刷新上一次的请求
	deleteArticle:function(articleId,i){
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/deleteArticle.action?article.articleId='+articleId,
			dataType : 'json',
			data:reply,
			success : function(data) {
				if (data.state == "0") {
					if(historyLinksStack.size() <= 1){
						$('#right').load("getMyArticlesList.action?pageNumber=1");
						return;
					}
					if(i == 0)
						$('#right').load(historyLinksStack.top());
					else if(i == 1){
						historyLinksStack.pop();					
						$('#right').load(historyLinksStack.top());	
					}
				} else if(data.state == "3"){
					alert(data.message);
				} else if(data.state == "3"){
					window.location.href=data.message;
				}
			}
		})
	}
};

//文章列表
var myBlogListEvent = {
	init:function(){
		$('.pageList').click(function(){
			if(myBlogMarked == "0"){			//表示我的所有文章翻页列表
				var link = "getMyArticlesList.action?pageNumber=" + $(this).attr('pageNum');
				historyLinksStack.push(link);
				$('#right').empty();
				$('#right').load(link);
			}else if(myBlogMarked == "1"){		//表示查询出来的我的文章翻页列表
				var link = "searchArticle.action?pageNumber=" + $(this).attr('pageNum');
				historyLinksStack.push(link);
				$('#right').empty();
				$('#right').load(link);
			}else if(myBlogMarked == "2"){		//表示我的分类文章翻页列表
				var link = "myClassArticle.action?pageNumber=" + $(this).attr('pageNum');
				historyLinksStack.push(link);
				$('#right').empty();
				$('#right').load(link);
			}else if(myBlogMarked == "3"){		//表示我的存档文章翻页列表
				var link = "myArticleGroupByDate.action?pageNumber=" + $(this).attr('pageNum');
				historyLinksStack.push(link);
				$('#right').empty();
				$('#right').load(link);
			}
			
		});
	
		//点击文章标题进入文章详情页面
		$('.myArticleDetail').click(function(){
			var link = "myArticleDetail.action?article.articleId=" + $(this).attr('date-id');
			historyLinksStack.push(link);
			$('#right').empty();
			$('#right').load(link);
		});
		
	}
};

//文章详情页面
var articleDetailEvent = {
	init:function(){
		//设置代码高亮
		marked.setOptions({
			highlight : function(code) {
				return hljs.highlightAuto(code).value;
			}
		});
		
		//解析markdown语法
		$('#contentDetail').html(marked($('#markedContent').val()));
		
		//文章分类下拉
		$('.dropdown-toggle').click(function(){
			$(this).next(".dropdown-menu").toggle();
		});
		
		//点击分类下拉栏的其它文章和上一篇下一篇
		$('.otherArticle').click(function(){
			var link = "myArticleDetail.action?article.articleId=" + $(this).attr('id');
			historyLinksStack.push(link);
			$('#right').empty();
			$('#right').load(link);
		});

		//返回
		$('#goBack').click(function(){
			historyLinksStack.pop();					//先吐出最上面的一个，因为最上面的就是当前链接
			if(historyLinksStack.size() <= 1){			//如果栈里面没东西或者只有一个，则表示从主页直接点击查看文章或者从我的博客页面直接点击查看文章的
				$('#right').load("getMyArticlesList.action?pageNumber=1");
			}else{
				$('#right').load(historyLinksStack.top());	//然后在加载下一个，但是不吐掉
			}
		});
	},
	
	//收藏文章
	collectArticle:function(articleId){
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/collectArticle.action?article.articleId='+articleId,
			dataType : 'json',
			success : function(data) {
				if (data.state == "0") {
					if(historyLinksStack.size() == 0)
						window.location.reload(true); 
					else
						$('#right').load(historyLinksStack.top());
				} 
				alert(data.message);
			}
		})
	},
	
	//取消收藏
	cancelCollection:function(collectionId){
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/cancelCollection.action?collection.collectionId='+collectionId,
			dataType : 'json',
			data:reply,
			success : function(data) {
				if (data.state == "0") {
					if(historyLinksStack.size() == 0)
						window.location.reload(true); 
					else
						$('#right').load(historyLinksStack.top());
				} 
			}
		})
	}
}

$(function(){
	myBlogEvent.init();
});