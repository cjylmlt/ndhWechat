package com.cjy.WechatHome.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.DefinedReplyDao;
import com.cjy.WechatHome.wechat.model.DefinedReply;
@Service
public class DefinedReplyService {
	@Autowired
	DefinedReplyDao definedReplyDao;
	public DefinedReply getReply(String replyKey,String username) {
		DefinedReply definedReply = definedReplyDao.getDefinedReplyByKeyAndUser(replyKey,username);
		if(definedReply!=null)
			definedReply.setValue(definedReply.getValue().replaceAll("\\\\n","\n"));
//		if (definedReply!=null) {
//			System.out.println(definedReply.getValue());
//		}
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
	
	public void updateDefinedReply(int id,String replyKey){
		definedReplyDao.updateDefinedReply(id,replyKey);
	}
	public void updateAd(int id,String replyKey,String picUrl,String url){
		definedReplyDao.updateAd(id,replyKey,picUrl,url);
	}
}
