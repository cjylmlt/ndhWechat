package com.cjy.WechatHome.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.ViewObject;
import com.cjy.WechatHome.service.RecordService;
import com.cjy.WechatHome.service.UserService;

@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	UserService userService;
	@Autowired
	RecordService recordService;
	@Autowired
	HostHolder hostHolder;
	@RequestMapping(path={"/","/index"},method = {RequestMethod.GET})
	public String index(Model model){
		model.addAttribute("topRecordList", recordService.selectTopRecord()); 
		if(hostHolder.getUser()!=null&&hostHolder.getUser().getUsername().equals("admin"))
			model.addAttribute("userList", userService.selectAll());
		return "index";
	}
//    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
//    public String userIndex(Model model, @PathVariable("userId") int userId) {
       // model.addAttribute("vos", getQuestions(userId, 0, 10));
//        return "index";
//    }
//	public List<ViewObject> getQuestions(int userId,int offset,int limit){
//		List<Question> questionList =	questionService.getLastestQuestions(userId, 0, 10);
//		List<ViewObject> vos = new ArrayList<>();
//		for(Question q:questionList){
//			ViewObject vo = new ViewObject();
//			vo.set("question", q);
//			vo.set("user", userService.getUser(q.getUserId()));
//			vos.add(vo);
//		}
//		return vos;
//	}
	
}
