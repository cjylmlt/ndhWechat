package com.cjy.WechatHome.web.service;

import com.cjy.WechatHome.dao.DefinedReplyDao;
import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.dao.UserDao;
import com.cjy.WechatHome.dao.UserSettingDao;
import com.cjy.WechatHome.web.model.LoginTicket;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.web.model.UserSetting;
import com.cjy.WechatHome.wechat.model.DefinedReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class UserSettingService {
	@Autowired
	UserSettingDao userSettingDao;

	public void insertUserSetting(UserSetting userSetting) {
		userSettingDao.insertUserSetting(userSetting);
	}

	public UserSetting selectUserSetting(int userId) {
		UserSetting userSetting = userSettingDao.selectUserSetting(userId);
		if(userSetting==null){
			userSetting = new UserSetting();
			userSetting.setId(userId);
			userSetting.setRegisterTime(10);
			userSetting.setRecommendTime(15);
			userSetting.setRecommendNum(5);
			userSetting.setAlipayKey("VQiR7X406h");
			insertUserSetting(userSetting);
		}
		return userSetting;
	}
	public void updateUserSetting(UserSetting userSetting){
		userSettingDao.updateUserSetting(userSetting);
	}
}
