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

import com.cjy.WechatHome.dao.DefinedReplyDao;
import com.cjy.WechatHome.dao.UserDao;
import com.cjy.WechatHome.model.DefinedReply;
import com.cjy.WechatHome.model.User;
@Service
public class DefinedReplyService {
	@Autowired
	DefinedReplyDao definedReplyDao;
	public DefinedReply getReply(String replyKey,String username) {
		DefinedReply definedReply = definedReplyDao.getDefinedReplyByKeyAndUser(replyKey,username);
		if (definedReply!=null) {
			System.out.println(definedReply.getValue());
		}
		return definedReply;
	}
	public List<DefinedReply> getReplyByUser(String username) {
		List<DefinedReply> definedReplyList = definedReplyDao.getDefinedReplyByUser(username);
		return definedReplyList;
	}
	public List<DefinedReply> getADList(String username) {
		List<DefinedReply> adList = definedReplyDao.getAdByUser(username);
		if (adList!=null) {
			
		}
		return adList;
	}
	public boolean insertDefinedReply(DefinedReply definedReply,String username){
		if(definedReplyDao.getDefinedReplyByKeyAndUser(definedReply.getReplyKey(),username)==null){
			definedReplyDao.insertDefinedReply(definedReply);
			return true;
		}
		else{
			return false;
		}
	}
	public void deleteDefinedReply(int id){
		definedReplyDao.deleteDefinedReply(id);
	}
}
