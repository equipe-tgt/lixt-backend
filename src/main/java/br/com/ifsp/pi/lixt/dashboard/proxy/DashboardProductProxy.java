package br.com.ifsp.pi.lixt.dashboard.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import br.com.ifsp.pi.lixt.dashboard.response.IDashboardProduct;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DashboardProductProxy implements InvocationHandler {
	
	private Object[] data;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		switch(method.getName()) {
			case "getDate":
				return data[0];
			case "getPrice":
				return data[1];
			default:
				return null;
		}
	}
	
	public static IDashboardProduct map(Object[] data) {
		return (IDashboardProduct) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), 
				new Class[] {IDashboardProduct.class}, 
				new DashboardProductProxy(data)
		);
	}

}
