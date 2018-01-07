package com.cjy.WechatHome.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cjy.WechatHome.async.EventHandler;
import com.cjy.WechatHome.async.EventModel;
import com.cjy.WechatHome.async.EventType;
import com.cjy.WechatHome.spider.VideoSpider;
import com.cjy.WechatHome.wechat.model.News;
import com.cjy.WechatHome.wechat.model.NewsPo;
import com.cjy.WechatHome.wechat.service.NewsService;
@Component
public class VideoSpiderHandler implements EventHandler{
	@Autowired
	VideoSpider videoSpider;
	@Autowired
	NewsService newsService;
	@Override
	public void doHandle(EventModel model) {
//		ExecutorService cachedThreadPool = Executors.newCachedThreadPool(); 
//		cachedThreadPool.execute(new Runnable(){  
//			public void run(){
//				String content = model.getExt("content");
//				List<News> newsList = videoSpider.getVideoMessage(model.getExt("urlContent"));	
//				try {
//					newsService.deleteNewsByKey(content);
//					for(News n:newsList){
//						NewsPo newsPo = new NewsPo();
//						newsPo.setDescription(n.getDescription());
//						newsPo.setKey(content);
//						newsPo.setPicUrl(n.getPicUrl());
//						newsPo.setTitle(n.getTitle());
//						newsPo.setUpdateTime(new Date());
//						newsPo.setUrl(n.getUrl());
//						newsService.insertNews(newsPo);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("done");
//			}
//		});
		List<News> newsList;
		String content = model.getExt("content");
		String type = model.getExt("type");
		if("old".equals(type)){
			newsList = videoSpider.getVideoMessage(model.getExt("urlContent"));	
			newsService.deleteNewsByKey(content);
		}
		else{
			newsList = videoSpider.newGetVideoMessage(model.getExt("urlContent"));	
			newsService.newDeleteNewsByKey(content);
		}
		newsService.deleteNewsByKey(content);
		for(News n:newsList){
			NewsPo newsPo = new NewsPo();
			newsPo.setDescription(n.getDescription());
			newsPo.setKey(content);
			newsPo.setPicUrl(n.getPicUrl());
			newsPo.setTitle(n.getTitle());
			newsPo.setUpdateTime(new Date());
			newsPo.setUrl(n.getUrl());
			if("old".equals(type)){
				
				newsService.insertNews(newsPo);
			}
			else if("new".equals(type)){
				
				newsService.newInsertNews(newsPo);
			}
		}
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		  return Arrays.asList(EventType.VIDEOSPIDER);
	}

}
