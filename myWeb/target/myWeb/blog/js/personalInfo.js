/**
 * 个人中心页面
 * 2017-03-06
 * jyh
 */


/**
 * 获取参数
 * @param name
 * @returns
 */
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return (r[2]);
	return null;
};

var thisUserId = GetQueryString("userId");
/**
 * vue时间过滤器
 */
Vue.filter('time', function (value) {
	if(!value || value=="")
		return "";
    return new Date(parseInt(value)).format("yyyy-MM-dd hh:mm:ss");
});

Vue.filter('date', function (value) {
	if(!value || value=="")
		return "";
    return new Date(parseInt(value)).format("yyyy-MM-dd");
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

/**
 * 数组和需要用','分割的字符串转换
 * @param v
 * @returns {String}
 */
function stringArray(v){
	var str = "";
	var arr = [];
	if(typeof(v) == "string"){
		var vv = v.split(",");
		for(var f in vv)
			arr.push(vv[f]);
		return arr;
	}else{
		for(var f in v){
			if(f > 0)
				str += ',';
			str += v[f];
		}
		return str;
	}
};

var personalEvent = {
	/**
	 * 初始获取页面数据
	 */
	getUserData:function(u){
		var userId = thisUserId;
		if(userId == "" || userId == null){
			window.location.href="/MyBlog/index.action";
			return;
		}
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/personalInfoView.action',
			data:'user.userId=' + userId,
			success : function(data) {
				if(data.state == "0"){
					var us = eval("(" + data.message +")");
					
					if(!us.birthday || us.birthday == "")
						us.birthday = new Date().format("yyyy-MM-dd").toString();
					else
						us.birthday = new Date(parseInt(us.birthday)).format("yyyy-MM-dd").toString();
					
					userInfo.attentionId = data.attentionId;
					
					us.professionalSkill = stringArray(us.professionalSkill);
					us.knowField = stringArray(us.knowField);

					otherInfo.educations = us.educations;
					otherInfo.works = us.works;
					delete us.educations;
					delete us.works;
					switch(u)
					{
					case null://表明刚刚加载本页面
						userInfo.user = otherInfo.user = us;
						break;
					case userInfo://表明编辑了userInfo之后刷新
						userInfo.user = us;
						break;
					case otherInfo://表示编辑了otherInfo之后刷新
						otherInfo.user = us;
						break;
					}
					//给userInfo中省份下拉框和市区下拉框填充数据
					var cc = [];
		    		for(var i = 0; i < proData.length; i ++){
		    			var p = proData[i];
		    			if(userInfo.user.myProvince == p.pv){
		    				for(var j = 0; j < cityData.length; j ++){
		    					var c = cityData[j];
		    					if(c.pk == p.pk){
		    						cc.push(c);
		    					}
		    				}
		    				userInfo.citys = cc;
		    			}
		    		}
		    		userInfo.pros = proData;
				}else
					window.location.href="error.jsp";
			},
			error:function(){
				window.location.href="error.jsp";
			}
		});
	},
	
	/**
	 * 获取我的动态数据
	 */
	getMyDynamic:function(t){
		$.ajax({
			type : 'get',
			url : '${pageContext.request.contextPath}/findMyDynamic.action',
			dataType : 'json',
			data:'dynamicType=' + t,
			success : function(data) {
				if(data.state == "0"){
					myDynamic.dynamic = eval("(" + data.message +")");
					//为了标识选中的标签按钮
					for(var i in myDynamic.classes){
						if(i == t)
							myDynamic.classes[i] = true;
						else
							myDynamic.classes[i] = false;
					}
				}else
					alert(data.message);
			},
			error:function(){
				alert("获取失败!");
			}
		})
	},
	
	/**
	 * 获取我关注的人/我关注的人/互相关注的人
	 */
	getMyAttention:function(t){
		$.ajax({
			type : 'get',
			url : '${pageContext.request.contextPath}/getMyAttention.action',
			dataType : 'json',
			data:'attentionType=' + t,
			success : function(data) {
				if(data.state == "0"){
					myRelationship.attentions = eval("(" + data.message +")");
					//为了标识选中的标签按钮
					for(var i in myRelationship.classes){
						if(i == t)
							myRelationship.classes[i] = true;
						else
							myRelationship.classes[i] = false;
					}
				}else
					alert(data.message);
			},
			error:function(){
				alert("获取失败!");
			}
		})
	},
	
	/**
	 * 获取我的收藏
	 */
	getMyCollection:function(num){
		$.ajax({
			type : 'get',
			url : '${pageContext.request.contextPath}/getMyCollection.action?pageNum=' + num,
			dataType : 'json',
			success : function(data) {
				if(data.state == "0"){
					var c = eval("(" + data.message +")");
					if(c.records != null)
						myCollection.collections = c.records;
					
					delete c.records;
					myCollection.page = c;
				}else
					alert(data.message);
			},
			error:function(){
				alert("获取失败!");
			}
		})
	},
	
	/**
	 * 删除收藏
	 * @param cId
	 */
	deleteCollection:function(cId){
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/deleteCollection.action?collection.collectionId='+cId,
			dataType : 'json',
			success : function(data) {
				if(data.state == "0"){
					personalEvent.getMyCollection(1);
				};
				alert(data.message);
			},
			error:function(){
				alert("删除失败!");
			}
		})
	},

	/**
	 * 提交用户信息
	 * @param u	vue对象
	 * @param e 弹窗对象
	 */
	submitUserInfo:function(u,e){
		var user = {};
		for(var i in u.user)
			user["user."+i] = u.user[i];
		
		user['user.professionalSkill'] = stringArray(u.user.professionalSkill);
		user['user.knowField'] = stringArray(u.user.knowField);
		user['user.birthday'] = $('#birthday').val();//不知道为什么vue无法同步时间选择器选中的数据
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/saveUser.action',
			dataType : 'json',
			data:user,
			success : function(data) {
				personalEvent.getUserData(u);
				if(data.state == "0"){
					if(e != null)
						$(e).modal('hide');
				}else
					alert(data.message);
			},
			error:function(){
				alert("保存失败!");
			}
		})
	},
	
	/**
	 * 验证用户
	 * @param userId	被查看的用户id
	 * @returns {Boolean}
	 */
	validateUser:function(userId){
		var uid = $('#thisUserId').val();
		if(userId == uid)
			return true;
		return false;
	},
};

