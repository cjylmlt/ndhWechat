package com.imooc.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjy.imooc.mapper.UserMapper;
import com.imooc.po.DefinedReply;
import com.imooc.po.News;
import com.imooc.po.NewsPo;
import com.imooc.po.Record;
import com.imooc.po.TextMessage;
import com.imooc.po.User;
import com.imooc.spider.VideoSpider;
import com.imooc.util.CheckUtil;
import com.imooc.util.DefinedReplyUtil;
import com.imooc.util.MessageUtil;
import com.imooc.util.NewsUtil;
import com.imooc.util.RecordUtil;



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
		long startTime = System.currentTimeMillis();
		long endTime;
		long spiderTimeStart = 0;
		long spiderTimeEnd = 0;
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			String userUrl = req.getRequestURL().toString();
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName= map.get("FromUserName");
			String toUserName= map.get("ToUserName");
			String msgType= map.get("MsgType");
			String content= map.get("Content");
			String message = null;
			String reply = null;
			User user = DefinedReplyUtil.getUser(toUserName);
			if(user == null||user.getStatus()!=0){
				Record record = new Record();
				record.setUserId(toUserName);
				if(user!=null){
					record.setUsername(user.getUsername());
				}
				else{
					record.setUsername("外部用户");
				}
				List<News> mindList = NewsUtil.selectNewsByKey("外部用户的提示");
				if(mindList.size()>0){
					message = MessageUtil.packNewsMessage(toUserName, fromUserName,mindList);
				}
				record.setUserUrl(userUrl);
				record.setDate(new Date());
				record.setContent(content);
				record.setReply(message);
				record.setStatus(1);
				endTime = System.currentTimeMillis();
				record.setDurationTime(endTime-startTime);
				RecordUtil.insertRecord(record);
				
				out.print(message);
				out.close();		
				return;
			}
			DefinedReply definedReply;

			List<DefinedReply> adList;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if((definedReply = DefinedReplyUtil.getReply(content, user.getUsername()))!=null){
					message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
				}
				else{
					String urlContent = URLEncoder.encode(content,"UTF-8");
					spiderTimeStart = System.currentTimeMillis();
					List<News> newsList = new ArrayList<>();
					List<NewsPo> newsPoList;
					newsPoList = NewsUtil.selectNewsPoByKey(content);
					if(newsPoList.size()>0&&(new Date().getTime()-newsPoList.get(0).getUpdateTime().getTime())<3600*1000){
						//System.out.println("数据库读取");
						newsList = NewsUtil.selectNewsByKey(content);
					}
					else{
						newsList = VideoSpider.getVideoMessage(urlContent);	
						NewsUtil.deleteNewsByKey(content);
						for(News n:newsList){
							NewsPo newsPo = new NewsPo();
							newsPo.setDescription(n.getDescription());
							newsPo.setKey(content);
							newsPo.setPicUrl(n.getPicUrl());
							newsPo.setTitle(n.getTitle());
							newsPo.setUpdateTime(new Date());
							newsPo.setUrl(n.getUrl());
							NewsUtil.insertNews(newsPo);
						}
					}
					spiderTimeEnd = System.currentTimeMillis();
					if(newsList.size()>0){
						if((adList = DefinedReplyUtil.getADList(user.getUsername()))!=null){
							if(adList.size()>0){
								DefinedReply d = adList.get(0);
								News news = new News();
								news.setDescription("");
								news.setTitle(d.getValue());
								news.setPicUrl(d.getPicUrl());
								news.setUrl(d.getUrl());
								newsList.add(1, news);
							}
//							for(DefinedReply d:adList){
//								if((8-newsList.size())>0){
//									News news = new News();
//									news.setDescription("");
//									news.setTitle(d.getValue());
//									news.setPicUrl(d.getPicUrl());
//									news.setUrl(d.getUrl());
//									newsList.add(news);
//								}
//							}
						}
						message = MessageUtil.packNewsMessage(toUserName, fromUserName,newsList);
					}
					else {
						
						if( DefinedReplyUtil.getReply("搜索不到的回复", user.getUsername())!=null)
							reply = DefinedReplyUtil.getReply("搜索不到的回复", user.getUsername()).getValue();
						else
							reply = "搜索不到您当前输入的资源，请检查下您的片名是否正确";
						message=MessageUtil.packText(toUserName, fromUserName, reply);
					}
				}
			}
			else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event");
				content = eventType;
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					if((definedReply = DefinedReplyUtil.getReply("新关注的回复", user.getUsername()))!=null){
						message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
					}
					else{
						reply = "欢迎您的关注，回复影视关键词即可在线观看超清资源，如果喜欢的话，请推荐给你的朋友们哦";
						message = MessageUtil.packText(toUserName, fromUserName, reply);
					}
				}
				else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){
					
				}
				else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					message = MessageUtil.packText(toUserName, fromUserName, reply);
				}
				else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
					message = MessageUtil.packText(toUserName, fromUserName, reply);
				}
			}
			else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label = map.get("Label");
				label = "您当前的位置在:"+label;
				message = MessageUtil.packText(toUserName, fromUserName, label);
				
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
			Record record = new Record();
			record.setUserId(toUserName);
			record.setUsername(user.getUsername());
			record.setUserUrl(userUrl);
			record.setDate(new Date());
			record.setContent(content);
			record.setReply(message);
			record.setStatus(0);
			out.print(message);
			endTime = System.currentTimeMillis();
			record.setDurationTime(endTime-startTime);
			record.setSpiderTime(spiderTimeEnd-spiderTimeStart);
			RecordUtil.insertRecord(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			out.close();
		}
	}
}
