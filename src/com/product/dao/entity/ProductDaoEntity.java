package com.product.dao.entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.product.dao.ProductDao;
import com.product.domain.Product;
import com.product.util.C3P0util;

public class ProductDaoEntity implements ProductDao {

	@Override
	public List<Product> findnewProduct() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		return qr.query("SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 9", new BeanListHandler<>(Product.class));
	}

	@Override
	public List<Product> findhotProduct() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		return qr.query("SELECT * FROM product WHERE is_hot=1 AND pflag=0 LIMIT 9", new BeanListHandler<>(Product.class));
	}

	@Override
	public int findProductNb() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		Long n = (Long) qr.query("select count(*) from product", new ScalarHandler());
		return n.intValue();
	}

	@Override
	public List<Product> findByPage(String cid, int currentPage, int pageSize) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql = "SELECT * FROM product WHERE 1=1 ";
		List list = new ArrayList<>();
		if(cid!=null){
			sql += " and  cid=?";
			list.add(cid);
		}
		sql +=" LIMIT ?,?";
		list.add((currentPage-1)*pageSize);
		list.add(pageSize);     
		return qr.query(sql, new BeanListHandler<Product>(Product.class),list.toArray());
	}

	@Override
	public Product findProductById(String pid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		return qr.query("select * from product where pid=?", new BeanHandler<>(Product.class),pid);
	}

	@Override
	public void addProduct(Product p) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql= "INSERT INTO product VALUES(?,?,?,?,?,?,?,?,0,?)";
		qr.update(sql,p.getPid(),p.getPname(),p.getMarket_price(),p.getShop_price(),p.getPimage(),p.getPdate(),p.getIs_hot(),p.getPdesc(),p.getCid());
	}

}
