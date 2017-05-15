/**
 * json数据传输测试
 */

$(function(){
	$("#btn1").click(function(){
		myEvent.getOne();
	});
	$("#btn2").click(function(){
		myEvent.getAll();
	});
});

var myEvent = {
	getOne:function(){
		//表单提交
		var user = $("#form1").serialize();
		$.ajax(
			{
				type:'post',
				url:'getOne.action',
				dataType:'json',
				data:user,
				success:function(data){
					//data = Object {userJson: "{"id":1,"name":"张三","password":"123"}"}
					//data是一个字符串对象,里面的字符串是一个json类型,所以需要得到data.userJson然后再转换成对象
					var u = $.parseJSON(data.userJson);		  //json字符串转换成对象
					alert(u.name);
					//var u = JSON.stringify(data); //json对象转换成json字符串
				},
				error:function(){
					alert("没有返回");
				}
			}	
		);
	},
	getAll:function(){
		//表单提交
		var users = $("#form2").serialize();
		$.ajax(
			{
				type:'post',
				url:'getAll.action',
				dataType:'json',
				data:users,
				success:function(data){
					var u = $.parseJSON(data.usersJson);		  //json字符串转换成对象
					alert(u[0].name + "," + u[1].name);
				},
				error:function(){
					alert("没有返回");
				}
			}	
		);
	}

};


