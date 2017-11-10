package com.cjy.WechatHome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.WechatUserDao;
import com.cjy.WechatHome.model.WechatUser;

@Service
public class WechatUserService {
	@Autowired
	WechatUserDao wechatUserDao;
	public boolean isWchatUserExist(String openId){
		WechatUser user = wechatUserDao.selectWechatById(openId);
		if(user==null){
			return false;
		}
		else{
			return true;
		}
	}
	public WechatUser selectWechatUser(String openId){
		WechatUser user = wechatUserDao.selectWechatById(openId);
		if(user==null){
			return null;
		}
		else{
			return user;
		}
	}
	public void regWechatUser(WechatUser wechatUser){
		wechatUserDao.addWechatUser(wechatUser);
	}
	public void updateExpireTime(WechatUser wechatUser){
		wechatUserDao.updateExpireTime(wechatUser);
	}
}
