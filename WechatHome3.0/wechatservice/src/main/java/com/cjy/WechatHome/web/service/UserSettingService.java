package com.cjy.WechatHome.web.service;

import com.cjy.WechatHome.dao.UserSettingDao;
import com.cjy.wechathome.data.web.UserSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
