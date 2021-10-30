package br.com.ifsp.pi.lixt.dashboard.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import br.com.ifsp.pi.lixt.dashboard.response.IDashboardTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DashboardTimeProxy implements InvocationHandler {
	
	private Object[] data;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		switch(method.getName()) {
			case "getPrice":
				return data[0];
			case "getTime":
				return data[1].toString();
			default:
				return null;
		}
	}
	
	public static IDashboardTime map(Object[] data) {
		return (IDashboardTime) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), 
				new Class[] {IDashboardTime.class}, 
				new DashboardTimeProxy(data)
		);
	}

}
