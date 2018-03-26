package com.product.service;

import java.util.List;

import com.product.domain.Category;

public interface CategoryService {

	List<Category> findAllCategory();

	void insertCategory(String cname);

	void delCategory(String cid);

	Category findByCid(String cid);

	void updateCategory(String cid, String cname);

}
