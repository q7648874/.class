package com.product.service;

import java.util.List;

import com.product.domain.Order;
import com.product.domain.PageBean;

public interface OrderService {

	void createOrder(Order order);

	List<Order> findOrderByUid(String uid);

	PageBean findOrderByPage(String uid, int currentPage, int pageSize);

	Order payOrderByOid(String oid);

	void update(Order o);

}
