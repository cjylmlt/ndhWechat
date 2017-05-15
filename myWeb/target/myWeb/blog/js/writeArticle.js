/**
*文章发表
*author jyh
*2017-02-26
*/
var cls = [];

var articleData = {
}

var writeEvent = {
	init:function(){
		
		
		$('[name="ac"]').each(function(i,e){
			var acId = $(this).html();
			var n = $('#'+acId).attr('checked','checked');
		});
		
		writeEvent.settingFormValidate();
		
		//限制标题字数
		$('#articleTitle').bind("keyup keydown change",function(){
			if($('#articleTitle').val().length >= 25){
				$('#articleTitle').val() = $('#articleTitle').val().substring(0,25);
			}
		});
		
		//文章设置提交
		$('#settingSubmit').click(function(){
			
			$('#settingForm').bootstrapValidator('validate');
			
			if($("#settingForm").data('bootstrapValidator').isValid())
				writeEvent.save(1);
		});
		
		//保存草稿
		$('#articleSave').click(function(){
			writeEvent.save(0);
		});
		
		//发表文章
		$('#publish').click(function(){
			writeEvent.publish();
		});
		
	},
	
	//发表文章前的设置
	publish:function(){
		var articleTitle = $('#articleTitle').val();
		var articleContent = $('#inputText').val();
		
		var str = marked(articleContent).replace(/<\/?[^>]*>/g, ''); // 去除HTML tag
		str.value = str.replace(/[ | ]*\n/g, '\n'); // 去除行尾空白
		str = str.replace(/[ | ]*\n/g, '\n'); // 去除行尾空白
		str = str.replace(/ /ig, '').replace(/<\/?.+?>/g,"").replace(/[\r\n]/g, "");// 去掉
		
		if(articleTitle == "" || articleContent == "")
			alert("文章标题和内容都不能为空!");
		else{
			
			$("#articleSummary").val(str.substring(0,200));
			$("#myModal").modal('show');
		}
		
	},
	
	//保存
	save:function(state){
		writeEvent.setData();
		articleData['article.state'] = state;
		articleData['article.articleId'] = $('#articleId').html();
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/saveArticle.action',
			dataType : 'json',
			data:articleData,
			success : function(data) {
				if (data.state == "0") {
					alert("发表成功!");
					window.location.href="writeArticle.action"
				} else if (data.state == "2") {
					alert(data.message);
				} else if(data.state == "3"){
					window.location.href=data.message;
				}
			}
		})
	},
	
	//填充数据
	setData:function(){
		//遍历这三种元素
		$('input,textarea,select').each(function(i,element){
			var name = $(this).attr('name');//获取元素名
			if(name != "" && name != undefined && name != null){
				//如果是checkbox类型的，判断有没有被选中
				if($(this).attr('type') == 'checkbox'){
					if(!$(this)[0].checked)
						return;//没有被选中则进行下一步
				}
				//以元素名为key，元素值为value创建json数据
				articleData[name] = $(this).val();
			}
			
		});
	},
	
	//表单验证
	settingFormValidate:function(){
		$('#settingForm').bootstrapValidator({
			feedbackIcons : {
				valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
			},
			fields : {
				"article.type" : {
					validators : {
						notEmpty : {
							message : '文章类型不能为空'
						}
					}
				},
				"article.classification" : {
					validators : {
						notEmpty : {
							message : '文章分类不能为空'
						}
					}
				}
			}
		});
	}
	
}

$(function(){
	writeEvent.init();
});