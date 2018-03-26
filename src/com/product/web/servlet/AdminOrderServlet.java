package com.product.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.domain.Order;
import com.product.domain.PageBean;
import com.product.domain.Product;
import com.product.service.OrderService;
import com.product.service.ProductService;
import com.product.service.entity.OrderServiceEntity;
import com.product.service.entity.ProductServiceEntity;
import com.product.web.base.BaseServlet;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public void findByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int currentPage = 1;
		int pageSize = 6;
		//接收页码
		String curr = req.getParameter("page");
		if(curr!=null){
			currentPage = Integer.parseInt(curr);
		}
		//接收每页显示条数
		String pSize = req.getParameter("rows");
		if(pSize!=null){
			pageSize = Integer.parseInt(pSize);
		}
		OrderService ps = new OrderServiceEntity();
		PageBean<Order> pb = ps.findOrderByPage(null, currentPage, pageSize);
		
		//{"total":51,"rows":[{pid:"1001",pname:"iphonex"}，{pid:"1002",pname:"华为"}]}
		
		Map<String, Object> map = new HashMap<>();
		map.put("total", pb.getCount());
		map.put("rows", pb.getList());
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"cid","market_price","pdate","pdesc","pflag"});
		String json = JSONObject.fromObject(map, jsonConfig ).toString();
//		System.out.println(json);
		resp.getWriter().write(json);
	}

}
