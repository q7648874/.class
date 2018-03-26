package com.product.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

	public static Object getProxy(final Class clazz){
		//����1������������̶�д�����뱻�������ʹ����ͬ������������ɡ�
		//����2��������Ҫʵ�ֵĽӿڡ��̶�д�����뱻�������ʹ����ͬ�Ľӿڼ��ɡ�
		//����3�����ԣ����������ģʽ��Ӧ�á����������ε�����ʵ����ķ�����
		Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
			//�ڴ��������õ��κη������������˷���ִ�С�
			//����1���������������á�һ�㲻�á�
			//����2�����������õķ���.
			//����3�����������õķ����еĲ�����
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object obj=null;
				
				try {
					//ǰ����ǿ  
//					System.out.println("��������");
					ConnectionManager.begin();
					//ִ����ʵ���󣨱�������󣩵ķ�����
					obj = method.invoke(clazz.newInstance(), args); //����1����ʵ����  ����2��������ʵ��
					//������ǿ
//					System.out.println("�ύ����");
					ConnectionManager.commit();
				} catch (Exception e) {
					ConnectionManager.rollBack();
					System.out.println("�ع�����");
					obj = null;
				} finally{
					ConnectionManager.close();
				}
				return obj;
			}
		});
		return proxy;
	}
}
