package com.product.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.domain.Product;
import com.product.service.entity.ProductServiceEntity;
import com.product.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String addCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取请求参数   pid
		String pid = req.getParameter("pid");
		//处理业务逻辑  	根据pid查出商品	从session域中获得购物车(如果没有就创建一个)	处理购物车中的商品数量	将商品添加到购物车	  将购物车放入session域中
		Product p = new ProductServiceEntity().findProductById(pid);
		HttpSession session = req.getSession();
		//从session域中获得购物车
		Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute("cartMap");
		if(cartMap==null){
			cartMap = new HashMap<>();
		}
		//处理购物车中的商品数量  如果有商品相同将hashmap中value数量加1 否则设置value值为1
		String snum = req.getParameter("num");
		if(snum==null){
			return null;
		}
		int num = Integer.parseInt(snum);//从product_info.jsp页面获得具体数字经行累加
		if(cartMap.containsKey(p)){
			num += cartMap.get(p);
			if(req.getParameter("change")!=null){//判断是否通过cart.jsp页面的ajax方式传过来的数据
				num = Integer.parseInt(req.getParameter("num"));
			}
		}
		//将商品添加入购物车
		cartMap.put(p, num);
		//将购物车放入session域中
		session.setAttribute("cartMap", cartMap);
		//分发转向  使用重定向 避免重复提交  也可以使用转发 但是必须使用令牌机制在提交数据中设置一个随机令牌经行比较  令牌不同后再经行业务逻辑
		resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
		session.removeAttribute("cartMap");
		return null;
	}

	public String delByPid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");
		Product p = new ProductServiceEntity().findProductById(pid);
		Map<Product, Integer> cartMap = (Map<Product, Integer>) req.getSession().getAttribute("cartMap");
		cartMap.remove(p);
		resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
		return null;
	}
	public String clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = "cartMap";
		req.getSession().removeAttribute(name);
		resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
		return null;
	}
}
