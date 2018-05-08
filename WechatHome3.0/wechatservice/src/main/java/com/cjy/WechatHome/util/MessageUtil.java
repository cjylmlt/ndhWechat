package com.cjy.WechatHome.util;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cjy.wechathome.data.wechat.News;
import com.cjy.wechathome.data.wechat.NewsMessage;
import com.cjy.wechathome.data.wechat.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import com.thoughtworks.xstream.XStream;





@Service
public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_NEWS = "news";
	
	
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception{
		Map<String, String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e:list)
			map.put(e.getName(), e.getText());
		ins.close();
		return map;
	}
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
		
	}
	/*
	 * 主菜单
	  */
	public static String subScribeMenu(){
		StringBuffer sb= new StringBuffer();
		sb.append("欢迎您的关注，回复影视关键词即可在线观看超清资源，如果喜欢的话，请推荐给你的朋友们哦");
		return sb.toString();
	}
	
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item",new News().getClass());
		return xStream.toXML(newsMessage);
		
	}
	
	public static String packNewsMessage(String toUserName,String fromUserName,List<News> newsList){
		String message = null;
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().toString());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	public static String packText(String toUserName,String fromUserName,String reply){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType("text");
		text.setCreateTime(new Date().toString());
		text.setContent(reply);
		return MessageUtil.textMessageToXml(text);
		
	}
}
