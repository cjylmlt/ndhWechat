package com.cjy.WechatHome.model;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.ClearCommand;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {
	private static ThreadLocal<User> users = new ThreadLocal<User>();
	public User getUser(){
		return users.get();
	}
	public void setUser(User user){
		users.set(user);
	}
	public void clear(){
		users.remove();
	}
	
}