package com.cjy.WechatHome.theater.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventProducer;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.dao.FanDao;
import com.cjy.wechathome.data.theater.Fan;
import com.cjy.wechathome.data.theater.FanPo;
import com.cjy.wechathome.data.theater.FanPoList;
import com.cjy.wechathome.data.web.User;
import com.cjy.WechatHome.web.service.UserService;

@Service
public class FanService {
	@Autowired
	FanDao fanDao;
	@Autowired
	MessageService messageService;
	@Autowired
	EventProducer eventProducer;
	@Autowired
	UserService userService;
	public boolean isFanExist(String openId){
		Fan user = fanDao.selectFanById(openId);
		if(user==null){
			return false;
		}
		else{
			return true;
		}
	}
	public Fan selectFan(String openId){
		Fan user = fanDao.selectFanById(openId);
		if(user==null){
			return null;
		}
		else{
			return user;
		}
	}
	public void updateFan(Fan wechatUser){
		fanDao.updateFan(wechatUser);
	}
	public void updateFan(String openId,int days){
		if(days>100000)
			return;
		Fan fan = selectFan(openId);
		if(new Date().before(fan.getExpireTime())){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(format.format(fan.getExpireTime()));
			fan.setExpireTime(new Date(fan.getExpireTime().getTime()+3600*1000*(long)days*24));
			updateFan(fan);
			System.out.println(format.format(fan.getExpireTime()));
			// 给用户发一份私信
			EventModel eventModel = new EventModel(EventType.MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			eventModel.setExt("content", "为了感谢您的支持,公众号免费赠送您"+days+"天vip时间，您的到期时间为" + dateFormat.format(fan.getExpireTime()));
			eventModel.setExt("fromId", fan.getBelongOwnerId());
			eventModel.setExt("toId", fan.getOpenId());
			eventProducer.fireEvent(eventModel);
		}
		else{
			fan.setExpireTime(new Date(new Date().getTime()+3600*1000*(long)days*24));
			updateFan(fan);
			// 给用户发一份私信
			EventModel eventModel = new EventModel(EventType.MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			eventModel.setExt("content", "为了感谢您的支持，公众号免费赠送您"+days+"天vip时间，您的到期时间为" + dateFormat.format(fan.getExpireTime()));
			eventModel.setExt("fromId", fan.getBelongOwnerId());
			eventModel.setExt("toId", fan.getOpenId());
			eventProducer.fireEvent(eventModel);
		}
	}
	public void updateAllFan(String ownerOpenId,int days){
		if(days>100000)
			return;
		List<Fan> fans = selectFansByOwner(ownerOpenId, 0, 300000);
		for (Fan fan : fans) {
			if(new Date().before(fan.getExpireTime())){
				fan.setExpireTime(new Date(fan.getExpireTime().getTime()+3600*1000*(long)days*24));
				updateFan(fan);
				// 给用户发一份私信
				EventModel eventModel = new EventModel(EventType.MESSAGE);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				eventModel.setExt("content", "为了感谢您的支持,公众号免费赠送您"+days+"天vip时间，您的到期时间为" + dateFormat.format(fan.getExpireTime()));
				eventModel.setExt("fromId", fan.getBelongOwnerId());
				eventModel.setExt("toId", fan.getOpenId());
				eventProducer.fireEvent(eventModel);
			}
			else{
				fan.setExpireTime(new Date(new Date().getTime()+3600*1000*(long)days*24));
				updateFan(fan);
				// 给用户发一份私信
				EventModel eventModel = new EventModel(EventType.MESSAGE);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				eventModel.setExt("content", "为了感谢您的支持，公众号免费赠送您"+days+"天vip时间，您的到期时间为" + dateFormat.format(fan.getExpireTime()));
				eventModel.setExt("fromId", fan.getBelongOwnerId());
				eventModel.setExt("toId", fan.getOpenId());
				eventProducer.fireEvent(eventModel);
			}
		}
	}
	public void updateExpiredFan(String ownerOpenId,int days){
		if(days>100000)
			return;
		List<Fan> fans = selectFansByOwner(ownerOpenId, 0, 300000);
		for (Fan fan : fans) {
			if(new Date().after(fan.getExpireTime())){
				fan.setExpireTime(new Date(new Date().getTime()+3600*1000*(long)days*24));
				updateFan(fan);
				EventModel eventModel = new EventModel(EventType.MESSAGE);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				eventModel.setExt("content", "为了感谢您的支持，公众号免费赠送您"+days+"天vip时间，您的到期时间为" + dateFormat.format(fan.getExpireTime()));
				eventModel.setExt("fromId", fan.getBelongOwnerId());
				eventModel.setExt("toId", fan.getOpenId());
				eventProducer.fireEvent(eventModel);
			}
		}
	}
	public Fan regFan(String openId,String ownerId,int registerTime){
		Fan wechatUser = new Fan();
		wechatUser.setOpenId(openId);
		wechatUser.setBelongOwnerId(ownerId);
		String username = "vip-"+UUID.randomUUID().toString().substring(0, 8).replaceAll("-", "");
		wechatUser.setUserName(username);
		wechatUser.setExpireTime(new Date(new Date().getTime()+3600*1000*24*registerTime));
		if(!isFanExist(openId))
			fanDao.addFan(wechatUser);
		return wechatUser;
	}
	public Fan selectFanByName(String username){
		return fanDao.selectFanByName(username);
	}
	
	public List<Fan> selectFansByOwner(String ownerOpenId,int offset, int limit){
		return fanDao.selectFansByOwner(ownerOpenId,offset,limit);
	}
	
	public Fan selectFanByNameAndOwner(String username,String ownerOpenId){
		return fanDao.selectFanByNameAndOwner(username, ownerOpenId);
	}
	
	public int selectFansCountByOwner(String ownerOpenId){
		return fanDao.selectCountByOwner(ownerOpenId);
	}
	
	public void deleteFan(String username){
		Fan user = fanDao.selectFanByName(username);
		if(user!=null){
			messageService.deleteMessageByUserId(user.getOpenId());
			fanDao.deleteFan(username);
		}
	}
	public void deleteFanById(String openId){
		Fan user = fanDao.selectFanById(openId);
		if(user!=null){
			messageService.deleteMessageByUserId(user.getOpenId());
			fanDao.deleteFan(user.getUserName());
		}
	}
	public String getFanPoJsonString(int id){
		User user = userService.getUser(id);
		List<Fan> fansList = selectFansByOwner(user.getUserId(), 0, 100000);
		List<FanPo> fanPoList = new LinkedList<>();
		
		for (Fan fan : fansList) {
			FanPo fanPo = new FanPo(fan);
			fanPoList.add(fanPo);
		}
		FanPoList jsonList = new FanPoList();
		jsonList.setData(fanPoList);
		String a = JSONObject.toJSONString(jsonList);
		return a;
	}
}
