package com.cjy.myWeb.mapper;

import java.util.List;

import com.cjy.myWeb.po.Article;
import com.cjy.myWeb.po.User;

public interface ArticleMapper {
	public Article findArticleById(String id);
	public List<Article> findArticleListByTitle(String title);
	public List<Article> findArticleListByAuthor(String author);
	public int updateArticle(Article article);
	public void deleteArticle(Article article);
	public int addArticle(Article article);
	public List<Article> findArticleListByPage(String authorId,int start);
	public int countArticleByAuthorId(String id);
}
