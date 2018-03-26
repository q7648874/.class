package com.product.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.domain.Category;
import com.product.service.CategoryService;
import com.product.service.entity.CategoryServiceEntity;
import com.product.util.JedisUtils;
import com.product.util.JsonUtil;
import com.product.web.base.BaseServlet;

import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得请求参数  无
		//调用业务逻辑
		//避免频繁访问mysql 使用jedis缓存
		String json = null;
		try {
			Jedis jedis = JedisUtils.getJedis();
			json = jedis.get("categorylist");
			if(json==null){//如果米有缓存 从mysql数据库中查询出来并放入缓存中
				CategoryService cs = new CategoryServiceEntity();
				List<Category> plist = cs.findAllCategory();
				//将查询到的list集合转换为json字符串
				json = JsonUtil.list2json(plist);
				System.out.println("jedis开启但未找到数据使用mysql数据库查询");
				jedis.set("categorylist", json);
			}
			JedisUtils.closeJedis(jedis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("jedis启动失败 请检查虚拟机是否开启  首先使用mysql查询");
			json = mysqlGetJson();
		}
		//分发转向
		request.getSession().setAttribute("json", json);
		response.getWriter().write(json);
		return null;
	}
	public static String mysqlGetJson(){
		CategoryService cs = new CategoryServiceEntity();
		List<Category> plist = cs.findAllCategory();
		//将查询到的list集合转换为json字符串
		return JsonUtil.list2json(plist);
	}

	
}
