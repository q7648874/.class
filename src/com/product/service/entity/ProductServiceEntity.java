package com.product.service.entity;

import java.sql.SQLException;
import java.util.List;

import com.product.dao.ProductDao;
import com.product.dao.entity.ProductDaoEntity;
import com.product.domain.PageBean;
import com.product.domain.Product;
import com.product.service.ProductService;

public class ProductServiceEntity implements ProductService {

	@Override
	public List<Product> findnewProduct() {
		// TODO Auto-generated method stub
		ProductDao pd = new ProductDaoEntity();
		List<Product> list = null;
		try {
			list = pd.findnewProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Product> findhotProduct() {
		// TODO Auto-generated method stub
		ProductDao pd = new ProductDaoEntity();
		List<Product> list = null;
		try {
			list = pd.findhotProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PageBean<Product> findByPage(String cid, int currentPage, int pageSize) {
		ProductDao pd = new ProductDaoEntity();
		int count = 0;
		int totalPage = 0;
		List<Product> list = null;
		try {
			count = pd.findProductNb();
			totalPage = (int) Math.ceil(count*1.0/pageSize);
			list = pd.findByPage(cid,currentPage,pageSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new PageBean<>(currentPage, pageSize, count, totalPage, list);
	}

	@Override
	public Product findProductById(String pid) {
		// TODO Auto-generated method stub
		ProductDao pd = new ProductDaoEntity();
		try {
			return pd.findProductById(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Œ“≤Ÿƒ„¬Ë");
		}
	}

	@Override
	public void addProduct(Product p) {
		// TODO Auto-generated method stub
		ProductDao pd = new ProductDaoEntity();
		try {
			pd.addProduct(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
