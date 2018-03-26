package com.product.domain;

import java.util.List;

public class PageBean<T> {
	private int currentPage; //��ǰҳ        ��ҳ���ȡ
	private int pageSize;    //ÿҳ��ʾ������  �Զ���
	private int count;    	 //������  ���ݿ���
	private int totalPage; 	 //��ҳ��	���������������
	private List<T> list;	 //��ǰҳ���ݼ���  	���ݿ���
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
