package com.cjy.myWeb.controller;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.myWeb.mapper.ArticleMapper;
import com.cjy.myWeb.po.Article;
import com.mysql.fabric.Response;
@Controller
public class ArticleController {
	@Autowired
	private ArticleMapper articleMapper;
	@RequestMapping("/articlePage/{articleId}")
	public ModelAndView articleDetail(@PathVariable("articleId") String articleId){
		ModelAndView modelAndView = new ModelAndView();
		Article article = articleMapper.findArticleById(articleId);
		modelAndView.setViewName("articlePage");
		modelAndView.addObject("article", article);
		return modelAndView;
	}
	@RequestMapping("/writeArticle")
	public ModelAndView writeArticle(HttpSession session){
		ModelAndView modelAndView = new ModelAndView();
		if(session.getAttribute("userId")==null){
			modelAndView.setViewName("login");
		}
		else{
			modelAndView.setViewName("markdown");
		}
		return modelAndView;
	}
	@RequestMapping("/saveArticle")
	public @ResponseBody Article saveArticle(Article article,HttpSession session){
		article.setId(String.valueOf((int)(Math.random()*1000000)));
		article.setAuthorId((String)session.getAttribute("userId"));
		article.setDate(new Date());
		articleMapper.addArticle(article);
		return article;
	}
}
