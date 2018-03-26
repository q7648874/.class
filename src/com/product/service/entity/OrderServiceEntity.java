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

	@Override//����������
	public void createOrder(Order order) {
		// TODO Auto-generated method stub
		OrderDao od = new OrderDaoEntity();
		List<Orderitem> orderitem = order.getOrderitem();
		try {
			od.inserOrder(order);
//			int a = 10/0;   //��������
			for (Orderitem oi : orderitem) {
				od.inserOrderitem(oi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("orderservice���д���");
		}
	}
	
	@Override//����User��id�ҵ���Ӧ�Ķ�������
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
	
	@Override//ֱ�Ӹ���User��id��װһ��pagebean����
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
	
	@Override//���ݶ������id�ҵ���Ӧ�Ķ�����
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
