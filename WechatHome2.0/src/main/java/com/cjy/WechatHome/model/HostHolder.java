package com.cjy.WechatHome.model;

import org.springframework.stereotype.Component;


@Component
public class HostHolder {
	private static ThreadLocal<User> users = new ThreadLocal<User>();
	private static ThreadLocal<WechatUser> wechatUsers = new ThreadLocal<WechatUser>();
	public User getUser(){
		return users.get();
	}
	public void setUser(User user){
		users.set(user);
	}
	public void clear(){
		users.remove();
	}
	public WechatUser getWechatUser(){
		return wechatUsers.get();
	}
	public void setWechatUser(WechatUser wechatUser){
		wechatUsers.set(wechatUser);
	}
	public void clearWechatUser(){
		wechatUsers.remove();
	}

}
