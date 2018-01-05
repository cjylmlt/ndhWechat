package com.cjy.WechatHome.model;

import java.util.Date;

/**
 * @Description: TODO
 * @author cjy
 * @date 2017年12月4日 下午7:25:48  
 */
public class WechatStatics {
	private int id;
	private Date date;
	private int newUser;
	private int cancelUser;
	private int netgainUser;
	private int cumulateUser;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNewUser() {
		return newUser;
	}
	public void setNewUser(int newUser) {
		this.newUser = newUser;
	}
	public int getCancelUser() {
		return cancelUser;
	}
	public void setCancelUser(int cancelUser) {
		this.cancelUser = cancelUser;
	}
	public int getNetgainUser() {
		return netgainUser;
	}
	public void setNetgainUser(int netgainUser) {
		this.netgainUser = netgainUser;
	}
	public int getCumulateUser() {
		return cumulateUser;
	}
	public void setCumulateUser(int cumulateUser) {
		this.cumulateUser = cumulateUser;
	}
	
}
