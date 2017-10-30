package com.cjy.WechatHome.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.WechatHome.model.DefinedReply;
import com.cjy.WechatHome.model.News;
import com.cjy.WechatHome.model.NewsPo;
import com.cjy.WechatHome.model.Record;
import com.cjy.WechatHome.model.TextMessage;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.spider.VideoSpider;
import com.cjy.WechatHome.util.CheckUtil;
import com.cjy.WechatHome.service.DefinedReplyService;
import com.cjy.WechatHome.util.MessageUtil;
import com.cjy.WechatHome.service.NewsService;
import com.cjy.WechatHome.service.RecordService;
import com.cjy.WechatHome.service.UserService;
@Controller
public class WechatController {
	@Autowired
	DefinedReplyService definedReplyService;
	@Autowired
	NewsService newsService;
	@Autowired
	RecordService recordService;
	@Autowired
	UserService userService;
	
	@RequestMapping(path={"/maxiaodai.do"},method = {RequestMethod.GET})
	@ResponseBody
	public String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			return echostr;
		}
		else
			return null;
	}
	@RequestMapping(path={"/maxiaodai.do"},method = {RequestMethod.POST})
	@ResponseBody
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		long endTime;
		long spiderTimeStart = 0;
		long spiderTimeEnd = 0;
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		try {
			String userUrl = req.getRequestURL().toString();
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName= map.get("FromUserName");
			String toUserName= map.get("ToUserName");
			String msgType= map.get("MsgType");
			String content= map.get("Content");
			String message = null;
			String reply = null;
			User user = userService.getUserByUserId(toUserName);
			if(user == null||user.getStatus()!=0){
				Record record = new Record();
				record.setUserId(toUserName);
				if(user!=null){
					record.setUsername(user.getUsername());
				}
				else{
					record.setUsername("外部用户");
				}
				List<News> mindList = newsService.selectNewsByKey("外部用户的提示");
				if(mindList.size()>0){
					message = MessageUtil.packNewsMessage(toUserName, fromUserName,mindList);
					return message;
				}
				record.setUserUrl(userUrl);
				record.setDate(new Date());
				record.setContent(content);
				record.setReply(message);
				record.setStatus(1);
				endTime = System.currentTimeMillis();
				record.setDurationTime(endTime-startTime);
				recordService.insertRecord(record);
						
				return null;
			}
			DefinedReply definedReply;

			List<DefinedReply> adList;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if((definedReply = definedReplyService.getReply(content, user.getUsername()))!=null){
					message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
				}
				else{
					String urlContent = URLEncoder.encode(content,"UTF-8");
					spiderTimeStart = System.currentTimeMillis();
					List<News> newsList = new ArrayList<>();
					List<NewsPo> newsPoList;
					newsPoList = newsService.selectNewsPoByKey(content);
					if(newsPoList.size()>0&&(new Date().getTime()-newsPoList.get(0).getUpdateTime().getTime())<3600*1000){
						//System.out.println("数据库读取");
						newsList = newsService.selectNewsByKey(content);
					}
					else{
						newsList = VideoSpider.getVideoMessage(urlContent);	
						newsService.deleteNewsByKey(content);
						for(News n:newsList){
							NewsPo newsPo = new NewsPo();
							newsPo.setDescription(n.getDescription());
							newsPo.setKey(content);
							newsPo.setPicUrl(n.getPicUrl());
							newsPo.setTitle(n.getTitle());
							newsPo.setUpdateTime(new Date());
							newsPo.setUrl(n.getUrl());
							newsService.insertNews(newsPo);
						}
					}
					spiderTimeEnd = System.currentTimeMillis();
					if(newsList.size()>0){
						if((adList = definedReplyService.getADList(user.getUsername()))!=null){
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
						
						if( definedReplyService.getReply("搜索不到的回复", user.getUsername())!=null)
							reply = definedReplyService.getReply("搜索不到的回复", user.getUsername()).getValue();
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
					if((definedReply = definedReplyService.getReply("新关注的回复", user.getUsername()))!=null){
						message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
					}
					else{
						//reply = "欢迎您的关注，回复影视关键词即可在线观看超清资源，如果喜欢的话，请推荐给你的朋友们哦";
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
			
			endTime = System.currentTimeMillis();
			record.setDurationTime(endTime-startTime);
			record.setSpiderTime(spiderTimeEnd-spiderTimeStart);
			recordService.insertRecord(record);
			return message;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
}
