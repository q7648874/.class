package com.product.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.domain.Order;
import com.product.domain.Orderitem;
import com.product.domain.PageBean;
import com.product.domain.Product;
import com.product.domain.User;
import com.product.service.OrderService;
import com.product.service.entity.OrderServiceEntity;
import com.product.util.ProxyFactory;
import com.product.web.base.BaseServlet;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获得session对象 判断是否已经登录了
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("sess_user");
		if(user==null){
			return "/user/login.jsp";
		}
		//如果用户登录了取得购物车中的数据 经行order订单对象的创建
		Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute("cartMap");
		//对订单对象的属性赋值
		String oid = UUID.randomUUID().toString().replace("-", "");  	 //订单id
		String ordertime = new Date().toLocaleString();                  //创建订单的时间
		int state = 0;                                       			 //订单的状态
		String address =null;                                 			 //地址
		String name = null;                                    			 //被邮寄人的姓名
		String telephone =null;                               			 //被邮寄人的电话
		String uid = user.getUid();                                      //登录用户的id
		//必须通过购物车数据经行计算的值
		double total = 0;                                  	//总价 在购物车数据中计算
		List<Orderitem> orderitem = new ArrayList<>(0);  	//封装了多个订单项的集合  通过购物车数据计算
		//遍历cartMap 对上面的两个数据赋值  同时创建订单项orderitem对象
		for (Map.Entry<Product,Integer> entry : cartMap.entrySet()) {
			Product p = entry.getKey();		//该商品对象
			String itemid = UUID.randomUUID().toString().replace("-", "");	//主键自己表的id
			int count = entry.getValue();									//商品数量
			String pid = p.getPid();										//商品id
			Orderitem oi = new Orderitem(itemid, count, pid, oid,p);
			orderitem.add(oi);
			total += p.getShop_price()*count;
		}
		//赋值并创建订单对象 order
		Order order = new Order(oid, ordertime, total, state, address, name, telephone, uid, orderitem);
		//将order对象传入service层 对数据库经行操作
		OrderService os = (OrderService) ProxyFactory.getProxy(OrderServiceEntity.class);
		os.createOrder(order);
		session.setAttribute("order", order);
		return "/user/order_info.jsp";
	}
	//为order_list创建分页对象
	public String findOrderByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User sess_user = (User) req.getSession().getAttribute("sess_user");
		String uid = sess_user.getUid();
		
		int currentPage = 1;
		String cu = req.getParameter("currentPage");
		if(cu!=null){
			currentPage = Integer.parseInt(cu);
		}
		int pageSize = 2;
		
		PageBean pb = new OrderServiceEntity().findOrderByPage(uid,currentPage,pageSize);
		req.setAttribute("pb", pb);
		//写错了 页面要的是一个分页
//		List<Order> orderList = new OrderServiceEntity().findOrderByUid(uid);
//		req.setAttribute("orderList", orderList);
		return "/user/order_list.jsp";
	}
	
	//在order_list付款时传递oid
	public String payOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		OrderService os = new OrderServiceEntity();
		Order order = os.payOrderByOid(oid);
		req.getSession().setAttribute("order", order);
		return "/user/order_info.jsp";
	}
}
