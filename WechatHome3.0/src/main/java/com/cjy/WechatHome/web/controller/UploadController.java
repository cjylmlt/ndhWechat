package com.cjy.WechatHome.web.controller;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping(path={"/upload/image"},method = {RequestMethod.POST,RequestMethod.GET})
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam("userId")String userId,
			@RequestParam("fileName")String fileName,
			@RequestParam("id")String id,
			HttpServletRequest request,
			Model model) throws IOException {  
		fileName = fileName+".jpg";
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
