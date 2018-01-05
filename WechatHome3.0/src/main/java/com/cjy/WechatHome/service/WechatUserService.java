package com.cjy.WechatHome.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.WechatUserDao;
import com.cjy.WechatHome.model.WechatUser;

@Service
public class WechatUserService {
	@Autowired
	WechatUserDao wechatUserDao;
	@Autowired
	MessageService messageService;
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
	public void updateWechatUser(WechatUser wechatUser){
		wechatUserDao.updateWechatUser(wechatUser);
	}
	public WechatUser regWechatUser(String openId,String ownerId){
		WechatUser wechatUser = new WechatUser();
		wechatUser.setOpenId(openId);
		wechatUser.setBelongOwnerId(ownerId);
		String username = "vip-"+UUID.randomUUID().toString().substring(0, 8).replaceAll("-", "");
		wechatUser.setUserName(username);
		wechatUser.setExpireTime(new Date(new Date().getTime()+3600*1000*24*10));
		wechatUserDao.addWechatUser(wechatUser);
		return wechatUser;
	}
	public WechatUser selectWechatUserByName(String username){
		return wechatUserDao.selectWechatByName(username);
	}
	
	public void deleteWechatUser(String username){
		WechatUser user = wechatUserDao.selectWechatByName(username);
		if(user!=null){
			messageService.deleteMessageByUserId(user.getOpenId());
			wechatUserDao.deleteWechatUser(username);
		}
	}
}
