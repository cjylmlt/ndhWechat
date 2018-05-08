package com.cjy.WechatHome.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjy.WechatHome.async.EventHandler;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.dao.TopRecordDao;
import com.cjy.wechathome.data.web.TopRecord;
import com.cjy.WechatHome.web.service.RecordService;
@Component
public class TopRecordHandler implements EventHandler{
@Autowired
TopRecordDao topRecordDao;
@Autowired
RecordService recordService;
	@Override
	public void doHandle(EventModel model) {
		// TODO Auto-generated method stub
		List<TopRecord> topRecordList=recordService.selectTopRecord();
		TopRecord subRecord = recordService.selectSubRecord();
		TopRecord unSubRecord = recordService.selectUnsubRecord();
		synchronized (topRecordDao) {
			Random random = new Random();
			topRecordDao.DisableTopRecord();
			if(topRecordList!=null){
				for(TopRecord r:topRecordList){
					r.setCount(r.getCount()*50+random.nextInt(50));
					r.setUpdateTime(new Date());
					r.setStatus(0);
					topRecordDao.insertTopRecord(r);
				}
			}
			topRecordDao.DisableSubRecord();
			if(subRecord!=null){
				subRecord.setCount(subRecord.getCount()*100+random.nextInt(100));
				subRecord.setStatus(0);
				subRecord.setUpdateTime(new Date());
				topRecordDao.insertTopRecord(subRecord);
			}
			topRecordDao.DisableUnsubRecord();
			if(unSubRecord!=null){
				unSubRecord.setCount(unSubRecord.getCount()*35+random.nextInt(35));
				unSubRecord.setStatus(0);
				unSubRecord.setUpdateTime(new Date());
				topRecordDao.insertTopRecord(unSubRecord);
			}
		}
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.TOPRECORD);
	}

}
