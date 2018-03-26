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
		//����������  ��
		//����ҵ���߼�
		//����Ƶ������mysql ʹ��jedis����
		String json = null;
		try {
			Jedis jedis = JedisUtils.getJedis();
			json = jedis.get("categorylist");
			if(json==null){//������л��� ��mysql���ݿ��в�ѯ���������뻺����
				CategoryService cs = new CategoryServiceEntity();
				List<Category> plist = cs.findAllCategory();
				//����ѯ����list����ת��Ϊjson�ַ���
				json = JsonUtil.list2json(plist);
				System.out.println("jedis������δ�ҵ�����ʹ��mysql���ݿ��ѯ");
				jedis.set("categorylist", json);
			}
			JedisUtils.closeJedis(jedis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("jedis����ʧ�� ����������Ƿ���  ����ʹ��mysql��ѯ");
			json = mysqlGetJson();
		}
		//�ַ�ת��
		request.getSession().setAttribute("json", json);
		response.getWriter().write(json);
		return null;
	}
	public static String mysqlGetJson(){
		CategoryService cs = new CategoryServiceEntity();
		List<Category> plist = cs.findAllCategory();
		//����ѯ����list����ת��Ϊjson�ַ���
		return JsonUtil.list2json(plist);
	}

	
}
