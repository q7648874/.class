package com.product.service;

import java.util.List;

import com.product.domain.PageBean;
import com.product.domain.Product;

public interface ProductService {

	List<Product> findnewProduct();

	List<Product> findhotProduct();

	PageBean<Product> findByPage(String cid, int currentPage, int pageSize);

	Product findProductById(String pid);

	void addProduct(Product p);

}
