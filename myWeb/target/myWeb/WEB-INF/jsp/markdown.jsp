<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title id="title">写博客</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/blog/frame/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/blog/frame/bootstrapValidator/bootstrapValidator.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/frame/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/frame/bootstrapValidator/bootstrapValidator.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/markdownEditor.css"></link>
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/styles/default.min.css">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/highlight.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/ajaxfileupload.js"></script>
	<script src="${pageContext.request.contextPath}/blog/js/marked.js"></script>
	<script src="${pageContext.request.contextPath}/blog/js/markdown.js"></script>
	<script src="${pageContext.request.contextPath}/blog/js/writeArticle.js"></script>
	<script type="text/javascript">

	</script>
</head>
<body>
	<div style="display:none;">
		<!-- 不知道为什么取不到input的值 所以都用span代替了-->
		<span id="articleId">${article.id}</span>
		<!-- 保存文章所在的分类 -->
		<c:forEach items="${article.classes}" var="ac">
			<span name="ac">${ac.personalClassification.classificationId}</span>
    	</c:forEach>
	</div>
	<div class="mark-head">
		<input id="articleTitle" value="${article.title}" name="title" placeholder="文章标题"/>
		<button id="publish" type="button" class="btn btn-primary">发表博客</button>
	</div>
	
	<div class="su-toolbar">
	    <div class="tool-block su-tool-bold" title="加粗(Ctrl+B)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-bold"></span>
	    </div>
	    <div class="tool-block su-tool-italic" title="斜体(Ctrl+I)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-italic"></span>
	    </div>
	    <div class="tool-block su-tool-head" title="标题(Ctrl+H)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-header"></span>
	    </div>
	    <div class="tool-block su-tool-link" title="链接(Ctrl+L)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-link"></span>
	    </div>
	    <div class="tool-block su-tool-img" title="图片(Ctrl+G)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-picture"></span>
	    </div>
	    <div class="tool-block su-tool-list" title="无序列表(Ctrl+U)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-list"></span>
	    </div>
	    <div class="tool-block su-tool-orderlist" title="有序列表(Ctrl+O)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-th-list"></span>
	    </div>
	    <div class="tool-block su-tool-code" title="单行代码(Ctrl+K)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-asterisk"></span>
	    </div>
	    <div class="tool-block su-tool-block" title="引用(Ctrl+Q)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-chevron-right"></span>
	    </div>
		<div class="tool-block su-tool-tab" title="区块(Ctrl+P)" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-align-justify"></span>
	    </div>
	    
	    <div style="float: left;line-height:40px;font-size: 24px;color: #e5e5e5;">|</div>
	    
	    <div id="articleSave" class="tool-block" title="保存" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-floppy-disk"></span>
	    </div>
	    
		<!-- 
		<div id="articleSetting" class="tool-block" title="设置" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-cog"></span>
	    </div>
	    <div id="articleSummary" class="tool-block" title="摘要" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-list-alt"></span>
	    </div>
	     -->
	    <div id="help" class="tool-block" title="帮助" data-placement="top" data-toggle="tooltip">
	        <span class="glyphicon glyphicon-question-sign"></span>
	    </div>
	</div>
	
	<div class="suEditor">
	   <textarea name="content" 
	   class="text-input" oninput="markdown.change()" id="inputText" placeholder="正文">${article.content}</textarea>
	</div>
	<div class="suPreview" id="preview">
	</div>
	
	<div class="help" >
		<div class="help-head">
			<h4 style="display: inline;">Markdown语法帮助<a id="helpClose"  class="help-close">关闭</a></h4>
		</div>
		<div class="help-middle">
			<div class="middle-list">
				<ul>
					<li><a class="tag" data-id="a">标题</a></li>
					<li><a class="tag" data-id="b">列表</a></li>
					<li><a class="tag" data-id="c">引用</a></li>
					<li><a class="tag" data-id="d">粗体与斜体</a></li>
					<li><a class="tag" data-id="e">链接与图片</a></li>
					<li><a class="tag" data-id="f">代码块与语法高亮</a></li>
					<li><a class="tag" data-id="g">分割线与删除线</a></li>
					<li><a class="tag" data-id="h">表格</a></li>
				</ul>
			</div>
			<div class="middle-right"></div>
		</div>
	</div>
	<div style="display: none">
		<div id="a">
			<h4>在文字写书写不同数量的#可以完成不同的标题，如下：</h4>
			<p># 一级标题<br>
				## 二级标题<br>
				### 三级标题<br>
				#### 四级标题<br>
				##### 五级标题<br>
				###### 六级标题</p>
			<h4>等号及减号也可以进行标题的书写，不过只能书写二级标题，并且需要写在文字的下面，减号及等号的数量不会影响标题的基数，如下：</h4>
			<p>二级标题<br>
				=========</p>
			<p>二级标题<br>
				---------</p>
		</div>
		
		<div id="b">
			<h4>无序列表的使用，在符号“-”后加空格使用。如下：</h4>
			<p> - 无序列表1<br>
				- 无序列表2<br>
				- 无序列表3</p>
			<h4>如果要控制列表的层级，则需要在符号“-”前使用空格。如下：</h4>
			<p> - 无序列表1<br>
				- 无序列表2<br>
				&nbsp;&nbsp;- 无序列表2.1<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 列表内容<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 列表内容</p>
			<p>&nbsp;</p>
			<h4>有序列表的使用，在数字及符号“.”后加空格几个，如下：</h4>
			<p> 1. 有序列表1<br>
				2. 有序列表2<br>
				3. 有序列表3<br>
			</p>
			<h4>有序列表如果要区分层级，也可以在数字前加空格。</h4>
		</div>
		
		<div id="c">
			<h4>引用的格式是使用符号“&gt;”后面书写文字，及可以使用引用。如下：</h4>
			<p> &gt;这个是引用<br> &gt; 是不是和电子邮件中的<br>
				&gt; 引用格式很像</p>
		</div>
		
		<div id="d">
			<h4>
				粗体的使用是在需要加粗的文字前后各加两个“*”，而斜体的使用则是在需要斜体的文字前后各加一个“*”，如果要使用粗体和斜体，那么就是在需要操作的文字前后各加三个“*”。如下：</h4>
			<p> **这个是粗体**<br>
				*这个是斜体*<br>
				***这个是粗体加斜体***</p>
		</div>
		
		<div id="e">
			<h4>在文中直接加链接，中括号中是需要添加链接的文字，圆括号中是需要添加的链接，如下：</h4>
			<p> [link text](http://example.com/ "optional title")</p>
			<h4>在引用中加链接，第一个中括号添加需要添加的文字，第二个中括号中是引用链接的id，之后在引用中，使用id加链接：如下：</h4>
			<p> [link text][id]<br>
				[id]: http://example.com/ "optional title here"</p>
			<h4>在文中直接引用链接，直接使用尖括号，把链接加入到尖括号中就可以实现，如下：</h4>
			<p>&lt;http://example.com/&gt; or &lt;address@example.com&gt;<br>
			</p>
			<h4>插入互联网上图片，格式如下：</h4>
			<p>![这里写图片描述](http://img3.douban.com/mpic/s1108264.jpg)<br>
				![这里写图片描述][jane-eyre-douban]<br>
				[jane-eyre-douban]: http://img3.douban.com/mpic/s1108264.jpg</p>
		</div>
		
		<div id="f">
			<h4>用TAB键起始的段落，会被认为是代码块，如下：</h4>
			<p> &nbsp;&nbsp;&nbsp;&nbsp;&lt;php&gt;<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;echo “hello world";<br>
				&nbsp;&nbsp;&nbsp;&nbsp;&lt;/php&gt;</p>
			<h4>如果在一个行内需要引用代码，只要用反引号`引起来就好，如下：</h4>
			<p> Use the <code>`printf()` </code>function.</p>
			<br/>
			<h4>在需要高亮的代码块的前一行及后一行使用三个反引号“`”，同时第一行反引号后面表面代码块所使用的语言，如下：</h4>
			<p>```ruby<br>
				require 'redcarpet'<br>
				markdown = Redcarpet.new("Hello World!")<br>
				puts markdown.to_html<br>
				```</p>
		</div>
		
		<div id="g">
			<h4>可以在一行中用三个以上的星号、减号、底线来建立一个分隔线，同时需要在分隔线的上面空一行。如下：</h4>
			<p> ---<br>
				****<br>
				___</p>
			<h4>删除线的使用，在需要删除的文字前后各使用两个符合“~”，如下</h4>
			<p> ~~Mistaken text.~~</p>
		</div>
		
		<div id="h">
			<h4>可以使用冒号来定义表格的对齐方式，如下：</h4>

			<p> | Tables | Are | Cool |<br>
				| ------------- |:-------------:| -----:|<br>
				| col 3 is | right-aligned | $1600 |<br>
				| col 2 is | centered | $12 |<br>
				| zebra stripes | are neat | $1 |<br>
			</p>
		</div>
	</div>


	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div id="settingDialog" class="modal-dialog">
			<div class="modal-content" style="width:500px;">
				<form id="settingForm" class="form-horizontal" style="margin-bottom: 0px;">
					<div class="modal-header">
						<button data-dismiss="modal" class="close" type="button">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title">文章设置</h4>
					</div>
					<div class="modal-body">
						<div class="form-group" style="margin-top:20px;">
		                    <label class="col-lg-3 control-label">文章类型:</label>
		                    <div class="col-lg-7">
		                        <select class="form-control" name="type">
		                            <option value="">-- 请选择 --</option>
		                            <option value="0" ${article.type eq 0?'selected':''}>原创</option>
		                            <option value="1" ${article.type eq 1?'selected':''}>转载</option>
		                            <option value="2" ${article.type eq 2?'selected':''}>翻译</option>
		                        </select>
		                    </div>
		                </div>
		                
		                <div class="form-group" style="margin-top:20px;">
		                    <label class="col-lg-3 control-label">个人分类:</label>
		                    <div class="col-lg-7">
		                    	<!--<c:if test="#session.user.personalClassifications.size() <= 0">
									<font color="red">请先创建分类!</font>
								</c:if>
								<c:if test="#session.user.personalClassifications.size() > 0">
									<c:forEach items="${sessionScope.user.personalClassifications}" var="cl" varStatus="i">
										<div style="float:left;margin-right:15px;">
										
							            	<input type="checkbox" id="${cl.classificationId}" value="${cl.classificationId}" 
							            	name="classList[${i.index}].classificationId"/>
							            	${cl.classificationName}
							            </div>
							    	</c:forEach>
								</c:if>-->
		                    </div>
		                </div>
		                
		                <div class="form-group" style="margin-top:20px;">
		                    <label class="col-lg-3 control-label">文章分类:</label>
		                    <div class="col-lg-7">
		                        <select class="form-control" name="classification">
		                            <option value="">-- 请选择 --</option>
		                            <option value="0" ${article.classification eq 0?'selected':''}>移动开发</option>
		                            <option value="1" ${article.classification eq 1?'selected':''}>Web前端</option>
		                            <option value="2" ${article.classification eq 2?'selected':''}>架构设计</option>
		                            <option value="3" ${article.classification eq 3?'selected':''}>编程语言</option>
		                            <option value="4" ${article.classification eq 4?'selected':''}>互联网</option>
		                            <option value="5" ${article.classification eq 5?'selected':''}>数据库</option>
		                            <option value="6" ${article.classification eq 6?'selected':''}>系统运维</option>
		                            <option value="7" ${article.classification eq 7?'selected':''}>云计算</option>
		                            <option value="8" ${article.classification eq 8?'selected':''}>研发管理</option>
		                            <option value="9" ${article.classification eq 9?'selected':''}>综合</option>
		                        </select>
		                    </div>
		                </div>
		                
		                <div class="form-group" style="margin-top:20px;">
	                        <label class="col-lg-3 control-label">文章标签:</label>
	                        <div class="col-lg-7">
	                            <input value="${article.tag}" class="form-control" name="tag" />
	                        </div>
	                    </div>
		               
		               	<div class="form-group" style="margin-top:20px;">
	                        <label class="col-lg-3 control-label">文章摘要:</label>
	                        <div class="col-lg-7">
	                            <textarea id="articleSummary" value="${article.summary}" cols="34" name="summary" rows="10" ></textarea>
	                        </div>
	                    </div>
                    
	                </div>
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
						<button class="btn btn-primary" id="settingSubmit" type="button">确定</button>
					</div>
				</form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	
	
	<div class="modal fade" id="findImg" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			
			<div class="modal-content" style="width: 500px;">
				<div class="modal-header" style="margin-bottom:15px;">
					<button data-dismiss="modal" class="close" type="button">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">选择图片</h4>
				</div>
				<div class="form-horizontal">
				
				
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#networkPicture" data-toggle="tab">在线图片</a></li>
						<li><a href="#uploadPicture" data-toggle="tab">上传图片</a></li>
					</ul>
                 	<div id="myTabContent" class="tab-content">
                 		<div class="tab-pane fade in active" style="margin-top:30px;margin-bottom:30px;" id="networkPicture">
                 			<div class="form-group">
			                    <label class="col-lg-3 control-label">图片链接:</label>
								 <div class="col-lg-7">
			                        <input type="text" class="form-control"/>
			                    </div>
			                </div>
		                </div>
		                <div class="tab-pane fade" style="margin-top:30px;margin-bottom:30px;" id="uploadPicture">
			                <div class="form-group">
			                    <label class="col-lg-3 control-label">上传图片:</label>
			                    <div class="col-lg-7">
			                        <input type="file" id="pictureUpload" name="file" class="form-control" name="firstFile" />
			                    </div>
			                </div>
		                </div>
					</div>
	            </div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn btn-default"
						type="button">关闭</button>
					<button class="btn btn-primary" type="button" id="submitPicture">提交</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>