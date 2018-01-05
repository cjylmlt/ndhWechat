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
import com.cjy.WechatHome.service.ReplyService;
import com.cjy.WechatHome.service.UserService;
@Controller
public class WechatReplyController {
	@Autowired
	DefinedReplyService definedReplyService;
	@Autowired
	NewsService newsService;
	@Autowired
	RecordService recordService;
	@Autowired
	UserService userService;
	@Autowired
	ReplyService replyService;
	@RequestMapping(path={"/WechatHome/maxiaodai.do"},method = {RequestMethod.GET})
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
	@RequestMapping(path={"/WechatHome/maxiaodai.do"},method = {RequestMethod.POST})
	@ResponseBody
	public String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		long endTime;
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		Record record = new Record();
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
			if(user == null||(user.getStatus()!=0&&user.getStatus()!=2&&user.getStatus()!=3)){	
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
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				message = replyService.textMessageReply(map, user, record);
			}
			else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				message = replyService.EventMessageReply(map, user, record);
			}
			else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String label = map.get("Label");
				label = "您当前的位置在:"+label;
				message = MessageUtil.packText(toUserName, fromUserName, label);
				
			}
			else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
				if(user.getStatus()==2||user.getStatus()==3){//超级用户
					message = replyService.voiceMessageReply(map, user, record);
				}
				if(user.getStatus()==0){//普通用户
					String warning = "您当前所属的微信公众号后台属于普通用户，不支持语音识别功能，想搭建支持全套影视功能的微信公众号后台请加微信 GeekMa_199X";
					message = MessageUtil.packText(toUserName, fromUserName,warning);
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
			
			record.setUserId(toUserName);
			record.setUsername(user.getUsername());
			record.setUserUrl(userUrl);
			record.setDate(new Date());
			record.setReply(message);
			record.setStatus(0);
			
			endTime = System.currentTimeMillis();
			record.setDurationTime(endTime-startTime);
			//record.setSpiderTime(spiderTimeEnd-spiderTimeStart);
			recordService.insertRecord(record);
			return message;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
}
