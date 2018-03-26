package com.product.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.domain.Product;
import com.product.service.entity.ProductServiceEntity;
import com.product.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String addCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��ȡ�������   pid
		String pid = req.getParameter("pid");
		//����ҵ���߼�  	����pid�����Ʒ	��session���л�ù��ﳵ(���û�оʹ���һ��)	�����ﳵ�е���Ʒ����	����Ʒ��ӵ����ﳵ	  �����ﳵ����session����
		Product p = new ProductServiceEntity().findProductById(pid);
		HttpSession session = req.getSession();
		//��session���л�ù��ﳵ
		Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute("cartMap");
		if(cartMap==null){
			cartMap = new HashMap<>();
		}
		//�����ﳵ�е���Ʒ����  �������Ʒ��ͬ��hashmap��value������1 ��������valueֵΪ1
		String snum = req.getParameter("num");
		if(snum==null){
			return null;
		}
		int num = Integer.parseInt(snum);//��product_info.jspҳ���þ������־����ۼ�
		if(cartMap.containsKey(p)){
			num += cartMap.get(p);
			if(req.getParameter("change")!=null){//�ж��Ƿ�ͨ��cart.jspҳ���ajax��ʽ������������
				num = Integer.parseInt(req.getParameter("num"));
			}
		}
		//����Ʒ����빺�ﳵ
		cartMap.put(p, num);
		//�����ﳵ����session����
		session.setAttribute("cartMap", cartMap);
		//�ַ�ת��  ʹ���ض��� �����ظ��ύ  Ҳ����ʹ��ת�� ���Ǳ���ʹ�����ƻ������ύ����������һ��������ƾ��бȽ�  ���Ʋ�ͬ���پ���ҵ���߼�
		resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
		session.removeAttribute("cartMap");
		return null;
	}

	public String delByPid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pid = req.getParameter("pid");
		Product p = new ProductServiceEntity().findProductById(pid);
		Map<Product, Integer> cartMap = (Map<Product, Integer>) req.getSession().getAttribute("cartMap");
		cartMap.remove(p);
		resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
		return null;
	}
	public String clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = "cartMap";
		req.getSession().removeAttribute(name);
		resp.sendRedirect(req.getContextPath()+"/user/cart.jsp");
		return null;
	}
}
