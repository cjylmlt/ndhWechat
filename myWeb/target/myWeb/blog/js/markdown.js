
/**
 * markdown编辑器
 * author:jyh
 * 2017-02-20
 */
var data = null;

var markdown = {
	
	init:function(){
		markdown.tabParse();
		markdown.styleLoad();
		markdown.clickload();
		markdown.change();
	},
	
	//获取选中的文本
	get:function(dst){
       var result={};
       
       $(dst).each(function(i,e){
    	   e.focus();
           var r = document.getSelection();
           if (r.toString() === "")
           {
        	   result = {
                   start: e.selectionStart,
                   end: e.selectionEnd,
                   length: e.value.length,
                   value:e.value,
                   text:""
               };
           }else{
        	   var re = r.getRangeAt(0);
               result = {
            	   start: e.selectionStart,
                   end: e.selectionEnd,
                   length: e.value.length,
                   value:e.value,
                   text: r.toString()
               }
           }
           return true;
       });
       return result;
   },
   
   //文本替换
   replace:function(sel){
	   target = $('#inputText');
	   var start = sel.start;
       var end = sel.end;
       var length = sel.length;
       var text = sel.text;
       var tmp;
       
       $(target).each(function(i,e){
    	   e.value = e.value.substr(0, start) + text + e.value.substr(end, length);
           tmp=$(e);
       });
       return tmp;
   },
   
   //功能键
   methods:{
       bold:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
           var target=data;
           target.text = '**'+target.text+'**';
           var dst = markdown.replace(target);
           return dst;
       },
       italic:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
           target.text = '*'+target.text+'*';
           var dst = markdown.replace(target);
           return dst;
       },
       head:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
    	   var first = target.text.substring(0, 1);
    	   if(first == "#")
    		   target.text = '#'+target.text;
    	   else
    		   target.text = '# '+target.text;
           var dst = markdown.replace(target);
           return dst;
       },
       link:function(){
    	   data = markdown.get($('#inputText'));
           var reg=/^\[[^\0]*\]\([^\0]*\)$/m;
           var target=data;
           var dst;

           if(!reg.test(target.text)) {
               var url=prompt('输入你的链接 :');
               if(!url) 
            	   return $('#inputText');
               if(target.text=='') 
            	   target.text="你的链接地址";
               target.text = ' [ ' + target.text + ' ](' + url + ') ';
               dst = markdown.replace(target);
           }
           else {
        	   target.text.split('[')[1].split(']')[0];
        	   dst=markdown.replace(target);
           }
           return dst;
       },
       list:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
           target.text = '- '+target.text;
           var dst = markdown.replace(target);
           return dst;
       },
       orderlist:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
           target.text = '1. '+target.text;
           var dst = markdown.replace(target);
           return dst;
       },
       code:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
           target.text = '`'+target.text+'`';
           var dst = markdown.replace(target);
           return dst;
       },
       block:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
           target.text = '> '+target.text;
           var dst = markdown.replace(target);
           return dst;
       },
       tab:function(){
    	   if(!markdown.validate())
    		   return $('#inputText');
    	   var target=data;
    	   target.text = '    ' + target.text + '\r\n';
           var dst = markdown.replace(target);
           return dst;
       }
   },

   //快捷键
   keydown:function(event){
       var dst,tmp;
       
       switch(event.which){
           case 17:
               $('#inputText').attr('data-state','1');
               break;
           case 66:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.bold();
                   event.preventDefault();
               }
               break;
           case 73:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.italic();
                   event.preventDefault();
               }
               break;
           case 71:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.img();
                   event.preventDefault();
               }
               break;
           case 72:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.head();
                   event.preventDefault();
               }
               break;
           case 75:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.code();
                   event.preventDefault();
               }
               break;
           case 76:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.link();
                   event.preventDefault();
               }
               break;
           case 79:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.orderlist();
                   event.preventDefault();
               }
               break;
           case 80:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   debugger;
            	   markdown.methods.tab();
                   event.preventDefault();
               }
               break;
           case 81:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	   markdown.methods.block();
                   event.preventDefault();
               }
               break;
           case 85:
               tmp=$('#inputText').attr('data-state');
               if(tmp=='1') {
            	  markdown.methods.list();
                  event.preventDefault();
               }
               break;

           default :   break;
       }
   },
   
   styleLoad:function(){
		 //自适应屏幕高度
			$('.suPreview').css("height",$(window).height()-125);
			$('.suEditor').css("height",$(window).height()-125);
			$('.help').css('display',"none");
			$('.middle-right').html($('#a').html());
	   },
   
   clickload:function(){
	   
	   //按钮监听触发
	   $('.tool-block').each(function(i,element){
	       var str=$(this).attr('class');
	       str=str.split(/\s+/g);
	       for(var i in str){
	           if(str[i].substring(0,8)=='su-tool-'){
	               var method=str[i].substring(8);
	               if(method=='preview') break;
	               if(method=='img'){
	            	   $(this).click(function(){
	            		   $('#findImg').modal('show');
	            	   });
	            	   return;
	               };
	               $(this).on('click',function(event){
	                   event.preventDefault();
	                   var dst = markdown.methods[method]();
	                   $('.suPreview').html(marked(dst.val()));
	                   dst.focus();
	               });
	           }
	       }
	       return true;
	   });
	
	   //选中文字的时候触发，获取选中的文字
	   $('#inputText').select(function(){
		   data = markdown.get($('#inputText'));
		});
	   
	   $("textarea:input").keydown(function(event){
		   markdown.keydown(event);
		});
	   
	   $("[data-toggle='tooltip']").tooltip();
	   
	   //打开或者关闭帮助界面
	   $('#help').click(function(){
			if($('.help').css('display') == "block"){
				$('.help').css('display',"none");
			}else{
				$('.help').css('display',"block");
			}
		});
		
	   //选中帮助目录
		$('.tag').click(function(){
			var id = $(this).attr('data-id');
			var idiv = $('#'+$(this).attr('data-id')).html();
			$('.middle-right').html($('#'+$(this).attr('data-id')).html());
		});
		
		//关闭帮助界面
		$('#helpClose').click(function(){
			$('.help').css('display',"none");
		});
		
		//提交图片
		$('#submitPicture').click(function(){
			markdown.submitPicture();
		});

   },
   
   //tab键换成4个空格
   tabParse:function () {
       var oTxt = document.getElementById("inputText");
       oTxt.onkeydown = function (ev) {
           var oEvent = ev || event;
           if (oEvent.keyCode == 9) {
               /*关键部分 开始(重要! 去掉浏览器的默认事件 不然会按tab之后光标会跳去其他的地方,用户优化不好)*/
               if (oEvent.preventDefault) {
                   oEvent.preventDefault();
               }
               else {
                   window.event.returnValue = false;
               }
               /*关键部分 结束*/
               oTxt.value += "    "; //这里放入了4个空格
           }
       }
   },
   
   //将文本解析成markdown格式
   change:function(){
	   	$('#preview').html(marked($('#inputText').val()));
	},
	
	//验证data是否有内容
	validate:function(){
		if(data != null){
			var m = data.text;
			if(m.Trim() != "")
				return true;
		}
		return false;
	},
	
	//点击弹窗确定
	submitPicture:function(){
		data = markdown.get($('#inputText'));
		
		var picture = $('#myTabContent').children('.active').find('input');
		var value = $(picture).val();
		var type = $(picture).attr('type');
		if(value == "")
			return;
		else if(type == "text"){
			data.text = ' ![](' + value + ') ';
			data = markdown.replace(data);
			$('.suPreview').html(marked(data.val()));
			$('#findImg').modal('hide');
			data.focus();
		}else if(type == "file"){
			$.ajaxFileUpload({
                url: "uploadPicture.action",
                secureuri: false,
                fileElementId: "pictureUpload",
                dataType: "json",
                cache: false,
                ifModified: true,
                beforeSend: function (xmlHttp) { xmlHttp.setRequestHeader("If-Modified-Since", "0"); xmlHttp.setRequestHeader("Cache-Control", "no-cache"); },
                success: function (d) {
                    if(d.state=="0") {
                		data = markdown.get($('#inputText'));
            			data.text = ' ![图片](' + d.filePath + ') ';
            			var dst = markdown.replace(data);
            			$('.suPreview').html(marked(dst.val()));
            			$('#findImg').modal('hide');
                    }
                },
                error: function () {
                	alert("上传失败");
                }
            });
		}
			
	},

};


$(function() {
	
	// 去掉字符两端的空白字符
	String.prototype.Trim = function()
	{
	  return this.replace(/(^\s*)|(\s*$)/g, "");
	}
	//设置代码高亮
	marked.setOptions({
       highlight: function (code) {
           return hljs.highlightAuto(code).value;
         }
   });
	
	markdown.init();
});

