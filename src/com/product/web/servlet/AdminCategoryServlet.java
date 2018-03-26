package com.product.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.domain.Category;
import com.product.service.CategoryService;
import com.product.service.entity.CategoryServiceEntity;
import com.product.web.base.BaseServlet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//找到所有的category
	public String findAllCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CategoryService cs = new CategoryServiceEntity();
		List<Category> list = cs.findAllCategory();
		String json = JSONArray.fromObject(list).toString();
		resp.getWriter().write(json);
		return null;
	}
	//添加一条category
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cname = req.getParameter("cname");
		CategoryService cs = new CategoryServiceEntity();
		cs.insertCategory(cname);
		resp.getWriter().write("{'msg':'添加成功'}");
		return null;
	}
	//更新一条category
	public String updateCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("cid");
		String cname = req.getParameter("cname");
		CategoryService cs = new CategoryServiceEntity();
		cs.updateCategory(cid,cname);
		Map<String, String> map = new HashMap<>();
		map.put("msg", "修改成功");
		String json = JSONObject.fromObject(map).toString();
		resp.getWriter().write(json);
		return null;
	}
	//根据cid找出category
	public String findByCid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("cid");
		CategoryService cs = new CategoryServiceEntity();
		Category cg = cs.findByCid(cid);
		String json = JSONObject.fromObject(cg).toString();
		resp.getWriter().write(json);
		return null;
	}
	//删除一条category
	public String delCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cid = req.getParameter("cid");
		CategoryService cs = new CategoryServiceEntity();
		cs.delCategory(cid);
		
		Map<String, String> map = new HashMap<>();
		map.put("msg", "删除成功");
		
		String json = JSONObject.fromObject(map).toString();
		resp.getWriter().write(json);
		return null;
	}

	
}
