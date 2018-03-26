package com.product.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

	public static Object getProxy(final Class clazz){
		//参数1：类加载器。固定写法。与被代理对象使用相同的类加载器即可。
		//参数2：代理类要实现的接口。固定写法。与被代理对象使用相同的接口即可。
		//参数3：策略（方案）设计模式的应用。代理对象如何调用真实对象的方法。
		Object proxy = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new InvocationHandler() {
			//在代理对象调用的任何方法，都触发此方法执行。
			//参数1：代理对象本身的引用。一般不用。
			//参数2：代理对象调用的方法.
			//参数3：代理对象调用的方法中的参数。
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object obj=null;
				
				try {
					//前置增强  
//					System.out.println("开启事务");
					ConnectionManager.begin();
					//执行真实对象（被代理对象）的方法。
					obj = method.invoke(clazz.newInstance(), args); //参数1：真实对象  参数2：方法的实参
					//后置增强
//					System.out.println("提交事务");
					ConnectionManager.commit();
				} catch (Exception e) {
					ConnectionManager.rollBack();
					System.out.println("回滚事务");
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
