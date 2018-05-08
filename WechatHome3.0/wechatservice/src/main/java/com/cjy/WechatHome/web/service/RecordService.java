package com.cjy.WechatHome.web.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cjy.WechatHome.dao.RecordDao;
import com.cjy.wechathome.data.web.Record;
import com.cjy.wechathome.data.web.TopRecord;
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
	public TopRecord selectSubRecord(){
		return recordDao.selectSubRecord();
	}
	public TopRecord selectUnsubRecord(){
		return recordDao.selectUnsubRecord();
	}

}
