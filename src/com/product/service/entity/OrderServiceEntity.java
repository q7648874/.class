package com.product.service.entity;

import java.sql.SQLException;
import java.util.List;

import com.product.dao.OrderDao;
import com.product.dao.entity.OrderDaoEntity;
import com.product.domain.Order;
import com.product.domain.Orderitem;
import com.product.domain.PageBean;
import com.product.service.OrderService;

public class OrderServiceEntity implements OrderService {

	@Override//创建订单表
	public void createOrder(Order order) {
		// TODO Auto-generated method stub
		OrderDao od = new OrderDaoEntity();
		List<Orderitem> orderitem = order.getOrderitem();
		try {
			od.inserOrder(order);
//			int a = 10/0;   //测试事物
			for (Orderitem oi : orderitem) {
				od.inserOrderitem(oi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("orderservice层有错误");
		}
	}
	
	@Override//根据User的id找到对应的订单表集合
	public List<Order> findOrderByUid(String uid) {
		// TODO Auto-generated method stub
		OrderDao od = new OrderDaoEntity();
		List<Order> list =null;
		try {
			list = od.findOrderByUid(uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@Override//直接根据User的id封装一个pagebean对象
	public PageBean findOrderByPage(String uid, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		OrderDao od = new OrderDaoEntity();
		PageBean<Order> pb = null;
		try {
			int count = od.findAllOrder(uid);
			int totalPage = (int) Math.ceil(count*1.0/pageSize);
			List<Order> list = od.findOrderPage(uid,currentPage,pageSize);
			pb = new PageBean<>(currentPage, pageSize, count, totalPage, list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pb;
	}
	
	@Override//根据订单表的id找到对应的订单表
	public Order payOrderByOid(String oid) {
		// TODO Auto-generated method stub
		OrderDao od = new OrderDaoEntity();
		Order o = null;
		try {
			o = od.payOrderByOid(oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	@Override
	public void update(Order o) {
		// TODO Auto-generated method stub
		OrderDao od = new OrderDaoEntity();
		try {
			od.update(o);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
