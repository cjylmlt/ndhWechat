package com.cjy.WechatHome.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cjy.WechatHome.dao.DefinedReplyDao;
import com.cjy.WechatHome.dao.LoginTicketDao;
import com.cjy.WechatHome.dao.UserDao;
import com.cjy.WechatHome.model.DefinedReply;
import com.cjy.WechatHome.model.LoginTicket;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.util.WendaUtil;



@Service
public class UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	LoginTicketDao loginTicketDao;
	@Autowired
	DefinedReplyDao definedReplyDao;
	public User getUser(int id){
		return userDao.selectById(id);
	}
	public Map<String, String> register(String username,String password,String wechatId){
		Map<String, String> map =  new HashMap<String,String>();
		if(StringUtils.isEmpty(username)){
			map.put("msg","用户名不能为空");
			return map;
		}
		if(StringUtils.isEmpty(password)){
			map.put("msg","密码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(wechatId)){
			map.put("msg","公众号id不能为空");
			return map;
		}
		User user = userDao.selectByName(username);
		if(user!=null){
			map.put("msg","用户名已经注册");
			return map;
		}
		else{
			user = new User();
			user.setUsername(username);
			//user.setSalt(UUID.randomUUID().toString().substring(0, 5));
			//user.setHeadUrl(String.format("http://images.cjy.com/head/%dt.png", new Random().nextInt(1000)));
			user.setPassword(password);
			user.setHeadUrl("https://images.nowcoder.com/head/613m.png");
			user.setUserId(wechatId);
			userDao.addUser(user); 
			user = userDao.selectByName(username);
			DefinedReply definedReply = new DefinedReply();
			definedReply.setReplyKey("新关注的回复");
			definedReply.setUserName(user.getUsername());
			definedReplyDao.insertDefinedReply(definedReply);
			definedReply.setReplyKey("搜索不到的回复");
			definedReplyDao.insertDefinedReply(definedReply);
			definedReply.setReplyKey("广告");
			//definedReply.setValue("预留广告位");
			definedReplyDao.insertDefinedReply(definedReply);
		}
		String ticket =  addLoginTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	public Map<String, String> login(String username,String password){
		Map<String, String> map =  new HashMap<String,String>();
		if(StringUtils.isEmpty(username)){
			map.put("msg","用户名不能为空");
			return map;
		}
		if(StringUtils.isEmpty(password)){
			map.put("msg","密码不能为空");
			return map;
		}
		User user = userDao.selectByName(username);
		if(user==null){
			map.put("msg","用户名不存在");
			return map;
		}

		else if(!(password).equals(user.getPassword())){
			map.put("msg", "密码错误");
			return map;
		}
		String ticket =  addLoginTicket(user.getId());
		map.put("ticket", ticket);
		return map;
	}
	
	public String addLoginTicket(int userId){
		LoginTicket loginTicket = new LoginTicket();
		loginTicket.setStatus(0);
		Date now = new Date();
		now.setTime(3600*1000+now.getTime());
		loginTicket.setExpired(now);
		loginTicket.setUserId(userId);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDao.addTicket(loginTicket);
		return loginTicket.getTicket();
	}
	
	public void logout(String ticket){
		loginTicketDao.updateStatus(1,ticket);
	}
	
	public User getUserByUserId(String userId) throws IOException{
		User user =userDao.selectUserByUserId(userId);
		if(user!=null)
			return user;
		else
			return null;
	}
	public List<User> selectAll(){
		return userDao.selectAll();
	}
	public void updateUser(User user){
		userDao.updateUser(user);
	}
}
