package com.cjy.myWeb.service;

import java.util.List;

import com.cjy.myWeb.po.Article;

public interface PageService {
	public List<Article> findArticleByPageNum(String authorId,int pageNum);
	public int getTotalPage(String authorId);
}
