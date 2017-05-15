package com.cjy.myWeb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cjy.myWeb.mapper.ArticleMapper;
import com.cjy.myWeb.po.Article;
import com.cjy.myWeb.service.PageService;

public class PageServiceImpl implements PageService {//一页十张
	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public List<Article> findArticleByPageNum(String authorId, int pageNum) {
		// TODO Auto-generated method stub
		int TotalPage = getTotalPage(authorId);
		if(pageNum>TotalPage)
			return null;
		else{
			int start = (pageNum-1)*10;
			List<Article> result= articleMapper.findArticleListByPage(authorId,start);
			return result;
		}
	}

	@Override
	public int getTotalPage(String authorId) {
		// TODO Auto-generated method stub
		double totalRecords = (double)articleMapper.countArticleByAuthorId(authorId);
		int totalPage = (int) Math.ceil(totalRecords/10.0);
		return totalPage;
	}

}
