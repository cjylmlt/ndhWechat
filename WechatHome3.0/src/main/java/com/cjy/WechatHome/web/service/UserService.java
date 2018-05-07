package com.cjy.WechatHome.web.service;

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
import com.cjy.WechatHome.util.WendaUtil;
import com.cjy.WechatHome.web.model.LoginTicket;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.wechat.model.DefinedReply;



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
			user.setHeadUrl("https://images.cjy.com/head/613m.png");
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
		String ticket =  addLoginTicket(Integer.toString(user.getId()));
		map.put("ticket", ticket);
		return map;
	}
	public Map<String, String> login(String username,String password,String rightCheckCode,String inputCheckCode){
		Map<String, String> map =  new HashMap<String,String>();
		if(StringUtils.isEmpty(username)){
			map.put("msg","用户名不能为空");
			return map;
		}
		if(StringUtils.isEmpty(password)){
			map.put("msg","密码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(inputCheckCode)){
			map.put("msg","验证码不能为空");
			return map;
		}
		if(!inputCheckCode.equals(rightCheckCode)){
			map.put("msg","验证码不正确");
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
		String ticket =  addLoginTicket(Integer.toString(user.getId()));
		map.put("ticket", ticket);
		return map;
	}
	
	public Map<String, String> changePassword(int userId,String oldPassword,String newPassword,String confirmPassword){
		Map<String, String> map =  new HashMap<String,String>();
		if(StringUtils.isEmpty(oldPassword)){
			map.put("msg","初始密码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(newPassword)){
			map.put("msg","新密码不能为空");
			return map;
		}
		if(StringUtils.isEmpty(confirmPassword)){
			map.put("msg","确认密码不能为空");
			return map;
		}
		if(!newPassword.equals(confirmPassword)){
			map.put("msg","两次输入密码不一致");
			return map;
		}
		User user = userDao.selectById(userId);
		
		if(user==null){
			map.put("msg","用户不存在");
			return map;
		}
		else if(!oldPassword.equals(user.getPassword())){
			
			map.put("msg","初始密码错误");
			return map;
		}
		else{
			user.setPassword(newPassword);
			userDao.updatePassword(user);
		}
		String ticket =  addLoginTicket(Integer.toString(user.getId()));
		map.put("ticket", ticket);
		return map;
	}
	
	public String addLoginTicket(String userId){
		LoginTicket loginTicket = new LoginTicket();
		loginTicket.setStatus(0);
		Date now = new Date();
		now.setTime(3600*1000*12+now.getTime());
		loginTicket.setExpired(now);
		loginTicket.setUserId(userId);
		loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		loginTicketDao.addTicket(loginTicket);
		return loginTicket.getTicket();
	}
	
	public void logout(String ticket){
		loginTicketDao.updateStatus(1,ticket);
	}
	
	public User getUserByUserId(String userId){
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