/**
 * 填充页面---头部的基础信息
 */
var userInfo = new Vue({
    el:'#userInfo',
    data:{
    	attentionId:1,
    	user:{},
    	pros:{},
    	citys:{},
    	letter:""
	},
    methods:{
    	/**
    	 * 验证被访问用户是不是当前登录的用户
    	 */
    	validateUser:function(){
    		return personalEvent.validateUser(this.user.userId);
    	},
    	/**
    	 * 上传头像
    	 */
    	uploadImg:function(){
    		var h = (window.screen.height - 500)/2;
    		var w = (window.screen.width - 610)/2;
    		imgUpload.open({
    			title : '头像上传', 						//弹窗左上角标题 默认:头像上传 
    			offset : [h,w], 						// 坐标 默认:'auto'（中间） 自定义:['100px', '200px'] 
    			success : function(state, url, obj) { 	// 上传完成返回(state:状态码，url:头像地址，obj:头像文件对象)
    				if(state == "1"){
    					personalEvent.getUserData(userInfo);
    				}
    			}
    		});
    	},
    	/**
    	 * 关注
    	 */
    	attentionUser:function(){
    		if(!$('#thisUserId').val() || $('#thisUserId').val() == ""){
    			alert("请先登录!");
    			return;
    		}
    		$.ajax({
    			type : 'post',
    			url : '${pageContext.request.contextPath}/attentionUser.action',
    			dataType : 'json',
    			data:"user.userId="+this.user.userId,
    			success : function(data) {
    				if(data.state == "0"){
        				personalEvent.getUserData(userInfo);
    				}else
    					alert(data.message);
    			},
    			error:function(){
    				alert("关注失败!");
    			}
    		})
    	},
    	/**
    	 * 取消关注
    	 */
    	cancelAttention:function(){
    		var aid = this.attentionId;
    		$.ajax({
    			type : 'post',
    			url : '${pageContext.request.contextPath}/cancelAttention.action',
    			dataType : 'json',
    			data:"attention.attentionId="+aid,
    			success : function(data) {
    				if(data.state == "0"){
        				personalEvent.getUserData(userInfo);
    				}else
    					alert(data.message);
    			},
    			error:function(){
    				alert("取消失败!");
    			}
    		})
    	},
    	/**
    	 * 发送私信弹窗
    	 */
    	sendLetter:function(){
    		if(!$('#thisUserId').val() || $('#thisUserId').val() == ""){
    			alert("请先登录!");
    			return;
    		}
    		$('#sendLetter').modal("show");
    	},
    	/**
    	 * 提交私信
    	 */
    	submitLetter:function(){
    		
    		$.ajax({
    			type : 'post',
    			url : '${pageContext.request.contextPath}/sendLetter.action',
    			dataType : 'json',
    			data:"letter.letterContent=" + this.letter + "&user.userId=" + this.user.userId,
    			success : function(data) {
    				if(data.state == "0"){
        				personalEvent.getUserData(userInfo);
        				userInfo.letter = "";
        				$('#sendLetter').modal("hide");
    				}
    				alert(data.message);
    			},
    			error:function(){
    				alert("发送失败!");
    			}
    		})
    	},
    	/**
    	 * 查看私信
    	 */
    	viewLetter:function(){
    		alert("查看私信");
    	},
    	/**
    	 * 点击日期初始化日历框
    	 */
    	shwoTime:function(){
    		$('#datetimePicker').datetimepicker({
    			format : "yyyy-mm-dd",
    			autoclose:true,//日期选择完成后是否关闭选择框
    		    minView: "month",//表示日期选择的最小范围，默认是hour
    		});
    		$('#datetimePicker').datetimepicker("show");
    	},
    	/**
    	 * 编辑最上方的基本信息
    	 */
    	baseInfoEdit:function(){
    		$("#editUserInfo").modal('show');
    	},
    	birthdayChange:function(){
    		alert("nn");
    	},
    	/**
    	 * 省市级联选中省份，网上找的省市级联数据，想不通为什么数据这样弄，在整理城市数据的时候直接以省为key不就行了么
    	 */
    	selectProvince:function(p){
			var cc = [];
    		for(var i = 0; i < proData.length; i ++){
    			var p = proData[i];
    			if(userInfo.user.myProvince == p.pv){
    				for(var j = 0; j < cityData.length; j ++){
    					var c = cityData[j];
    					if(c.pk == p.pk){
    						cc.push(c);
    					}
    				}
    				userInfo.citys = cc;
    			}
    		}
    	},
    	/**
    	 * 头部的基础信息提交
    	 */
    	submitBaseInfo:function(){
    		var u = this.user;
    		personalEvent.submitUserInfo(this, $("#editUserInfo"));
    	}
    	
    }
    
  });

