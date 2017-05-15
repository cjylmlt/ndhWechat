/**
 * 博客管理
 * jyh
 */

Vue.filter('time', function (value) {
	if(!value || value=="")
		return "";
    return new Date(parseInt(value)).format("yyyy-MM-dd hh:mm:ss");
});

/**
 * 时间格式化
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};

var myTab = new Vue({
	el:'#myTab',
	methods:{
		findMyArticles:function(){
			articleManage.findMyArticles(1);
		},
		findMyComments:function(){				
			commentManage.findMyComments();
		},
		findMyClass:function(){
			classManage.findMyClass();
		},
		findMyLetter:function(){
			letterManage.findMySendLetters();
		}
	}
});

var articleManage = new Vue({
	el:'#articleManage',
	data:{
		page:{},
		articles:{}
	},
	created:function(){
		this.findMyArticles(1);
	},
	methods:{
		/**
		 * 我的文章
		 */
		findMyArticles:function(n){
			$.ajax({
				type : 'get',
				url : '${pageContext.request.contextPath}/findMyArticles.action?pageNumber=' + n,
				dataType : 'json',
				success : function(data) {
					if(data.state == "0"){
						var d = eval("(" + data.message +")");
						articleManage.articles = d.records;
						delete d.records;
						articleManage.page = d;
					}else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("获取失败!");
				}
			})
		},
		gotoArticle:function(aid){
			return "articleDetail.action?article.articleId="+aid;
		},
		gotoWriteArticle:function(aid){
			return "writeArticle.action?article.articleId="+aid;
		},
		deleteArticle:function(aid){
			var num = this.page.currentPageNum;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/deleteArticle.action?article.articleId='+aid,
				dataType : 'json',
				success : function(data) {
					alert(data.message);
					if (data.state == "0") {
						articleManage.findMyArticles(num);
					} 
				}
			})
		},
		goPage:function(num){
			articleManage.findMyArticles(num);
		},
		getNumInterval:function(){
			var interval = [];
			for(var i = this.page.startPageNum; i <= this.page.endPageNum; i++){
				interval.push(i);
			}
			return interval;
		}
	}
});

var commentManage = new Vue({
	el:'#commentManage',
	data:{
		classes:{
			f:true,
			s:false
		},
		comments:{},
		type:0
	},
	methods:{
		/**
		 * 我发表的评论
		 */
		findMyComments:function(){
			commentManage.classes.f = true;
			commentManage.classes.s = false;
			commentManage.type = 0;	//标致着是我发表的评论
			$.ajax({
				type : 'get',
				url : '${pageContext.request.contextPath}/findMyComments.action',
				dataType : 'json',
				success : function(data) {
					if(data.state == "0"){
						commentManage.comments =  eval("(" + data.message +")");
					}else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("获取失败!");
				}
			})
		},
		/**
		 * 我文章的评论
		 */
		findMyArticleComments:function(){
			commentManage.classes.s = true;
			commentManage.classes.f = false;
			commentManage.type = 1;	//标致着是我文章的评论
			$.ajax({
				type : 'get',
				url : '${pageContext.request.contextPath}/findMyArticleComments.action',
				dataType : 'json',
				success : function(data) {
					if(data.state == "0"){
						commentManage.comments =  eval("(" + data.message +")");
					}else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("获取失败!");
				}
			})
		},
		gotoArticle:function(aid){
			return "articleDetail.action?article.articleId="+aid;
		},
		//点击用户进入用户博客
		gotoUser:function(uid){
			return "myBlogView.action?userId="+uid;
		},
		deleteComment:function(cid){
			var type = this.type;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/deleteComment.action?comment.commentId='+cid,
				dataType : 'json',
				success : function(data) {
					if(data.state == "0"){
						if(type == 0){
							commentManage.findMyComments();
						}else if(type == 1){
							commentManage.findMyArticleComments();
						}
					}else
						alert(data.message);
				},
				error:function(){
					alert("删除失败!");
				}
			})
		}
	}
});

