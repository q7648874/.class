package com.product.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.product.domain.User;
import com.product.service.entity.UserServiceEntity;

/**
 * Servlet Filter implementation class AutoLogin
 */
public class AutoLogin implements Filter {

    /**
     * Default constructor. 
     */
    public AutoLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		//���� ǿת�������
		HttpServletRequest request = (HttpServletRequest) req;
		//�ж�session�����Ƿ����û��Ѿ���¼����Ϣ
		User user = (User) request.getSession().getAttribute("sess_user");
		if(user==null){
			Cookie[] cookies = request.getCookies();
			if(cookies==null){//û��cookies���� ֱ�ӷ���
				chain.doFilter(request, response);
				return;
			}
			Cookie autoCookie = null;//���������Զ���¼��cookieΪnull
			for (Cookie cookie : cookies) {//��������cookies����  Ѱ����û���Զ���¼��cookie
				if("autoLogin".equals(cookie.getName())){
					autoCookie = cookie;
					break;
				}
			}
			if(autoCookie==null){//���û�ҵ� ����
				chain.doFilter(request, response);
				return;
			}
			//�ҵ����Զ���¼��cookie
			String[] values = autoCookie.getValue().split("-");
			user = new UserServiceEntity().login(values[0], values[1]);
			request.getSession().setAttribute("sess_user", user);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
