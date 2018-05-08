package com.cjy.WechatHome.wechat.service;

import com.cjy.WechatHome.dao.NewsDao;
import com.cjy.wechathome.data.wechat.News;
import com.cjy.wechathome.data.wechat.NewsPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService{
	@Autowired
	NewsDao newsDao;
	
	public void insertNews(NewsPo newsPo) {
		newsDao.insertNews(newsPo);
	}
	public List<News> selectNewsByKey(String key) {
		List<News> newsList = newsDao.selectNewsByKey(key);
		return newsList;
	}
	public List<NewsPo> selectNewsPoByKey(String key){
		List<NewsPo> newsPoList = newsDao.selectNewsPoByKey(key);
		return newsPoList;
	}
	public void deleteNewsByKey(String key){
		newsDao.deleteNewsByKey(key);
	}
	public void deleteNewsAll(){
		newsDao.deleteNewsAll();
	}
	
	
	public void newInsertNews(NewsPo newsPo){
		newsDao.newInsertNews(newsPo);
	}
	public List<News> newSelectNewsByKey(String key){
		List<News> newsList = newsDao.newSelectNewsByKey(key);
		return newsList;
	}
	public List<NewsPo> newSelectNewsPoByKey(String key){
		List<NewsPo> newsPoList = newsDao.newSelectNewsPoByKey(key);
		return newsPoList;
	}
	public void newDeleteNewsByKey(String key){
		newsDao.newDeleteNewsByKey(key);
	}
}
