package com.cjy.WechatHome;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cjy.WechatHome.util.MessageUtil;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class TestWeb {
	@Test
	public void contextLoads() throws IOException {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        String url = "http://jy.dcchan.site/WechatHome/maxiaodai.do";
		//String url = "http://fortestwechat.free.ngrok.cc/WechatHome/maxiaodai.do";
		String toUserName = "gh_8f67b02309a8";
		String fromUserName = "gh_8f67b02309a8";
		File file = new File("output.txt");
		if(!file.exists()){
		    file.createNewFile();
        }
        PrintWriter writer =  new PrintWriter(file);
		for(int i=0;i<500;i++) {
            String b = String.valueOf(i);
            String a = MessageUtil.packText(toUserName, fromUserName,b);


            try {
                cachedThreadPool.execute(()->{
                    PostMethod post = new PostMethod(url);
                    post.setRequestBody(a);
                    long startTime = System.currentTimeMillis();
                    HttpClient client = new HttpClient();
                    try {
                        int status = client.executeMethod(post);
                        String content = post.getResponseBodyAsString();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                //String content = post.getResponseBodyAsString();
               // writer.write(content);
                System.out.println(i);

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            } finally {
                //post.releaseConnection();
            }


        }
//		long endTime = System.currentTimeMillis();
//		System.out.println((endTime-startTime)*0.001);

	}

}
