package com.cjy.WechatHome.util;

import com.cjy.WechatHome.model.OnlineMediaMessage;
import com.cjy.WechatHome.type.MediaType;

public class OnlineMediaUtil {
	
	public static OnlineMediaMessage getOnlineMediaMessage(MediaType type){
		OnlineMediaMessage message = new OnlineMediaMessage();
		message.setCount(20);
		message.setOffset(0);
		String a = type.toString();
		message.setType(type.toString());
		return message;
	}
	public static OnlineMediaMessage getLastMediaMessage(MediaType type){
		OnlineMediaMessage message = new OnlineMediaMessage();
		message.setCount(1);
		message.setOffset(0);
		message.setType(type.toString());
		return message;
	}

}
