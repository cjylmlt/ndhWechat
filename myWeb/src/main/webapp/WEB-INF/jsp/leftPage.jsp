<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<script type="text/javascript">
		$(function(){
			$('.trick').click(function(){
				alert("简直异想天开")
			})
		})
	</script>

	<ul class="list-group new-group">
		<li class="list-group-item new-item">
			<ul class="list-group">
				<li href="#" class="list-group-item first">博主资料</li>
				<li style="width:230px ;height:230px"><img src="${pageContext.request.contextPath}/picture/userImage/user.jpg" class="user_img"></li>
				<li class="list-group-item first">联系方式</li>
				<li class="list-group-item">电话:<a class="trick">点击查看</a></li>
				<li class="list-group-item">微信:<a class="trick">点击查看</a></li>
				<li class="list-group-item">邮箱:cjylemonlight@163.com</li>
				<li class="list-group-item">qq:<a class="trick">点击查看</a></li>
			</ul>
		</li>
		<li class="list-group-item new-item">
			<ul class="list-group articleClass">
				<li class="list-group-item first">博文分类</li>
				<li class="list-group-item"><a articleClass="0"
					href="javascript:;">移动开发</a></li>
				<li class="list-group-item"><a articleClass="1"
					href="javascript:;">web前端</a></li>
				<li class="list-group-item"><a articleClass="2"
					href="javascript:;">架构设计</a></li>
				<li class="list-group-item"><a articleClass="3"
					href="javascript:;">编程语言</a></li>
				<li class="list-group-item"><a articleClass="4"
					href="javascript:;">互联网</a></li>
				<li class="list-group-item"><a articleClass="5"
					href="javascript:;">数据库</a></li>
				<li class="list-group-item"><a articleClass="6"
					href="javascript:;">系统运维</a></li>
				<li class="list-group-item"><a articleClass="7"
					href="javascript:;">云计算</a></li>
				<li class="list-group-item"><a articleClass="8"
					href="javascript:;">研发管理</a></li>
				<li class="list-group-item"><a articleClass="9"
					href="javascript:;">综合</a></li>
			</ul>
		</li>
		<li class="list-group-item new-item">
			<ul class="list-group">
				<li class="list-group-item first">推荐网站</li>
				<li class="list-group-item"><a
					href="https://github.com/OverrideRe" target="_blank">github</a></li>
				<li class="list-group-item"><a href="http://blog.csdn.net/"
					target="_blank">CSDN</a></li>
				<li class="list-group-item"><a href="http://www.cnblogs.com/"
					target="_blank">博客园</a></li>
			</ul>
		</li>
		
	</ul>


</html>