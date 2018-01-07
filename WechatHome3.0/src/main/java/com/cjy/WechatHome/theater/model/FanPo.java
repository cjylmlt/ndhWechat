package com.cjy.WechatHome.theater.model;

public class FanPo extends Fan {
	private boolean isExpired;

	public FanPo(Fan fan){
		setOpenId(fan.getOpenId());
		setUserName(fan.getUserName());
		setBelongOwnerId(fan.getBelongOwnerId());
		setExpireTime(fan.getExpireTime());
		setRecommendNum(fan.getRecommendNum());
	}

	public boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}


	
	
}
