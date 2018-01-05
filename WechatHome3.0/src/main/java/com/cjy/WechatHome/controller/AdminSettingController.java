package com.cjy.WechatHome.controller;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.WechatHome.dao.AdminSettingDao;
import com.cjy.WechatHome.dao.NewsDao;
import com.cjy.WechatHome.model.HostHolder;
import com.cjy.WechatHome.model.User;
import com.cjy.WechatHome.model.WechatStatics;
import com.cjy.WechatHome.model.WechatUser;
import com.cjy.WechatHome.service.SpiderWebService;
import com.cjy.WechatHome.service.WechatStaticsService;
import com.cjy.WechatHome.service.WechatUserService;

@Controller
public class AdminSettingController {
	@Autowired
	AdminSettingDao adminSettingDao;
	@Autowired
	HomeController homeController;
	@Autowired
	WechatStaticsService wechatStaticsService;
	@Autowired
	HostHolder hostHolder;
	@Autowired
	SpiderWebService spiderWebService;
	@Autowired
	WechatUserService wechatUserService;
	@Autowired
	NewsDao newsDao;
	private static final Logger logger = LoggerFactory.getLogger(AdminSettingController.class);
	@RequestMapping(path={"/adminSetting"},method = {RequestMethod.POST})
	public String changeHostWeb(@RequestParam("hostWeb")String hostWeb,Model model){
		adminSettingDao.updateAdminSetting("hostWeb", hostWeb);
		return "redirect:/";
	}
	@RequestMapping(path={"/adminSetting/changeWebUrl"},method = {RequestMethod.POST})
	public String changeWebUrl(@RequestParam("webUrl")String webUrl,Model model){
		int id = Integer.valueOf(adminSettingDao.selectAdminSetting("hostWeb"));
		spiderWebService.updateWebUrl(webUrl, id);
		newsDao.deleteNewsAll();
		return "redirect:/";
	}
	@RequestMapping(path={"/wechatStatics"},method = {RequestMethod.POST,RequestMethod.GET})
	public String wechatStatics(Model model){
		User visitUser = hostHolder.getUser();
		if(visitUser.getUsername().equals("admin")){
			model.addAttribute("wechatStaticsList",wechatStaticsService.selectFirstPage());
			model.addAttribute("startDate",wechatStaticsService.selectStartDate());
			model.addAttribute("endDate",wechatStaticsService.selectEndDate());
		}
		return "wechat";
	}
	@RequestMapping(path={"/adminSetting/addWechatStatics"},method = {RequestMethod.POST})
	public String addStatics(Model model,@RequestParam("statics")int statics){
		Random random = new Random();
		int newUser = statics;
		int cancelUser = (int) (random.nextInt(statics)*0.09+statics*0.09);
	    int netgainUser = newUser - cancelUser;
		int cumulateUser = wechatStaticsService.selectFirstPage().get(0).getCumulateUser()+netgainUser;
		WechatStatics wechatStatics = new WechatStatics();
		wechatStatics.setCancelUser(cancelUser);
		wechatStatics.setCumulateUser(cumulateUser);
		wechatStatics.setDate(new Date(wechatStaticsService.selectEndDate().getTime()+24*3600*1000));
		wechatStatics.setNetgainUser(netgainUser);
		wechatStatics.setNewUser(newUser);
		wechatStaticsService.insertStatics(wechatStatics);
		return "redirect:/wechatStatics";
	}
	@RequestMapping(path={"/search"},method = {RequestMethod.POST,RequestMethod.GET})
	public String searchWechatUser(Model model,@RequestParam("q")String username){
		User user = hostHolder.getUser();
		model.addAttribute("user",user);
		WechatUser wechatUser = wechatUserService.selectWechatUserByName(username);
		model.addAttribute("wechatUser", wechatUser);
		return "wechatUser";
	}
	@RequestMapping(path={"/wechatUser/delete"},method = {RequestMethod.POST,RequestMethod.GET})
	public String deleteWechatUser(Model model,@RequestParam("userName")String username){
		wechatUserService.deleteWechatUser(username);
		return "redirect:/search?q="+username;
	}
	@RequestMapping(path={"/upload/image"},method = {RequestMethod.POST,RequestMethod.GET})
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("userId")String userId,
			@RequestParam("fileName")String fileName,
			@RequestParam("id")String id,
			HttpServletRequest request,
			Model model) throws IOException {  
		fileName = fileName+".jpg";
        System.out.println("开始");  
        String path="C:\\uploadImages\\"+userId+"\\";
        File targetDir = new File(path); 
        if(!targetDir.exists()){
        	targetDir.mkdirs();
        }
       // System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.createNewFile();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return "redirect:/user/"+id;  
    }  
//	@RequestMapping(path={"/images/upload"},method = {RequestMethod.POST,RequestMethod.GET})
//	public String getFile(Model model){
//	    String filePath = "C:\\updateImages\\abc\\qrcode.jpg";//绝对路径
//	    Resource resource = new FileSystemResource(filePath);
//	    try {
//			File file = resource.getFile();
//			model.addAttribute("file",file);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} //获取file对象
//	    return "test";
//	}
	@RequestMapping(path={"/uploadImages/{userId}/{fileName}"},method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView download(HttpServletRequest request, 
    		HttpServletResponse response,
    		@PathVariable("userId")String userId,  
    		@PathVariable("fileName")String fileName)  
            throws Exception {  
        response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("UTF-8");  
        java.io.BufferedInputStream bis = null;  
        java.io.BufferedOutputStream bos = null;  
        String downLoadPath = "C:\\uploadImages\\"+userId+"\\"+fileName+".jpg"; 
       // System.out.println(downLoadPath);  
        File file = new File(downLoadPath);
        if(!file.exists())
        	return null;
        try {  
            long fileLength = new File(downLoadPath).length();  
            response.setContentType("application/x-msdownload;");  
            response.setHeader("Content-disposition", "attachment; filename="  
                    + new String("qrcode.jpg".getBytes("utf-8"), "ISO8859-1"));  
            response.setHeader("Content-Length", String.valueOf(fileLength));  
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
            bos = new BufferedOutputStream(response.getOutputStream());  
            byte[] buff = new byte[2048];  
            int bytesRead;  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
                bos.write(buff, 0, bytesRead);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bis != null)  
                bis.close();  
            if (bos != null)  
                bos.close();  
        }  
        return null;  
    }  
	
}
