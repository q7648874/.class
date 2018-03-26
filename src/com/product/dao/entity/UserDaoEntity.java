package com.product.dao.entity;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.product.dao.UserDao;
import com.product.domain.User;
import com.product.util.C3P0util;

public class UserDaoEntity implements UserDao{

	@Override
	public void insert(User u) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?,?,?,0,null)";
		qr.update(sql,u.getUid(),u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getBirthday(),u.getSex(),u.getCode());
	}

	@Override
	public void update(User u) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
		String sql = "UPDATE USER SET username=?,PASSWORD=?,NAME=?,email=?,birthday=?,sex=?,CODE=?,state=? WHERE uid=?";
		qr.update(sql,u.getUsername(),u.getPassword(),u.getName(),u.getEmail(),u.getBirthday(),u.getSex(),u.getCode(),u.getState(),u.getUid());
	}

	@Override
	public User queryOne(String filed, String value) throws SQLException {
		// TODO Auto-generated method stub
		User u = null;
		if("username".equals(filed)){
			QueryRunner qr = new QueryRunner(C3P0util.getDataSource());
			u = qr.query("select * from user where username= ?", new BeanHandler<>(User.class),value);
		}
		return u;
	}


}
