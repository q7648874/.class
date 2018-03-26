package com.product.domain;

import java.util.List;

public class PageBean<T> {
	private int currentPage; //当前页        从页面获取
	private int pageSize;    //每页显示的条数  自定义
	private int count;    	 //总条数  数据库查出
	private int totalPage; 	 //总页数	根据上面数据算出
	private List<T> list;	 //当前页数据集合  	数据库查出
	public int getCurrentPage() {
		return currentPage;
	}
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageBean(int currentPage, int pageSize, int count, int totalPage, List<T> list) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.count = count;
		this.totalPage = totalPage;
		this.list = list;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [currentPage=" + currentPage + ", pageSize=" + pageSize + ", count=" + count + ", totalPage="
				+ totalPage + ", list=" + list + "]";
	}
	
}