/**
 * 初始化填充页面---下面的基础信息
 */
var otherInfo = new Vue({
	el:'#myData',
	data:{
		field:"",
		professional:"",
		educations:{},
		works:{},
		user:{}
	},
	methods:{
		/**
    	 * 验证被访问用户是不是当前登录的用户
    	 */
    	validateUser:function(){
    		return personalEvent.validateUser(this.user.userId);
    	},
		/**
    	 * 编辑联系方式-弹窗
    	 */
    	contactEdit:function(){
    		$("#editContact").modal('show');
    	},
    	/**
    	 * 提交联系方式
    	 */
    	submitContact:function(){
    		personalEvent.submitUserInfo(this, $("#editContact"));
    	},
    	/**
    	 * 编辑熟悉的领域delField
    	 */
    	addField:function(){
    		$("#addField").modal('show');
    	},
    	delField:function(index){
    		this.user.knowField.splice(index,1);
    		personalEvent.submitUserInfo(this, null);
    	},
    	/**
    	 * 提交添加的熟悉领域
    	 */
    	submitField:function(){
    		this.user.knowField.push(this.field);
    		personalEvent.submitUserInfo(this, $("#addField"));
    	},
    	/**
    	 * 编辑专业技能
    	 */
    	addProfessional:function(){
    		$("#addProfessional").modal('show');
    	},
    	/**
    	 * 删除专业技能
    	 */
    	delProfessional:function(index){
    		this.user.professionalSkill.splice(index,1);
    		personalEvent.submitUserInfo(this, null);
    	},
    	/**
    	 * 提交专业技能
    	 */
    	submitProfessional:function(){
    		this.user.professionalSkill.push(this.professional);
    		personalEvent.submitUserInfo(this, $("#addProfessional"));
    	},
    	
    	/**
    	 * 增加或编辑教育背景
    	 */
    	addOreditEducation:function(i){
    		var ee = {};
    		if(i == -1){//如果是添加
    			ee['education.startDate'] = new Date().format("yyyy-MM-dd");
    			ee['education.endDate'] = new Date().format("yyyy-MM-dd");
    		}else{//如果是编辑
        		var education = this.educations[i];
        		for(var j in education)
        			ee["education."+j] = education[j];
        		ee['education.startDate'] = new Date(parseInt(ee['education.startDate'])).format("yyyy-MM-dd");
        		ee['education.endDate'] = new Date(parseInt(ee['education.endDate'])).format("yyyy-MM-dd");
    		}
    		//给弹窗对象赋值
    		educationInfo.e = ee;
    		//显示弹窗
    		$("#addOrEditEducation").modal('show');
    	},
    	/**
    	 * 删除教育背景
    	 */
    	delEducation:function(eId){
    		$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/deleteEducation.action',
				dataType : 'json',
				data:'education.educationId='+eId,
				success : function(data) {
					if(data.state == "0")
						personalEvent.getUserData(otherInfo);
					alert(data.message);	
				}
			})
    	},
    	/**
    	 * 添加工作经历
    	 */
    	addOrEditWork:function(i){
    		var ww = {};
    		if(i == -1){//如果是添加
    			ww['work.startDate'] = new Date().format("yyyy-MM-dd");
    			ww['work.endDate'] = new Date().format("yyyy-MM-dd");
    		}else{//如果是编辑
        		var work = this.works[i];
        		for(var j in work)
        			ww["work."+j] = work[j];
        		ww['work.startDate'] = new Date(parseInt(ww['work.startDate'])).format("yyyy-MM-dd");
        		ww['work.endDate'] = new Date(parseInt(ww['work.endDate'])).format("yyyy-MM-dd");
    		}
    		//给弹窗对象赋值
    		workInfo.w = ww;
    		//显示弹窗
    		$("#addOrEditWork").modal('show');
    	},
    	/**
    	 * 删除工作经历
    	 */
    	delWork:function(wId){
    		$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/deleteWork.action',
				dataType : 'json',
				data:'work.workId='+wId,
				success : function(data) {
					if(data.state == "0")
						personalEvent.getUserData(otherInfo);
					alert(data.message);	
				}
			})
    	}
	}
});

