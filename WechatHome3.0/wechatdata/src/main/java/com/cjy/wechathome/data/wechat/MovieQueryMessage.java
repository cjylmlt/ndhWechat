package com.cjy.wechathome.data.wechat;


import java.util.Date;

public class MovieQueryMessage {
    private String queryUserId;
    private String ownerId;
    private String queryContent;
    private String queryResult;
    private Date queryTime;

    public MovieQueryMessage(){

    }

    public MovieQueryMessage(String queryUserId, String ownerId, String queryContent, String queryResult, Date queryTime) {
        this.queryUserId = queryUserId;
        this.ownerId = ownerId;
        this.queryContent = queryContent;
        this.queryResult = queryResult;
        this.queryTime = queryTime;
    }

    public String getQueryUserId() {
        return queryUserId;
    }

    public void setQueryUserId(String queryUserId) {
        this.queryUserId = queryUserId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }

    public String getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(String queryResult) {
        this.queryResult = queryResult;
    }

    public Date getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Date queryTime) {
        this.queryTime = queryTime;
    }
}
