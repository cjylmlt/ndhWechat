package com.cjy.WechatHome.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.AdminSettingDao;
import com.cjy.WechatHome.dao.NewsDao;
import com.cjy.WechatHome.dao.RecordDao;
import com.cjy.WechatHome.dao.SpiderWebDao;
import com.cjy.WechatHome.model.News;
import com.cjy.WechatHome.model.NewsPo;
import com.cjy.WechatHome.model.Record;
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
}
