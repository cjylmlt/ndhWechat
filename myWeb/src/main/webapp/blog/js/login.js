/*
 * 用户登录注册
 * author:jyh
 * date:2017-2-22
 */

var loginEvent = {
	init : function() {
		$(".container").css("margin-top",
				($(window).height() - $(".container").height()) / 2 - 80);

		$('input,button').click(function() {
			$('.message').remove();
		});
		
		$('#sendMail').click(function() {
			loginEvent.sendMail();
		});
		$("#userName").blur(function(){
			loginEvent.nameRepeat();
		});
		
	},
	
	//注册
	regist : function() {
		$('#registForm').bootstrapValidator({
			message : '表单不能为空',
			feedbackIcons : {
				valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
			},
			fields : {
				"user.userName" : {
					message : '用户名不能为空',
					validators : {
						notEmpty : {
							message : '请输入用户名'
						},
						stringLength : {
							min : 5,
							max : 15,
							message : '用户名长度必须在5-15之间'
						},
						regexp : {
							regexp : /^[a-zA-Z0-9_\.]+$/,
							message : '用户名只能包含字母，数字，点和下划线'
						}
					}
				},
				"user.email" : {
					validators : {
						notEmpty : {
							message : '请输入邮箱地址'
						},
						emailAddress : {
							message : '输入的email地址格式不正确'
						}
					}
				},
				"user.password" : {
					validators : {
						notEmpty : {
							message : '请输入密码'
						}
					}
				},
				"validateCode" : {
					validators : {
						notEmpty : {
							message : '请输入验证码'
						}
					}
				}
			}
		})
		 .on('success.form.bv', function(e) {
			 //防止表单提交
			 e.preventDefault();
			
			 var user = $("#registForm").serialize();
			$.ajax(
				{
					type:'post',
					url:'${pageContext.request.contextPath}/regist.action',
					dataType:'json',
					data:user,
					success:function(data){
						if(data.state == "0"){
							alert("注册成功!");
							location.reload();
						}else if(data.state == "2"){
							alert(data.message);
						}else{
							var message = $.parseJSON(data.message);		  // json字符串转换成对象
							$('#registForm input').each(function(){
								var m = message[this.name];
								if(m != undefined && m != null){
									loginEvent.showErrors(this,m);
									$("#registSubmit").attr("disabled","disabled");
								}
							});
						}
					},
					error:function(){
						alert("注册失败");
					}
				})
		 });
	},
	
	//登录
	login : function() {
		$('#loginForm').bootstrapValidator({
			message : '表单不能为空',
			feedbackIcons : {
				valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
			},
			fields : {
				"user.userName" : {
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
				"user.password" : {
					validators : {
						notEmpty : {
							message : '请输入密码'
						}
					}
				},
			}
		})
		 .on('success.form.bv', function(e) {
			 //防止表单提交
			 e.preventDefault();
			
			 var user = $("#loginForm").serialize();
			$.ajax(
				{
					type:'post',
					url:'${pageContext.request.contextPath}/login.action',
					dataType:'json',
					data:user,
					success:function(data){
						if(data.state == "0"){
							window.location.href='/MyBlog/index.action';
						}else if(data.state == "2"){
							alert(data.message);
						}else{
							var message = $.parseJSON(data.message);		  // json字符串转换成对象
							$('#registForm input').each(function(){
								var m = message[this.name];
								if(m != undefined && m != null){
									loginEvent.showErrors(this,m);
									$("#loginSubmit").attr("disabled","disabled");
								}
							});
						}
					},
					error:function(){
						alert("信息:登录失败!");
					}
				})
		 });
	},
	
	//发送邮件
	sendMail : function(){
		if($('#email').val() == ""){
			return;
		}
		var user = $("#registForm").serialize();
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/sendMail.action',
			dataType : 'json',
			data : user,
			async:false,
			success : function(data) {
				if(data.state == "0"){
					alert("验证码发送成功!");
					$('#sendMail').html("验证码已发送");
				}else if(data.state == "2"){
					alert(data.message);
				}else{
					var m = message['user.email'];
					if(m != undefined && m != null){
						loginEvent.showErrors($("#email"),m);
						$("#registSubmit").attr("disabled","disabled");
					}
				}
			},
			error : function() {
				alert("呵呵哈哈哈!");
			}
		});
	},
	
	//判断用户名是否已经被注册
	nameRepeat : function(){
		var user = "user.userName="+$('#userName').val();
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/nameRepeat.action',
			dataType : 'json',
			data : user,
			async:false,
			success:function(data){
				if(data.state == "1"){
					loginEvent.showErrors($("#userName"),"该用户名已被注册");
					$("#registSubmit").attr("disabled","disabled");
				}
			}
		});
	},
	
	//显示错误信息
	showErrors : function(p,message){
		$(p).parent().parent().attr("class","form-group has-feedback has-error");
		$(p).next().css("display","block");
		$(p).next().attr("class","form-control-feedback glyphicon glyphicon-remove");
		$(p).next().next().css("display","block");
		$(p).next().next().html(message);
		//$(":submit").attr("disabled","disabled");
	}
};

$(function() {
	loginEvent.init();
	loginEvent.regist();
	loginEvent.login();
});
