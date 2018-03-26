package com.product.dao;

import java.sql.SQLException;
import java.util.List;

import com.product.domain.Order;
import com.product.domain.Orderitem;

public interface OrderDao {

	void inserOrder(Order order) throws SQLException;

	void inserOrderitem(Orderitem oi) throws SQLException;

	List<Order> findOrderByUid(String uid) throws SQLException;

	int findAllOrder(String uid) throws SQLException;

	List<Order> findOrderPage(String uid, int currentPage, int pageSize) throws SQLException;

	Order payOrderByOid(String oid) throws SQLException;

	void update(Order o) throws SQLException;

}