var classManage = new Vue({
	el:'#classManage',
	data:{
		classes:{},
		thisClass:{
			'pClassification.classificationId':"",
			'pClassification.classificationName':""
		}
	},
	methods:{
		/**
		 * 我的类别
		 */
		findMyClass:function(){
			$.ajax({
				type : 'get',
				url : '${pageContext.request.contextPath}/findMyClass.action',
				dataType : 'json',
				success : function(data) {
					if(data.state == "0"){
						classManage.classes = eval("(" + data.message +")");
					}else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("获取失败!");
				}
			})
		},
		editMyClass:function(i){
			this.thisClass['pClassification.classificationId'] = this.classes[i][0];
			this.thisClass['pClassification.classificationName'] = this.classes[i][1];
			$('#editMyClass').modal("show");
		},
		deleteMyClass:function(cId){
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/deleteMyClass.action?pClassification.classificationId='+cId,
				dataType : 'json',
				success : function(data) {
					if(data.state == "0")
						classManage.findMyClass();
					else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("删除失败!");
				}
			})
		},
		submitMyClass:function(){
			var c = this.thisClass;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/saveMyClass.action',
				dataType : 'json',
				data:c,
				success : function(data) {
					if(data.state == "0"){
						classManage.findMyClass();
						$('#editMyClass').modal("hide");
					}else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("保存失败!");
				}
			})
		}
	}
});

var letterManage = new Vue({
	el:'#letterManage',
	data:{
		classes:{
			f:true,
			s:false
		},
		mySendLetters:{},
		myReceiveLetters:{},
		letter:""
	},
	methods:{
		findMySendLetters:function(){
			this.classes.f = true;
			this.classes.s = false;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/findMySendLetters.action',
				dataType : 'json',
				success : function(data) {
					if(data.state == "0")
						letterManage.mySendLetters = eval("(" + data.message +")");
					else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("获取失败!");
				}
			})
		},
		findMyReceiveLetters:function(){
			this.classes.s = true;
			this.classes.f = false;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/findMyReceiveLetters.action',
				dataType : 'json',
				success : function(data) {
					if(data.state == "0")
						letterManage.myReceiveLetters = eval("(" + data.message +")");
					else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("获取失败!");
				}
			})
		},
		deleteMySendLetter:function(lId){
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/deleteLetter.action?letter.letterId='+lId,
				dataType : 'json',
				success : function(data) {
					if(data.state == "0")
						letterManage.findMySendLetters();
					else if(data.state == "2")
						alert(data.message);
					else if(data.state == "3")
						window.location.href=data.message;
				},
				error:function(){
					alert("删除失败!");
				}
			})
		},
		gotoUser:function(uid){
			return "myBlogView.action?userId="+uid;
		},
		getH:function(id){
			return '#'+id;
		},
		submitLetter:function(uId){
			var letter = {};
			letter['letter.letterContent'] = this.letter;
			letter['user.userId'] = uId;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/sendLetter.action',
				dataType : 'json',
				data:letter,
				success : function(data) {
					if(data.state == "3")
						window.location.href=data.message;
					else if(data.state == "0"){
						letterManage.letter = "";
						alert(data.message);
					}
					else
						alert(data.message);
				},
				error:function(){
					alert("删除失败!");
				}
			})
		}
	}
});

var accountSetting = new Vue({
	el:'#accountSetting',
	data:{
		user:{
			'user.userName':"",
			'user.password':""
		}
	},
	methods:{
		submitAccount:function(){
			
			$('#accountForm').bootstrapValidator('validate');
			
			if($("#accountForm").data('bootstrapValidator').isValid()){
				var user = this.user;
				$.ajax({
					type : 'post',
					url : '${pageContext.request.contextPath}/updateAccount.action',
					dataType : 'json',
					data:user,
					success : function(data) {
						if(data.state == "3")
							window.location.href=data.message;
						else if(data.state == "0"){
							accountSetting.user['user.userName'] = "";
							accountSetting.user['user.password'] = "";
							alert(data.message);
						}
						else
							alert(data.message);
					},
					error:function(){
						alert("修改失败!");
					}
				})
			}
		}
	}
});

$('#accountForm').bootstrapValidator({
	message : '表单不能为空',
	feedbackIcons : {
		valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
	},
	fields : {
		"username" : {
			message : '用户名不能为空',
			validators : {
				notEmpty : {
					message : '请输入用户名'
				},
				stringLength : {
					min : 4,
					max : 15,
					message : '用户名长度必须在5-15之间'
				},
				regexp : {
					regexp : /^[a-zA-Z0-9_\.]+$/,
					message : '用户名只能包含字母，数字，点和下划线'
				}
			}
		},
		"password" : {
			validators : {
				notEmpty : {
					message : '请输入密码'
				}
			}
		},
	}
});
