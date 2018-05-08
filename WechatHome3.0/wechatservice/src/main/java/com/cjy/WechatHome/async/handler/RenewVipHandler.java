package com.cjy.WechatHome.async.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjy.WechatHome.async.EventHandler;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.theater.service.FanService;
import com.cjy.WechatHome.theater.service.MessageService;
@Component
public class RenewVipHandler implements EventHandler{
@Autowired
EventProducer eventProducer;
@Autowired
MessageService messageService;
@Autowired
FanService fanService;
	@Override
	public void doHandle(EventModel model) {
		String openId = model.getExt("openId");
		String range = model.getExt("range");
		int days = Integer.valueOf(model.getExt("days"));
		if(range.equals("all")){
			fanService.updateAllFan(openId, days);
			
		}
		else if(range.equals("expired")){
			fanService.updateExpiredFan(openId, days);
		}
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.RENEWVIP);
	}

}
