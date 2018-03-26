package com.product.dao;

import java.sql.SQLException;
import java.util.List;

import com.product.domain.Product;

public interface ProductDao {

	List<Product> findnewProduct() throws SQLException;

	List<Product> findhotProduct() throws SQLException;

	int findProductNb() throws SQLException;

	List<Product> findByPage(String cid, int currentPage, int pageSize) throws SQLException;

	Product findProductById(String pid) throws SQLException;

	void addProduct(Product p) throws SQLException;

}
