package com.product.dao;

import java.sql.SQLException;

import com.product.domain.User;

public interface UserDao {

	void insert(User u) throws SQLException;

	void update(User user) throws SQLException;

	User queryOne(String filed,String value) throws SQLException;

}
