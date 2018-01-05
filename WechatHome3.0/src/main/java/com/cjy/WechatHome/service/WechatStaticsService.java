package com.cjy.WechatHome.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.WechatStaticsDao;
import com.cjy.WechatHome.model.WechatStatics;

@Service
public class WechatStaticsService {
	@Autowired
	WechatStaticsDao wechatStaticsDao;
	public List<WechatStatics> selectFirstPage(){
		return wechatStaticsDao.selectFirstPage();
	}
	public Date selectStartDate(){
		return wechatStaticsDao.selectStartDate();
	}
	public Date selectEndDate(){
		return wechatStaticsDao.selectEndDate();
	}
	public void insertStatics(WechatStatics wechatStatics){
		wechatStaticsDao.insertStatics(wechatStatics);
	}
}
