package com.cjy.WechatHome.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjy.WechatHome.async.EventHandler;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.dao.TopRecordDao;
import com.cjy.wechathome.data.theater.Message;
import com.cjy.WechatHome.theater.service.MessageService;
import com.cjy.WechatHome.web.service.RecordService;
@Component
public class MessageHandler implements EventHandler{
@Autowired
TopRecordDao topRecordDao;
@Autowired
RecordService recordService;
@Autowired
MessageService messageService;
	@Override
	public void doHandle(EventModel model) {
		Message regMessage = new Message();
		String content = model.getExt("content");
		String fromId = model.getExt("fromId");
		String toId = model.getExt("toId");
		if(toId==null)
			return;
		regMessage.setContent(content);
		regMessage.setFromId(fromId);
		regMessage.setToId(toId);
		String conversationId;
        if (fromId.compareTo(toId)>=0) {
        	conversationId = String.format("%s_%s",fromId, toId);
        } else {
        	conversationId = String.format("%s_%s", toId, fromId);
        }
        regMessage.setConversationId(conversationId);
        regMessage.setCreatedDate(new Date());
        regMessage.setHasRead(0);
        messageService.addMessage(regMessage);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.MESSAGE);
	}

}
