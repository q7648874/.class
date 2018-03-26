package com.product.dao;

import java.sql.SQLException;
import java.util.List;

import com.product.domain.Category;

public interface CategoryDao {

	List<Category> findAllCategory() throws SQLException;

	void insertCategory(String cname) throws SQLException;

	void delCategory(String cid) throws SQLException;

	Category findByCid(String cid) throws SQLException;

	void updateCategory(String cid, String cname) throws SQLException;

}
