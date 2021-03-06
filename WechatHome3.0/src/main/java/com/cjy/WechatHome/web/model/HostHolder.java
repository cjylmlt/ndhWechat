package com.cjy.WechatHome.web.model;

import org.springframework.stereotype.Component;

import com.cjy.WechatHome.theater.model.Fan;


@Component
public class HostHolder {
	private static ThreadLocal<User> users = new ThreadLocal<User>();
	private static ThreadLocal<Fan> fans = new ThreadLocal<Fan>();
	private static ThreadLocal<User> wechatOwnerUsers = new ThreadLocal<User>();
	public User getUser(){
		return users.get();
	}
	public void setUser(User user){
		users.set(user);
	}
	public void clear(){
		users.remove();
	}
	public Fan getFan(){
		return fans.get();
	}
	public void setFan(Fan fan){
		fans.set(fan);
	}
	public void clearFans(){
		fans.remove();
	}
	public User getFanOwnerUser(){
		return wechatOwnerUsers.get();
	}
	public void setFanOwnerUser(User user){
		wechatOwnerUsers.set(user);
	}
	public void clearFanOwnerUsers(){
		wechatOwnerUsers.remove();
	}
}
