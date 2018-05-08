package com.cjy.wechathome.data.wechat;

import java.util.Date;

public class ElasticSearchRecord {
    private String fromUserId;
    private String ownerId;
    private String receivedMessage;
    private String sentMessage;
    private Date messageTime;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public String getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(String sentMessage) {
        this.sentMessage = sentMessage;
    }


    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
}
