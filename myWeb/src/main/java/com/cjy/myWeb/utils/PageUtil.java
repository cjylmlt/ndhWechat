package com.cjy.myWeb.utils;

import java.util.List;

public class PageUtil {

	private List<?> records;//查询到的信息列表
	private int currentPageNum;//当前页数
	private int totalRecords;//总共信息数量
	private int totalPage;//总页数
	private int pageRecords = 10;//每页显示数量
	private int startIndex;//每页开始的索引
	private int lastPageNum;//上一页
	private int nextPageNum;//下一页
	private int startPageNum;//开始的页数
	private int endPageNum;//结束的页数
	private String uri;//需要引用分页的页面地址
	
	public PageUtil(int currentPageNum,int totalRecords) {
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;
		totalPage = totalRecords%pageRecords == 0?totalRecords/pageRecords:totalRecords/pageRecords + 1;
		startIndex = (currentPageNum - 1)*pageRecords;
		
		if(totalPage > 5){
			endPageNum = currentPageNum + 2;
			startPageNum = currentPageNum - 2;
			if(startPageNum < 1){
				startPageNum = 1;
				endPageNum = 5;
			}else if(endPageNum > totalPage){
				endPageNum = totalPage;
				startPageNum = totalPage - 4;
			}
		}else{
			startPageNum = 1;
			endPageNum = totalPage;
		}
	}

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageRecords() {
		return pageRecords;
	}

	public void setPageRecords(int pageRecords) {
		this.pageRecords = pageRecords;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getLastPageNum() {
		lastPageNum = currentPageNum - 1;
		if(lastPageNum < 1){
			return 1;
		}
		return lastPageNum;
	}

	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public int getNextPageNum() {
		nextPageNum = currentPageNum + 1;
		if(nextPageNum > totalPage){
			return totalPage;
		}
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getStartPageNum() {
		return startPageNum;
	}

	public void setStartPageNum(int startPageNum) {
		this.startPageNum = startPageNum;
	}

	public int getEndPageNum() {
		return endPageNum;
	}

	public void setEndPageNum(int endPageNum) {
		this.endPageNum = endPageNum;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
