package com.cjy.WechatHome.wechat.model;



public class MovieQueryMessage {
    private String queryUserId;
    private String ownerId;
    private String queryContent;
    private String queryResult;
    private String queryTime;

    public MovieQueryMessage(){

    }

    public MovieQueryMessage(String queryUserId, String ownerId, String queryContent, String queryResult, String queryTime) {
        this.queryUserId = queryUserId;
        this.ownerId = ownerId;
        this.queryContent = queryContent;
        this.queryResult = queryResult;
        this.queryTime = queryTime;
    }

    public String getQueryContent() {
        return queryContent;
    }

    public void setQueryContent(String queryContent) {
        this.queryContent = queryContent;
    }


    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }

    public String getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(String queryResult) {
        this.queryResult = queryResult;
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
}
