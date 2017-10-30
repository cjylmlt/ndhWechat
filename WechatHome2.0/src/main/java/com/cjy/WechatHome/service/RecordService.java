package com.cjy.WechatHome.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cjy.WechatHome.dao.RecordDao;
import com.cjy.WechatHome.model.Record;
import com.cjy.WechatHome.model.TopRecord;
@Controller
public class RecordService{
	@Autowired
	RecordDao recordDao;
	public void insertRecord(Record record){
		recordDao.insertRecord(record);
	}
	public List<TopRecord> selectTopRecord(){
		return recordDao.selectTopRecord();
	}
}
