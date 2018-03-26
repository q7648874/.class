package com.product.service.entity;

import java.sql.SQLException;
import java.util.List;

import com.product.dao.CategoryDao;
import com.product.dao.entity.CategoryDaoEntity;
import com.product.domain.Category;
import com.product.service.CategoryService;

public class CategoryServiceEntity implements CategoryService {

	@Override
	public List<Category> findAllCategory() {
		// TODO Auto-generated method stub
		CategoryDao cd = new CategoryDaoEntity();
		List<Category> list = null;
		try {
			list = cd.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void insertCategory(String cname) {
		// TODO Auto-generated method stub
		CategoryDao cd = new CategoryDaoEntity();
		try {
			cd.insertCategory(cname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delCategory(String cid) {
		// TODO Auto-generated method stub
		CategoryDao cd = new CategoryDaoEntity();
		try {
			cd.delCategory(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Category findByCid(String cid) {
		// TODO Auto-generated method stub
		CategoryDao cd = new CategoryDaoEntity();
		Category cg = null;
		try {
			cg=cd.findByCid(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cg;
	}

	@Override
	public void updateCategory(String cid, String cname) {
		// TODO Auto-generated method stub
		CategoryDao cd = new CategoryDaoEntity();
		try {
			cd.updateCategory(cid,cname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
