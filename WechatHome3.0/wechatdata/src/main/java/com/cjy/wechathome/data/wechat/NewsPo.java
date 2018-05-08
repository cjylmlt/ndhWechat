package com.cjy.wechathome.data.wechat;

import java.util.Date;

public class NewsPo extends News {
	private String key;
	private Date updateTime;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
