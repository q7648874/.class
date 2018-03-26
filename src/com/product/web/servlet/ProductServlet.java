package com.product.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.domain.PageBean;
import com.product.domain.Product;
import com.product.service.ProductService;
import com.product.service.entity.ProductServiceEntity;
import com.product.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	static int pageSize = 6;
	//封装页面bean数据
	public String findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("cid");
		String cu = req.getParameter("currentPage");
		int currentPage = cu==null?1:Integer.parseInt(cu);
		ProductService ps = new ProductServiceEntity();
		PageBean<Product> pb = ps.findByPage(cid,currentPage,pageSize);
		req.setAttribute("pb", pb);
		req.setAttribute("cid", cid);
		return "/user/product_list.jsp";
	}
	//查看商品详细信息
	public String findProductById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");
		ProductService ps = new ProductServiceEntity();
		Product p = ps.findProductById(pid);
		req.setAttribute("p", p);
		
		//########创建浏览记录#########
		String historyId =getProductId(pid,req);
		Cookie cook = new Cookie("history", historyId);
		cook.setPath(req.getContextPath());
		cook.setMaxAge(-1);
		resp.addCookie(cook);
		return "/user/product_info.jsp";
	}
	private String getProductId(String pid, HttpServletRequest req) {
		// TODO Auto-generated method stub
		Cookie[] cookies = req.getCookies();
		if(cookies==null){
			return pid;
		}
		Cookie historyCookie = null;
		for (Cookie cookie : cookies) {
			if("history".equals(cookie.getName())){
				historyCookie = cookie;
				break;
			}
		}
		if(historyCookie==null){
			return pid;
		}
		String[] values = historyCookie.getValue().split("-");
		LinkedList<String> llist = new LinkedList<>(Arrays.asList(values));//将切割后的id数组转成list集合放入LinkedList中
		//如果出现了pid 就移除  移除后直接添加pid到第一个
		if(llist.contains(pid)){
			llist.remove(pid);
		}
		if(llist.size()>=3){//如果没出现且个数到达三个了  直接移除最后一个
			llist.removeLast();
		}
		llist.addFirst(pid);
		
		String historyId = "";
		for (int i = 0; i < llist.size(); i++) {
			if(i>0){
				historyId += "-";
			}
			historyId += llist.get(i);
		}
		return historyId;
	}
	//查询最热最新商品
	public String findProducts(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductService ps = new ProductServiceEntity();
		List<Product> hotlist = ps.findhotProduct();
		List<Product> newlist = ps.findnewProduct();
		req.setAttribute("hotList", hotlist);
		req.setAttribute("newList", newlist);
		return "/user/index.jsp";
	}
	
	

    

}
