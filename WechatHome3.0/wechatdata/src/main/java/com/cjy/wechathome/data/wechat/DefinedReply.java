package com.cjy.wechathome.data.wechat;

public class DefinedReply {
	private int id;
	private String replyKey;
	private String value;
	private String userName;
	private String url;
	private String picUrl;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getReplyKey() {
		return replyKey;
	}
	public void setReplyKey(String replyKey) {
		this.replyKey = replyKey;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
