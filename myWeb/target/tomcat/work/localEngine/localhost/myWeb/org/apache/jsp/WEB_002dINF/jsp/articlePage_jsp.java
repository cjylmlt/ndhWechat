package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import java.util.*;
import java.util.*;

public final class articlePage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(4);
    _jspx_dependants.add("/WEB-INF/jsp/common/head.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/leftPage.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/articleDetail.jsp");
    _jspx_dependants.add("/WEB-INF/jsp/common/tail.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>主页</title>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/css/home.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/css/myblog.css\">\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/css/index.css\">\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap-3.3.7/css/bootstrap.min.css\" />\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap-3.3.7/css/bootstrap.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap-3.3.7/css/mystyle.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap-3.3.7/css/style.css\" />\r\n");
      out.write("\r\n");
      out.write("<meta name=\"viewport\"\r\n");
      out.write("\tcontent=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">\r\n");
      out.write("\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t");
      out.write("\n");
      out.write("\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/frame/bootstrap/css/bootstrap.css\">\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\n");
      out.write("\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/css/index.css\">\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/js/jquery.js\"></script>\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/frame/bootstrap/js/bootstrap.js\"></script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\t$(function(){\n");
      out.write("\t\t$('.logout').click(function(){\n");
      out.write("\t\t\tvar id = '");
      out.print(session.getAttribute("userId") );
      out.write("';\n");
      out.write("\t\t\tif(id==\"null\")\n");
      out.write("\t\t\t\talert(\"您还未登陆\");\n");
      out.write("\t\t\telse\n");
      out.write("\t\t\t\twindow.location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/logout';\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t});\n");
      out.write("\t\t$('.myBlog').click(function(){\n");
      out.write("\t\t\tvar id = '");
      out.print(session.getAttribute("userId") );
      out.write("';\n");
      out.write("\t\t\tif(id==\"null\")\n");
      out.write("\t\t\t\talert(\"您还未登陆\");\n");
      out.write("\t\t\telse{\n");
      out.write("\t\t\t\twindow.location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/index/'+id.toString()+'/1';\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t});\n");
      out.write("\t\t$('.personalCenter').click(function(){\n");
      out.write("\t\t\tvar id = '");
      out.print(session.getAttribute("userId") );
      out.write("';\n");
      out.write("\t\t\tif(id==\"null\")\n");
      out.write("\t\t\t\talert(\"您还未登陆\");\n");
      out.write("\t\t\telse{\n");
      out.write("\t\t\t\twindow.location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/index/'+id.toString()+'/personalCenter';\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t});\n");
      out.write("\t\t$('.blogManage').click(function(){\n");
      out.write("\t\t\tvar id = '");
      out.print(session.getAttribute("userId") );
      out.write("';\n");
      out.write("\t\t\tif(id==\"null\")\n");
      out.write("\t\t\t\talert(\"您还未登陆\");\n");
      out.write("\t\t\telse{\n");
      out.write("\t\t\t\twindow.location.href='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/index/'+id.toString()+'/blogManage';\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\t\t\n");
      out.write("\t\t});\n");
      out.write("\t\t\n");
      out.write("\t});\n");
      out.write("</script>\n");
      out.write("\t<div class=\"head\">\n");
      out.write("\t\t<div class=\"head_main\">\n");
      out.write("\t\t\t<div class=\"logo\">\n");
      out.write("\t\t\t\t<img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/images/user.jpg\"> <a id=\"gohome\"\n");
      out.write("\t\t\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/index\">CJY博客</a>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"head_search\">\n");
      out.write("\t\t\t\t<form action=\"searchAllArticle.action\" method=\"post\" target=\"_blank\">\n");
      out.write("\t\t\t\t\t<div class=\"input-group\">\n");
      out.write("\t\t\t\t\t\t<input id=\"searchKey\" name=\"queryCondition\" type=\"text\" class=\"form-control\" placeholder=\"请输入搜索内容\">\n");
      out.write("\t\t\t\t\t\t<span class=\"input-group-btn\">\n");
      out.write("\t\t\t\t\t\t\t<button id=\"globalSearch\" class=\"btn btn-default\" type=\"submit\">搜索</button>\n");
      out.write("\t\t\t\t\t\t</span>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t</form>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div class=\"option\">\n");
      out.write("\t\t\t\t<ul class=\"nav nav-pills\">\n");
      out.write("\t\t\t\t\t\t");
      if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\t<li role=\"presentation\"><a class=\"myBlog\"\n");
      out.write("\t\t\t\t\t\t\thref=\"javascript:;\">我的博客</a></li>\n");
      out.write("\t\t\t\t\t\t<li role=\"presentation\"><a class=\"personalCenter\"\n");
      out.write("\t\t\t\t\t\t\thref=\"javascript:;\">个人中心</a></li>\n");
      out.write("\t\t\t\t\t\t<li role=\"presentation\"><a class=\"blogManage\"\n");
      out.write("\t\t\t\t\t\t\thref=\"javascript:;\">博客管理</a></li>\n");
      out.write("\t\t\t\t\t\t<li role=\"presentation\"><a\n");
      out.write("\t\t\t\t\t\t\thref=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/writeArticle\"\n");
      out.write("\t\t\t\t\t\t\ttarget=\"_blank\">写博客</a></li>\n");
      out.write("\t\t\t\t\t\t<li role=\"presentation\" ><a class = \"logout\" href=\"javascript:;\">退出</a></li>\n");
      out.write("\t\t\t\t</ul>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t</div>\n");
      out.write("\t<div id=\"main\" class=\"main\">");
      out.write("\r\n");
      out.write("\t<div class=\"mytitle\">\r\n");
      out.write("\t\t<h1>CJY博客</h1>\r\n");
      out.write("\t\t<h3>DEBUG!!!!</h3>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class=\"main_content\">\r\n");
      out.write("\t\t<div class=\"main_left\">\r\n");
      out.write("\t\t\t");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t$(function(){\r\n");
      out.write("\t\t\t$('.trick').click(function(){\r\n");
      out.write("\t\t\t\talert(\"简直异想天开\")\r\n");
      out.write("\t\t\t})\r\n");
      out.write("\t\t})\r\n");
      out.write("\t</script>\r\n");
      out.write("\r\n");
      out.write("\t<ul class=\"list-group new-group\">\r\n");
      out.write("\t\t<li class=\"list-group-item new-item\">\r\n");
      out.write("\t\t\t<ul class=\"list-group\">\r\n");
      out.write("\t\t\t\t<li href=\"#\" class=\"list-group-item first\">博主资料</li>\r\n");
      out.write("\t\t\t\t<li style=\"width:230px ;height:230px\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/picture/userImage/user.jpg\" class=\"user_img\"></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item first\">联系方式</li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\">电话:<a class=\"trick\">点击查看</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\">微信:<a class=\"trick\">点击查看</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\">邮箱:cjylemonlight@163.com</li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\">qq:<a class=\"trick\">点击查看</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li class=\"list-group-item new-item\">\r\n");
      out.write("\t\t\t<ul class=\"list-group articleClass\">\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item first\">博文分类</li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"0\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">移动开发</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"1\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">web前端</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"2\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">架构设计</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"3\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">编程语言</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"4\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">互联网</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"5\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">数据库</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"6\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">系统运维</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"7\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">云计算</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"8\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">研发管理</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a articleClass=\"9\"\r\n");
      out.write("\t\t\t\t\thref=\"javascript:;\">综合</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t<li class=\"list-group-item new-item\">\r\n");
      out.write("\t\t\t<ul class=\"list-group\">\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item first\">推荐网站</li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a\r\n");
      out.write("\t\t\t\t\thref=\"https://github.com/OverrideRe\" target=\"_blank\">github</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a href=\"http://blog.csdn.net/\"\r\n");
      out.write("\t\t\t\t\ttarget=\"_blank\">CSDN</a></li>\r\n");
      out.write("\t\t\t\t<li class=\"list-group-item\"><a href=\"http://www.cnblogs.com/\"\r\n");
      out.write("\t\t\t\t\ttarget=\"_blank\">博客园</a></li>\r\n");
      out.write("\t\t\t</ul>\r\n");
      out.write("\t\t</li>\r\n");
      out.write("\t\t\r\n");
      out.write("\t</ul>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</html>");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"main_right\" id=\"contentShow\">\r\n");
      out.write("\t\t\t");
      out.write("\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\"\n");
      out.write("\tsrc=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/js/myBlog.js\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/js/marked.js\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/js/myBlog.js\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/blog/js/comments.js\"></script>\n");
      out.write("<script src=\"http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/highlight.min.js\"></script>\n");
      if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t</div>\r\n");
      out.write("\t");
      out.write("\n");
      out.write("\n");
      out.write("</div>\n");
      out.write("<div class=\"tail\">\n");
      out.write("\t<div>\n");
      out.write("\t\t<h5>Copyright © 2017 CJY个人博客 版权所有</h5>\n");
      out.write("\t</div>\n");
      out.write("</div>\n");
      out.write("</div>\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f0.setParent(null);
    // /WEB-INF/jsp/common/head.jsp(69,6) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${empty sessionScope.userId}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
    if (_jspx_eval_c_005fif_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t<li role=\"presentation\"><a\n");
        out.write("\t\t\t\t\t\t\thref=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("/loginView\">登录</a></li>\n");
        out.write("\t\t\t\t\t\t<li role=\"presentation\"><a\n");
        out.write("\t\t\t\t\t\t\thref=\"");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("/registerView\">注册</a></li>\n");
        out.write("\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f1 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f1.setParent(null);
    // /WEB-INF/jsp/articleDetail.jsp(9,0) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f1.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${!empty article}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
    if (_jspx_eval_c_005fif_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t<script type=\"text/javascript\">\n");
        out.write("\t\t$(function() {\n");
        out.write("\t\t\tarticleDetailEvent.init();\n");
        out.write("\t\t});\n");
        out.write("\t</script>\n");
        out.write("\t<div id=\"blog_article\">\n");
        out.write("\t\t<ul class=\"list-group new-group\">\n");
        out.write("\t\t\t<li class=\"list-group-item new-item\">\n");
        out.write("\t\t\t\t<div class=\"panel panel-default\">\n");
        out.write("\t\t\t\t\t<div class=\"panel-body\" style=\"padding: 12px\">\n");
        out.write("\t\t\t\t\t\t<div class=\"myblogs\">\n");
        out.write("\t\t\t\t\t\t\t<div class=\"myblog_title\">\n");
        out.write("\t\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
          return true;
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t<a href=\"javascript:;\">");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${article.title}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</a> <a\n");
        out.write("\t\t\t\t\t\t\t\t\thref=\"javascript:;\"\n");
        out.write("\t\t\t\t\t\t\t\t\tstyle=\"float: right; font-size: 14px; margin-right: 5px;\"\n");
        out.write("\t\t\t\t\t\t\t\t\tid=\"goBack\">返回</a>\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t<div>\n");
        out.write("\t\t\t\t\t\t\t\t<div style=\"margin-bottom: 15px;\">标签:");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${article.tag}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</div>\n");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t<div class=\"select_other\">\n");
        out.write("\t\t\t\t\t\t\t\t\t<div class=\"classification\">文章所在分类:</div>\n");
        out.write("\t\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t\t\t<!-- \t\t  \t\t\t\t<div class = \"article_info\"> -->\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t<!-- \t\t\t\t\t\t\t\t\t<a href=\"loginView.action\" target=\"_blank\">收藏</a> -->\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t");
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t<!-- \t\t  \t\t\t\t</div> -->\n");
        out.write("\t\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t</li>\n");
        out.write("\t\t\t<li class=\"list-group-item new-item\">\n");
        out.write("\t\t\t\t<div class=\"panel panel-default\">\n");
        out.write("\t\t\t\t\t<textarea hidden id=\"markedContent\">");
        out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${article.content}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
        out.write("</textarea>\n");
        out.write("\t\t\t\t\t<div class=\"panel-body articleDetailShow\" id=\"contentDetail\">\n");
        out.write("\t\t\t\t\t</div>\n");
        out.write("\t\t\t\t</div>\n");
        out.write("\t\t\t</li>\n");
        out.write("\t\t\t<!-- \t\t\t<li class=\"list-group-item new-item\"> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t<div class=\"panel panel-default\"> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\tset集合无法获取当前文章的上下篇，所以转换成数组 -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t<div class=\"panel-body\" style=\"padding: 12px\"> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t\t上一篇:&nbsp;&nbsp;&nbsp; -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t\t\t<span>没有上一篇了!</span> -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" class=\"otherArticle\" -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t</div> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t<div class=\"panel-body\" style=\"padding: 12px\"> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t\t下一篇:&nbsp;&nbsp;&nbsp; -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t\t\t<span>没有下一篇了!</span> -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" class=\"otherArticle\" -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\t</div> -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t</div> -->\n");
        out.write("\t\t\t<!-- \t\t\t</li> -->\n");
        out.write("\t\t\t<li class=\"list-group-item new-item\">\n");
        out.write("\t\t\t\t<ul class=\"list-group\" id=\"thisArticleComments\">\n");
        out.write("\n");
        out.write("\t\t\t\t\t");
        out.write("\n");
        out.write("\n");
        out.write("\t\t\t\t</ul>\n");
        out.write("\t\t\t</li>\n");
        out.write("\t\t\t<!-- \t\t\t<li class=\"list-group-item new-item\"> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t<ul class=\"list-group\"> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t<li class=\"list-group-item first\">发表评论</li> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t<li class=\"list-group-item\" id=\"commentIntanceInstance\"><input -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\tid=\"thisArticleId\" name=\"commentArticle\" hidden -->\n");
        out.write("\t\t\t");
        out.write("\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\tid=\"commentContent\" class=\"form-control\" style=\"height: 100px\"></textarea> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t<button type=\"button\" class=\"btn btn-default\" -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t\t\tonclick=\"commentEvent.submitComment();\" style=\"margin-top: 10px\">提交</button> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t\t</li> -->\n");
        out.write("\t\t\t<!-- \t\t\t\t</ul> -->\n");
        out.write("\t\t\t<!-- \t\t\t</li> -->\n");
        out.write("\t\t</ul>\n");
        out.write("\t\t<!-- \t\t<div class=\"modal fade\" id=\"myModal\" tabindex=\"-1\" role=\"dialog\" -->\n");
        out.write("\t\t<!-- \t\t\taria-labelledby=\"myModalLabel\"> -->\n");
        out.write("\t\t<!-- \t\t\t<div class=\"modal-dialog\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t<div class=\"modal-content\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t<div class=\"modal-header\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t<button data-dismiss=\"modal\" class=\"close\" type=\"button\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t\t<span aria-hidden=\"true\">×</span><span class=\"sr-only\">Close</span> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t</button> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t<h4 class=\"modal-title\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t\t回复:<span id=\"replyUserName\"></span> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t</h4> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t</div> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t<div class=\"modal-body\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t<textarea id=\"replyContent\" class=\"form-control\" -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t\tstyle=\"height: 100px\"></textarea> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t</div> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t<div class=\"modal-footer\"> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t<button data-dismiss=\"modal\" class=\"btn btn-default\" type=\"button\">关闭</button> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t<button class=\"btn btn-primary\" -->\n");
        out.write("\t\t<!-- \t\t\t\t\t\t\tonclick=\"commentEvent.submitReply()\" type=\"button\">提交</button> -->\n");
        out.write("\t\t<!-- \t\t\t\t\t</div> -->\n");
        out.write("\t\t<!-- \t\t\t\t</div> -->\n");
        out.write("\t\t<!-- \t\t\t\t/.modal-content -->\n");
        out.write("\t\t<!-- \t\t\t</div> -->\n");
        out.write("\t\t<!-- \t\t\t<!-- /.modal-dialog -->\n");
        out.write("\t\t-->\n");
        out.write("\t\t<!-- \t\t</div> -->\n");
        out.write("\t</div>\n");
        int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f2 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f1);
    // /WEB-INF/jsp/articleDetail.jsp(22,8) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f2.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${article.type} == 0", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
    if (_jspx_eval_c_005fif_005f2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t\t<span class=\"label label-success\" style=\"font-size: 13px;\">原</span>&nbsp;&nbsp;\n");
        out.write("\t\t  \t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f3(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f3 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f1);
    // /WEB-INF/jsp/articleDetail.jsp(25,8) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f3.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${article.type} == 1", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
    if (_jspx_eval_c_005fif_005f3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t\t<span class=\"label label-warning\" style=\"font-size: 13px;\">转</span>&nbsp;&nbsp;\n");
        out.write("\t\t  \t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
    return false;
  }

  private boolean _jspx_meth_c_005fif_005f4(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_005fif_005f4 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
    _jspx_th_c_005fif_005f4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fif_005f1);
    // /WEB-INF/jsp/articleDetail.jsp(28,8) name = test type = boolean reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fif_005f4.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${article.type} == 2", java.lang.Boolean.class, (PageContext)_jspx_page_context, null, false)).booleanValue());
    int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
    if (_jspx_eval_c_005fif_005f4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n");
        out.write("\t\t\t\t\t\t\t\t\t<span class=\"label label-danger\" style=\"font-size: 13px;\">译</span>&nbsp;&nbsp;\n");
        out.write("\t\t  \t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_005fif_005f4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
    return false;
  }
}
