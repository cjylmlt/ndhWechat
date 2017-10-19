package com.imooc.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imooc.po.News;
import com.imooc.po.TextMessage;
import com.imooc.spider.VideoSpider;
import com.imooc.util.CheckUtil;
import com.imooc.util.MessageUtil;


public class WechatServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		PrintWriter out = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	//@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName= map.get("FromUserName");
			String toUserName= map.get("ToUserName");
			String msgType= map.get("MsgType");
			String content= map.get("Content");
			String message = null;
			String reply = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				String urlContent = URLEncoder.encode(content,"UTF-8");
				List<News> newsList = VideoSpider.getVideoMessage(urlContent);
				if(newsList.size()>0)
					message = MessageUtil.packNewsMessage(toUserName, fromUserName,newsList);
				else {
					reply = "您当前搜索的影视尚未收入，请留言想看的影片，我们会后台更新";
					message=MessageUtil.packText(toUserName, fromUserName, reply);
				}

			}
			else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.packText(toUserName, fromUserName, MessageUtil.subScribeMenu());
				}
				else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){
					
				}
				else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					
				}
				else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
					
				}
			}
			else{
				TextMessage text = new TextMessage();
				text.setFromUserName(toUserName);
				text.setToUserName(fromUserName);
				text.setMsgType("text");
				text.setCreateTime(new Date().toString());
				text.setContent("暂不支持此消息类型，期待升级哦！");
				message = MessageUtil.textMessageToXml(text);
				
			}
			System.out.print(message);
			out.print(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			out.close();
		}
	}
}
