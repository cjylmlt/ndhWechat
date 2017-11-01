package com.cjy.WechatHome.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.WechatHome.model.DefinedReply;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.News;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.service.DefinedReplyService;
import com.cjy.WechatHome.service.UserService;
import com.cjy.WechatHome.util.WendaUtil;

@Controller
public class DefinedReplySettingController {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	DefinedReplyService definedReplyService;
	@RequestMapping(path = {"/definedReply/add"}, method = {RequestMethod.POST})
	@ResponseBody
	public String addDefinedReply(@RequestParam("id") int id,@RequestParam("title") String title, @RequestParam("content") String content,Model model,HttpServletRequest request){
		User visitUser = hostHolder.getUser();
		User user = userService.getUser(id);
	    DefinedReply ad = null;
	    List<DefinedReply> definedReplyList ;
	    if(visitUser==null){
	    	 return "redirect:/relogin";
	    }
	    //如果是超级用户或者是用户本人
	    if("admin".equals(visitUser.getUsername())||visitUser.getId()==user.getId()){
	       DefinedReply definedReply = new DefinedReply();
	       definedReply.setReplyKey(title);
	       definedReply.setUserName(user.getUsername());
	       definedReply.setValue(content);
	       if(!definedReplyService.insertDefinedReply(definedReply,user.getUsername())){
	    	   return WendaUtil.getJSONString(1,"失败");
	       }
	       definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
		   model.addAttribute("settingUser", user);
		   
	    }
	    else{
		   user = visitUser;
		   definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
		   model.addAttribute("user", null);
	    }
	    try {
		  List<DefinedReply> adList = definedReplyService.getADList(user.getUsername());
		  if(adList.size()>0){
			ad = adList.get(0);
		  }
		  model.addAttribute("ad",ad);
		  model.addAttribute("definedReplyList", definedReplyList);
		  return WendaUtil.getJSONString(0);
	    }catch (Exception e) {
		  e.printStackTrace();
		  return WendaUtil.getJSONString(1,"失败");
	    }
	    
    }
	@RequestMapping(path = {"/definedReply/delete"}, method = {RequestMethod.POST})
	public String deleteDefinedReply(@RequestParam("definedReplyId")int definedReplyid,@RequestParam("userId")int userId,Model model){
		User visitUser = hostHolder.getUser();
		User user = userService.getUser(userId);
	    DefinedReply ad = null;
	    List<DefinedReply> definedReplyList ;
	    if(visitUser==null){
	    	 return "redirect:/relogin";
	    }
	    //如果是超级用户或者是用户本人
	    if("admin".equals(visitUser.getUsername())||visitUser.getId()==user.getId()){
	       definedReplyService.deleteDefinedReply(definedReplyid);
	       definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
		   model.addAttribute("settingUser", user);
		   
	    }
	    else{
		   user = visitUser;
		   definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
		   model.addAttribute("settingUser", null);
	    }
	    try {
		  List<DefinedReply> adList = definedReplyService.getADList(user.getUsername());
		  if(adList.size()>0){
			ad = adList.get(0);
		  }
		  model.addAttribute("ad",ad);
		  model.addAttribute("definedReplyList", definedReplyList);
	    }catch (Exception e) {
		  e.printStackTrace();
		  
	    }
	    return "redirect:/user/"+String.valueOf(user.getId());
	}

}
