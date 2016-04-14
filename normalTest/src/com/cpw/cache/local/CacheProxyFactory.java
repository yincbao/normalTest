package com.cpw.cache.local;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This class creates the Cache Proxy for a target interface.
 */
public class CacheProxyFactory {
	public Object getCacheProxy(Class proxyInterface, Object target) {
		try {
			Object proxy = null;
			// The naming convention for a CacheInvocationHandler is "proxyInterfaceName" + "CacheInvocationHandler" 
			// For e.g. UserDomainDAOCacheInvocationHandler
			//Note: A CacheInvocationHandler is to be defined in the same package where the proxyInterface is defined.
			String invocationHandlerName = proxyInterface.getName()
					+ "CacheInvocationHandler";
			Class invocationHandlerNameClass = Class
					.forName(invocationHandlerName);
			//Every CacheInvocationHandler defined will have a Constructor that takes CacheManager as an argument
			Constructor ctor = invocationHandlerNameClass
					.getDeclaredConstructor(CacheManager.class);
			ctor.setAccessible(true);
			//Create the InvocationHandler and set the target
			CacheInvocationHandlerAbstract invocationHandler = (CacheInvocationHandlerAbstract) ctor
					.newInstance(CacheManager.getInstance());
			invocationHandler.setTarget(target);//Create the Proxy
			proxy = this.createProxy(proxyInterface, invocationHandler);
			return proxy;
		} catch (Exception e) {
			throw new CacheException(this.getClass().getName(), e);
		}
	}
	
	private Object createProxy(Class proxyInterface,
			InvocationHandler handler) {
		return Proxy.newProxyInstance(proxyInterface.getClassLoader(),
				new Class[] { proxyInterface }, handler);
	}	
}
