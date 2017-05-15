package com.cjy.myWeb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cjy.myWeb.mapper.ArticleMapper;
import com.cjy.myWeb.po.Article;
import com.cjy.myWeb.po.Page;
import com.cjy.myWeb.service.PageService;
import com.cjy.myWeb.utils.PageUtil;

@Controller
public class BlogListController {
	@Autowired
	private PageService pageService;
	@RequestMapping("/index/{authorId}/{pageNum}")
	public ModelAndView blogList(@PathVariable("authorId") String authorId,@PathVariable("pageNum") int pageNum){
		Page page = new Page();
		page.setPresentPage(pageNum);
		page.setLastPage(pageNum-1);
		page.setNextPage(pageNum+1);
		List<Article> records = pageService.findArticleByPageNum(authorId, pageNum);	
		page.setRecords(records);
		page.setTotalPage(pageService.getTotalPage(authorId));
		page.setStartPage(1);
		page.setEndPage(pageService.getTotalPage(authorId));
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		modelAndView.addObject("page",page);
		modelAndView.addObject("authorId",authorId);
		return modelAndView;
	}
	@RequestMapping("/index")
	public ModelAndView gotoMainBlog(){
		String authorId = "cjy";
		int pageNum = 1;
		return blogList(authorId, pageNum);
	}
}
