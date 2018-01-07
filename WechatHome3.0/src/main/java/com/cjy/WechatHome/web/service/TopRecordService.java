package com.cjy.WechatHome.web.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.dao.TopRecordDao;
import com.cjy.WechatHome.web.model.TopRecord;



@Service
public class TopRecordService {
	@Autowired
	RecordService recordService;
	@Autowired
	TopRecordDao topRecordDao;
	@Autowired
	EventProducer eventProducer;
	public List<TopRecord> selectTopRecord(){
		synchronized(topRecordDao){
			List<TopRecord> topRecordList = topRecordDao.selectTopRecord();
			//需要更新toprecord
			Random random = new Random();
			if(topRecordList.size()==0){
				topRecordList=recordService.selectTopRecord();
				if(topRecordList==null)
					return null;
				for(TopRecord r:topRecordList){
					r.setCount(r.getCount()*50+random.nextInt(50));
					r.setUpdateTime(new Date());
					r.setStatus(0);
					topRecordDao.insertTopRecord(r);
				}
			}
			else if((new Date().getTime()-topRecordList.get(0).getUpdateTime().getTime())>3600*1000*2400){
				EventModel eventModel = new EventModel(EventType.TOPRECORD);
				eventProducer.fireEvent(eventModel);
			}
			return topRecordDao.selectTopRecord();
		}
		
	}
	
	public TopRecord selectSubRecord(String subOrUnsub){
		synchronized (topRecordDao) {
			TopRecord subOrUnsubRecord = topRecordDao.selectSubRecord(subOrUnsub);
			//需要更新toprecord
			Random random = new Random();
			if(subOrUnsubRecord==null){
				if(subOrUnsub.equals("subscribe")){
					subOrUnsubRecord = recordService.selectSubRecord();
					if(subOrUnsubRecord==null)
						return null;
					subOrUnsubRecord.setCount(subOrUnsubRecord.getCount()*100+random.nextInt(100));
				}
				else{
					subOrUnsubRecord = recordService.selectUnsubRecord();
					if(subOrUnsubRecord==null)
						return null;
					subOrUnsubRecord.setCount(subOrUnsubRecord.getCount()*35+random.nextInt(35));
				}

				subOrUnsubRecord.setStatus(0);
				subOrUnsubRecord.setUpdateTime(new Date());
				topRecordDao.insertTopRecord(subOrUnsubRecord);
			}
			return topRecordDao.selectSubRecord(subOrUnsub);
		}
		
	}
}
