package com.cjy.WechatHome.service;

import com.cjy.WechatHome.dao.MessageDAO;
import com.cjy.WechatHome.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/24.
 */
@Service
public class MessageService {

    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    public int addMessage(Message message) {
        message.setContent(message.getContent());
        return messageDAO.addMessage(message) > 0 ? message.getId() : 0;
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return  messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(String userId, int offset, int limit) {
        return  messageDAO.getConversationList(userId, offset, limit);
    }

    public List<Message> getMessageList(String userId, int offset, int limit) {
        return  messageDAO.getMessageList(userId, offset, limit);
    }
    
    public int getConversationUnreadCount(String userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }
    public int getUnreadCount(String userId) {
        return messageDAO.getUnreadCount(userId);
    }
    public void updateReadStatus(String userId){
    	messageDAO.updateReadStatus(userId);
    }
    public void deleteMessageByUserId(String userId){
    	messageDAO.deleteMessageByUserId(userId);
    }
    
}
