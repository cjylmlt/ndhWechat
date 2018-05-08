package com.cjy.wechathome.data.theater;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FanPo{
	private String isExpired;
	private String dateString;
	private String openId;
	private String userName;
	private int recommendNum;
	public String getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
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
	public int getRecommendNum() {
		return recommendNum;
	}
	public void setRecommendNum(int recommendNum) {
		this.recommendNum = recommendNum;
	}
	public FanPo(Fan fan){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		openId = fan.getOpenId();
		userName = fan.getUserName();
		recommendNum = fan.getRecommendNum();
		dateString = dateFormat.format(fan.getExpireTime());
		if(new Date().before(fan.getExpireTime())){
			isExpired="正常";
		}
		else{
			isExpired="过期";
		}
	}

	

	


	
	
}
