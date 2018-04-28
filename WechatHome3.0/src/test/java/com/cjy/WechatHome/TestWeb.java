package com.cjy.WechatHome;


import com.cjy.WechatHome.web.service.DefinedReplyService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cjy.WechatHome.util.MessageUtil;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@SpringBootTest
public class TestWeb {
	@Test
	public void contextLoads() throws IOException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        String url = "http://127.0.0.1:8080/WechatHome/maxiaodai.do";
		//String url = "http://fortestwechat.free.ngrok.cc/WechatHome/maxiaodai.do";
		String toUserName = "gh_8f67b02309a8";
		String fromUserName = "gh_8f67b02309a8";
		for(int i=0;i<1;i++) {
            String b = String.valueOf(i);
            String a = MessageUtil.packText(toUserName, fromUserName,"4");
            PostMethod post = new PostMethod(url);
            post.setRequestBody(a);
            HttpClient client = new HttpClient();
            try {
                int status = client.executeMethod(post);
                String content = post.getResponseBodyAsString();
                System.out.println(content);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

	}

}
