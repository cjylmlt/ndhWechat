package com.cjy.WechatHome.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjy.WechatHome.dao.NewsDao;
import com.cjy.WechatHome.dao.RecordDao;

import com.cjy.WechatHome.model.News;
import com.cjy.WechatHome.model.NewsPo;
import com.cjy.WechatHome.model.Record;

@Service
public class NewsService{
	@Autowired
	NewsDao newsDao;
	
	public void insertNews(NewsPo newsPo) throws IOException{
		newsDao.insertNews(newsPo);
	}
	public List<News> selectNewsByKey(String key) throws IOException{
		List<News> newsList = newsDao.selectNewsByKey(key);
		return newsList;
	}
	public List<NewsPo> selectNewsPoByKey(String key) throws IOException{
		List<NewsPo> newsPoList = newsDao.selectNewsPoByKey(key);
		return newsPoList;
	}
	public void deleteNewsByKey(String key) throws IOException{
		newsDao.deleteNewsByKey(key);
	}
}
