package com.cjy.WechatHome.model;

import java.util.Date;

public class WechatUser {
	private String openId;
	private String userName;
	private String belongOwnerId;
	private Date expireTime;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBelongOwnerId() {
		return belongOwnerId;
	}
	public void setBelongOwnerId(String belongOwnerId) {
		this.belongOwnerId = belongOwnerId;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
}
