package com.cjy.WechatHome.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.AdminSettingDao;
import com.cjy.WechatHome.dao.SpiderWebDao;
import com.cjy.WechatHome.model.SpiderWeb;

@Service
public class SpiderWebService{
	@Autowired
	SpiderWebDao spiderWebDao;
	@Autowired
	AdminSettingDao adminSettingDao;
	public SpiderWeb selectSpiderWeb(int id){
		return spiderWebDao.selectUserWeb(id);
	}
	public List<SpiderWeb> selectSpiderWebList(){
		return spiderWebDao.selectWebList();
	}
	public SpiderWeb selectChoosenSpiderWeb(){
		return spiderWebDao.selectUserWeb(Integer.parseInt(adminSettingDao.selectAdminSetting("hostWeb")));
	}
	public void updateWebUrl(String webUrl,int id){
		spiderWebDao.updateWebUrl(webUrl, id);
	}
}
