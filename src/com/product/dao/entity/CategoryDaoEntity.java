package com.product.dao.entity;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.product.dao.CategoryDao;
import com.product.domain.Category;
import com.product.util.C3P0util;

public class CategoryDaoEntity implements CategoryDao {

	@Override
	public List<Category> findAllCategory() throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		return qr.query("select * from category", new BeanListHandler<>(Category.class));
	}

	@Override
	public void insertCategory(String cname) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		qr.update("insert into category values (?,?)",UUID.randomUUID().toString().replace("-", ""),cname);
	}

	@Override
	public void delCategory(String cid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		qr.update("delete from category where cid=?",cid);
	}

	@Override
	public Category findByCid(String cid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		return qr.query("select * from category where cid=?", new BeanHandler<>(Category.class),cid);
	}

	@Override
	public void updateCategory(String cid, String cname) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		qr.update("update category set cname=? where cid=?",cname,cid);
	}

}
