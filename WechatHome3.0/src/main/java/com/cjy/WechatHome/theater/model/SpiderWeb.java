package com.cjy.WechatHome.theater.model;

public class SpiderWeb {
	private int id;
	private String webUrl;
	private String searchUrl;
	private String titleRegex1;
	private String picRegex1;
	private String videoRegex1;
	private String titleRegex2;
	private String picRegex2;
	private String videoRegex2;
	private int userId;
	private String webName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public String getTitleRegex1() {
		return titleRegex1;
	}
	public void setTitleRegex1(String titleRegex1) {
		this.titleRegex1 = titleRegex1;
	}
	public String getPicRegex1() {
		return picRegex1;
	}
	public void setPicRegex1(String picRegex1) {
		this.picRegex1 = picRegex1;
	}
	public String getVideoRegex1() {
		return videoRegex1;
	}
	public void setVideoRegex1(String videoRegex1) {
		this.videoRegex1 = videoRegex1;
	}
	public String getTitleRegex2() {
		return titleRegex2;
	}
	public void setTitleRegex2(String titleRegex2) {
		this.titleRegex2 = titleRegex2;
	}
	public String getPicRegex2() {
		return picRegex2;
	}
	public void setPicRegex2(String picRegex2) {
		this.picRegex2 = picRegex2;
	}
	public String getVideoRegex2() {
		return videoRegex2;
	}
	public void setVideoRegex2(String videoRegex2) {
		this.videoRegex2 = videoRegex2;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getWebName() {
		return webName;
	}
	public void setWebName(String webName) {
		this.webName = webName;
	}

	
}
