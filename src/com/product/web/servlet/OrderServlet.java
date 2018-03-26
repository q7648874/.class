package com.product.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product.domain.Order;
import com.product.domain.Orderitem;
import com.product.domain.PageBean;
import com.product.domain.Product;
import com.product.domain.User;
import com.product.service.OrderService;
import com.product.service.entity.OrderServiceEntity;
import com.product.util.ProxyFactory;
import com.product.web.base.BaseServlet;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//���session���� �ж��Ƿ��Ѿ���¼��
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("sess_user");
		if(user==null){
			return "/user/login.jsp";
		}
		//����û���¼��ȡ�ù��ﳵ�е����� ����order��������Ĵ���
		Map<Product, Integer> cartMap = (Map<Product, Integer>) session.getAttribute("cartMap");
		//�Զ�����������Ը�ֵ
		String oid = UUID.randomUUID().toString().replace("-", "");  	 //����id
		String ordertime = new Date().toLocaleString();                  //����������ʱ��
		int state = 0;                                       			 //������״̬
		String address =null;                                 			 //��ַ
		String name = null;                                    			 //���ʼ��˵�����
		String telephone =null;                               			 //���ʼ��˵ĵ绰
		String uid = user.getUid();                                      //��¼�û���id
		//����ͨ�����ﳵ���ݾ��м����ֵ
		double total = 0;                                  	//�ܼ� �ڹ��ﳵ�����м���
		List<Orderitem> orderitem = new ArrayList<>(0);  	//��װ�˶��������ļ���  ͨ�����ﳵ���ݼ���
		//����cartMap ��������������ݸ�ֵ  ͬʱ����������orderitem����
		for (Map.Entry<Product,Integer> entry : cartMap.entrySet()) {
			Product p = entry.getKey();		//����Ʒ����
			String itemid = UUID.randomUUID().toString().replace("-", "");	//�����Լ����id
			int count = entry.getValue();									//��Ʒ����
			String pid = p.getPid();										//��Ʒid
			Orderitem oi = new Orderitem(itemid, count, pid, oid,p);
			orderitem.add(oi);
			total += p.getShop_price()*count;
		}
		//��ֵ�������������� order
		Order order = new Order(oid, ordertime, total, state, address, name, telephone, uid, orderitem);
		//��order������service�� �����ݿ⾭�в���
		OrderService os = (OrderService) ProxyFactory.getProxy(OrderServiceEntity.class);
		os.createOrder(order);
		session.setAttribute("order", order);
		return "/user/order_info.jsp";
	}
	//Ϊorder_list������ҳ����
	public String findOrderByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User sess_user = (User) req.getSession().getAttribute("sess_user");
		String uid = sess_user.getUid();
		
		int currentPage = 1;
		String cu = req.getParameter("currentPage");
		if(cu!=null){
			currentPage = Integer.parseInt(cu);
		}
		int pageSize = 2;
		
		PageBean pb = new OrderServiceEntity().findOrderByPage(uid,currentPage,pageSize);
		req.setAttribute("pb", pb);
		//д���� ҳ��Ҫ����һ����ҳ
//		List<Order> orderList = new OrderServiceEntity().findOrderByUid(uid);
//		req.setAttribute("orderList", orderList);
		return "/user/order_list.jsp";
	}
	
	//��order_list����ʱ����oid
	public String payOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		OrderService os = new OrderServiceEntity();
		Order order = os.payOrderByOid(oid);
		req.getSession().setAttribute("order", order);
		return "/user/order_info.jsp";
	}
}
