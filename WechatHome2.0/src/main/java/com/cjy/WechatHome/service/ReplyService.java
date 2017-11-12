package com.cjy.WechatHome.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.model.DefinedReply;
import com.cjy.WechatHome.model.News;
import com.cjy.WechatHome.model.NewsPo;
import com.cjy.WechatHome.model.Record;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.spider.VideoSpider;
import com.cjy.WechatHome.util.MessageUtil;
import com.cjy.WechatHome.util.UserUtil;
import com.cjy.WechatHome.util.WechatUtil;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventType;

@Service
public class ReplyService {
	@Autowired
	DefinedReplyService definedReplyService;
	@Autowired
	VideoSpider videoSpider;
	@Autowired
	NewsService newsService;
	@Autowired
	EventProducer eventProducer;
	public String textMessageReply(Map<String, String> map,User user,Record record) throws Exception{
		long spiderTimeStart = 0;
		long spiderTimeEnd = 0;
		String fromUserName= map.get("FromUserName");
		String toUserName= map.get("ToUserName");
		String msgType= map.get("MsgType");
		String content= map.get("Content");
		String message = null;
		DefinedReply definedReply;
		List<DefinedReply> adList;
		String reply;
		record.setContent(content);
		if((definedReply = definedReplyService.getReply(content, user.getUsername()))!=null){
			if(definedReply.getPicUrl()!=null){
				List<News> newsList = new ArrayList<>();
				News news = new News();
				news.setDescription("");
				news.setTitle(definedReply.getValue());
				news.setPicUrl(definedReply.getPicUrl());
				news.setUrl(definedReply.getUrl());
				newsList.add(news);
				message = MessageUtil.packNewsMessage(toUserName, fromUserName,newsList);
			}
			else
				message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
		}
		else{
			String urlContent = URLEncoder.encode(content,"UTF-8");
			spiderTimeStart = System.currentTimeMillis();
			List<News> newsList = new ArrayList<>();
			List<NewsPo> newsPoList;
			newsPoList = newsService.selectNewsPoByKey(content);
			if(newsPoList.size()>0){
				//System.out.println("数据库读取");
				newsList = newsService.selectNewsByKey(content);
				if((new Date().getTime()-newsPoList.get(0).getUpdateTime().getTime())>3600*1000*24){
					EventModel eventModel = new EventModel(EventType.VIDEOSPIDER);
					eventModel.setExt("urlContent", urlContent);
					eventModel.setExt("content", content);
					eventProducer.fireEvent(eventModel);
				}
			}
			else{
				newsList = videoSpider.getVideoMessage(urlContent);	
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
			for(News n: newsList){
				n.setUrl(WechatUtil.packUserUrl(n.getUrl(),user.getUserId()));
				
			}
			spiderTimeEnd = System.currentTimeMillis();
			if(newsList.size()>0){
				if((adList = definedReplyService.getADList(user.getUsername()))!=null){
					if(adList.size()>0&&adList.get(0).getValue()!=null&&!adList.get(0).getValue().equals("")){
						DefinedReply d = adList.get(0);
						News news = new News();
						news.setDescription("");
						news.setTitle(d.getValue());
						news.setPicUrl(d.getPicUrl());
						news.setUrl(d.getUrl());
						newsList.add(1, news);
					}
//					for(DefinedReply d:adList){
//						if((8-newsList.size())>0){
//							News news = new News();
//							news.setDescription("");
//							news.setTitle(d.getValue());
//							news.setPicUrl(d.getPicUrl());
//							news.setUrl(d.getUrl());
//							newsList.add(news);
//						}
//					}
				}
				message = MessageUtil.packNewsMessage(toUserName, fromUserName,newsList);
			}
			else {
				
				if( definedReplyService.getReply("搜索不到的回复", user.getUsername())!=null)
					reply = definedReplyService.getReply("搜索不到的回复", user.getUsername()).getValue();
				else{
					reply = "您输入的消息为"+"'"+content+"',"+"搜索不到您当前输入的资源，请检查下您的片名是否正确";
				}
				if(reply==null||reply.equals("")){
					reply = "您输入的消息为"+"'"+content+"',"+"搜索不到您当前输入的资源，请检查下您的片名是否正确";
				}
				message=MessageUtil.packText(toUserName, fromUserName, reply);
			}
		}
		record.setSpiderTime(spiderTimeEnd-spiderTimeStart);
		return message;
	}
	public String voiceMessageReply(Map<String, String> map,User user,Record record) throws Exception{
		String voice = map.get("Recognition");
		voice = voice.substring(0, voice.length()-1);
		map.put("Content",voice);
		return textMessageReply(map, user, record);
	}
	
	public String EventMessageReply(Map<String, String> map,User user,Record record) throws Exception{
		String eventType = map.get("Event");
		String content = eventType;
		record.setContent(content);
		String msgType= map.get("MsgType");
		DefinedReply definedReply;
		String message = null;
		String fromUserName= map.get("FromUserName");
		String toUserName= map.get("ToUserName");
		String reply="";
		if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
			if((definedReply = definedReplyService.getReply("新关注的回复", user.getUsername()))!=null){
				if(definedReply.getValue()!=null){
					message = MessageUtil.packText(toUserName, fromUserName, definedReply.getValue());
				}
				else{
					message = MessageUtil.packText(toUserName, fromUserName, reply);
				}
			}
			else{
				//reply = "欢迎您的关注，回复影视关键词即可在线观看超清资源，如果喜欢的话，请推荐给你的朋友们哦";
				message = MessageUtil.packText(toUserName, fromUserName, reply);
			}
		}
		else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){
			
		}
		else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
			String buttonKey = map.get("EventKey");
			if("33".equals(buttonKey)){
				reply="商务合作请联系我\n微信：18822802281\nQQ：8652837\nTel：18822802281\n非客户不要轻易投食，合作请注明公司，合作项目，其他勿扰";
			}
			else{
				reply = "你点击了一下，真调皮";
			}
			message = MessageUtil.packText(toUserName, fromUserName, reply);
		}
		else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){
			message = MessageUtil.packText(toUserName, fromUserName, reply);
		}
		return message;
	}
}