/**
 * 添加或者编辑教育信息的对象
 */
var educationInfo = new Vue({
	el:'#addOrEditEducation',
	data:{
		e:{}
	},
	methods:{
		submitEducation:function(){
			var e = this.e;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/saveEducation.action',
				dataType : 'json',
				data:e,
				success : function(data) {
					personalEvent.getUserData(otherInfo);
					if(data.state == "0")
						$('#addOrEditEducation').modal('hide');
					alert(data.message);	
				}
			})
		},
    	shwoStartTime:function(){
    		$('#educatoin_start').datetimepicker({
    			format : "yyyy-mm-dd",
    			autoclose:true,//日期选择完成后是否关闭选择框
    			maxView:"year",
    		    minView: "month",//表示日期选择的最小范围，默认是hour
    		});
    		$('#educatoin_start').datetimepicker("show");
    	},
    	shwoEndTime:function(){
    		$('#educatoin_end').datetimepicker({
    			format : "yyyy-mm-dd",
    			autoclose:true,//日期选择完成后是否关闭选择框
    			maxView:"year",
    		    minView: "month",//表示日期选择的最小范围，默认是hour
    		});
    		$('#educatoin_end').datetimepicker("show");
    	}
	}
});

/**
 * 添加或者编辑工作经历的对象
 */
