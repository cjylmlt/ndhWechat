package com.cjy.WechatHome;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cjy.WechatHome.util.MessageUtil;

@SpringBootTest
public class TestWeb {
	@Test
	public void contextLoads() {
		String url = "http://fortestwechat.free.ngrok.cc/WechatHome/maxiaodai.do";
		//String url = "http://fortestwechat.free.ngrok.cc/WechatHome/maxiaodai.do";
		String content="";
		String toUserName = "gh_d0b487277d65";
		String fromUserName = "gh_d0b487277d65";
		String b = "a";
		String a = MessageUtil.packText(toUserName, fromUserName,b);
		PostMethod post = new PostMethod(url);
		post.setRequestBody(a);
		long startTime = System.currentTimeMillis();
		HttpClient client = new HttpClient();
		
		for(int i=0; i<300; i++){
			try {
				int status = client.executeMethod(post);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			} finally {
				//post.releaseConnection();
			}
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println((endTime-startTime)*0.001);
		
	}

}
