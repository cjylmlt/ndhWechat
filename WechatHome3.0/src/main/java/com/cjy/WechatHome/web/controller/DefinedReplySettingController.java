package com.cjy.WechatHome.web.controller;

import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjy.WechatHome.util.WendaUtil;
import com.cjy.WechatHome.web.model.HostHolder;
import com.cjy.WechatHome.web.model.User;
import com.cjy.WechatHome.web.service.DefinedReplyService;
import com.cjy.WechatHome.web.service.UserService;
import com.cjy.WechatHome.wechat.model.DefinedReply;

@Controller
public class DefinedReplySettingController {
	@Autowired
	HostHolder hostHolder;
	@Autowired
	UserService userService;
	@Autowired
	DefinedReplyService definedReplyService;
	@RequestMapping(path = {"/definedReply/addTextReply"}, method = {RequestMethod.POST})
	public String addTextReply(@RequestParam("id") int id,@RequestParam("key") String key, @RequestParam("content") String content,Model model,HttpServletRequest request){
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
	       definedReply.setReplyKey(key);
	       definedReply.setUserName(user.getUsername());
	       definedReply.setValue(content);
	       if(!definedReplyService.insertDefinedReply(definedReply,user.getUsername())){
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
	    }catch (Exception e) {
		  e.printStackTrace();
	    }
	    return "redirect:/user/"+user.getId();
    }
	
	@RequestMapping(path = {"/definedReply/addPicReply"}, method = {RequestMethod.POST})
	public String addPicReply(@RequestParam("id") int id,
			@RequestParam("key") String key, 
			@RequestParam("value") String value,
			@RequestParam("picUrl") String picUrl,
			@RequestParam("url") String url,
			Model model,HttpServletRequest request){
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
	       definedReply.setReplyKey(key);
	       definedReply.setUserName(user.getUsername());
	       definedReply.setValue(value);
	       definedReply.setPicUrl(picUrl);
	       definedReply.setUrl(url);
	       if(!definedReplyService.insertDefinedReply(definedReply,user.getUsername())){
	    	   System.out.println("aa");
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
	    }catch (Exception e) {
		  e.printStackTrace();
	    }
	    return "redirect:/user/"+user.getId();
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

	@RequestMapping(path = {"/definedReply/update"}, method = {RequestMethod.POST})
	public String updateDefinedReply(@RequestParam("definedReplyId")int definedReplyid,@RequestParam("userId")int userId,@RequestParam("replyValue")String replyValue,Model model){
		User visitUser = hostHolder.getUser();
		User user = userService.getUser(userId);
	    DefinedReply ad = null;
	    List<DefinedReply> definedReplyList ;
	    if(visitUser==null){
	    	 return "redirect:/relogin";
	    }
	    //如果是超级用户或者是用户本人
	    if("admin".equals(visitUser.getUsername())||visitUser.getId()==user.getId()){
	       definedReplyService.updateDefinedReply(definedReplyid,replyValue);
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
	
	@RequestMapping(path = {"/definedReply/updateAd"}, method = {RequestMethod.POST})
	public String updateAd(@RequestParam("adId")int adId,
			@RequestParam("userId")int userId,
			@RequestParam("adValue")String adValue,
			@RequestParam("adPicUrl")String adPicUrl,
			@RequestParam("adUrl")String adUrl,
			Model model){
		User visitUser = hostHolder.getUser();
		User user = userService.getUser(userId);
	    DefinedReply ad = null;
	    List<DefinedReply> definedReplyList ;
	    if(visitUser==null){
	    	 return "redirect:/relogin";
	    }
	    //如果是超级用户或者是用户本人
	    if("admin".equals(visitUser.getUsername())||visitUser.getId()==user.getId()){
	       definedReplyService.updateAd(adId,adValue,adPicUrl,adUrl);
	       definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
		   model.addAttribute("settingUser", user);
		   
	    }
	    else{
		   user = visitUser;
		   definedReplyList = definedReplyService.getReplyByUser(user.getUsername());
		   model.addAttribute("settingUser", null);
	    }
	    ad = getFirstAd(user,(x)->{
			List<DefinedReply> adList = definedReplyService.getADList(x.getUsername());
			if(adList.size()>0){
				return adList.get(0);
			}
			else
				return null;
		});
	    model.addAttribute("ad",ad);
	    model.addAttribute("definedReplyList", definedReplyList);
	    return "redirect:/user/"+String.valueOf(user.getId());
	}
	public static DefinedReply getFirstAd(User user, Function<User,DefinedReply> func){
		return func.apply(user);
	}
}