var workInfo = new Vue({
	el:'#addOrEditWork',
	data:{
		w:{}
	},
	methods:{
		submitWork:function(){
			var w = this.w;
			$.ajax({
				type : 'post',
				url : '${pageContext.request.contextPath}/saveWork.action',
				dataType : 'json',
				data:w,
				success : function(data) {
					personalEvent.getUserData(otherInfo);
					if(data.state == "0")
						$('#addOrEditWork').modal('hide');
					alert(data.message);
				}
			})
		},
    	shwoStartTime:function(){
    		$('#work_start').datetimepicker({
    			format : "yyyy-mm-dd",
    			autoclose:true,//日期选择完成后是否关闭选择框
    			maxView:"year",
    		    minView: "month",//表示日期选择的最小范围，默认是hour
    		});
    		$('#work_start').datetimepicker("show");
    	},
    	shwoEndTime:function(){
    		$('#work_end').datetimepicker({
    			format : "yyyy-mm-dd",
    			autoclose:true,//日期选择完成后是否关闭选择框
    			maxView:"year",
    		    minView: "month",//表示日期选择的最小范围，默认是hour
    		});
    		$('#work_end').datetimepicker("show");
    	}
	}
});

/**
 * 中间的导航按钮实例
 */
var myTab = new Vue({
	el:'#myTab',
	methods:{
		//获取我的动态
		getMyDynamic:function(){
			personalEvent.getMyDynamic("0");
		},
		//点击我的关系获取我关注的
		getMyRelationship:function(){
			personalEvent.getMyAttention("0");
		},
		//获取我的收藏
		getMyCollection:function(){
			personalEvent.getMyCollection("1");
		}
	}
});


/**
 * 我的动态数据
 */
var myDynamic = new Vue({
	el:'#myDynamic',
	data:{
		classes:[true,false],
		dynamic:{}
	},
	methods:{
		getMyDynamic:function(t){
			personalEvent.getMyDynamic(t);
		},
		gotoUser:function(uid){
			return "myBlogView.action?userId="+uid;
		},
		gotoArticle:function(aid,i){
			if(i == 1)
				return "articleDetail.action?article.articleId="+aid+"#articleComments";
			if(i == 0)
				return "articleDetail.action?article.articleId="+aid;
		}
	}
});

/**
 * 我的关系vue实例
 */
var myRelationship = new Vue({
	el:'#myRelationship',
	data:{
		classes:[true,false,false],
		attentions:{}
	},
	methods:{
		//点击选择查看
		getMyAttention:function(t){
			personalEvent.getMyAttention(t);
		},
		//点击用户进入用户博客
		gotoUser:function(uid){
			return "myBlogView.action?userId="+uid;
		},
		validateUser:function(){
			var userId = GetQueryString("userId");
    		return personalEvent.validateUser(userId);
    	}
	}
});

/**
 * 我的收藏
 */
var myCollection = new Vue({
	el:'#myCollection',
	data:{
		page:{},
		collections:{}
	},
	methods:{
		validateUser:function(){
			var userId = GetQueryString("userId");
    		return personalEvent.validateUser(userId);
    	},
    	deleteCollection:function(cId){
    		personalEvent.deleteCollection(cId);
    	},
    	gotoArticle:function(aid){
			return "articleDetail.action?article.articleId="+aid;
		},
		goPage:function(num){
			personalEvent.getMyCollection(num);
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

/**
 * 第一次加载页面数据
 */
personalEvent.getUserData(null);
