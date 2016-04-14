package com.cpw.cache.local;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
 
/**
 * Base class for any Cache InvocationHandler.
 * This is based on Java Dynamic Proxy.
 * Caching is an Aspect and is handled transparently by the application.
 * Thus, any client object that uses an interface is unaware of the 
 * fact that a cache proxy may have been used as an interceptor. 
 * To define a new cache proxy, a Subclass can be implemented like this:-
   public class UserRegistryCacheInvocationHandler extends CacheInvocationHandlerAbstract {
    private Cache cache;
	public UserRegistryCacheInvocationHandler(CacheManager cacheManager) {
		super(cacheManager);
		long expiryTimeInSeconds=1800;
		int maxElementsInCache=500;
		cache = new Cache("SystemGroupsForUser", expiryTimeInSeconds,maxElementsInCache);
		cacheManager.addCache(cache);
	}

	@Override
	protected boolean canInterceptMethod(Method method,
			Object[] args) {
		if(method.getName().equals("getSystemGroupsByUser")) {
			return true;
		}
		return false;
	}

	@Override
	protected Object getFromCache(Method method, Object[] args)
			 {
		if(method.getName().equals("getSystemGroupsByUser")) {
			return cache.getCacheElementValue((String)args[0]);
		}
		return null; 
	}

	@Override
	protected void addObjectToCache(Method method,
			Object[] args, Object cacheObject) {
		if(cacheObject!=null) {
			String userId = ((String)args[0]).toUpperCase();
			cache.put(new CacheElement(userId, cacheObject));
		}
	}
}
 * A Proxy can be created like this:-
   Object userRegistryCacheProxy = new CacheProxyFactory().getCacheProxy(UserRegistry.class, userRegistry);

 * Any client that will use the cache will have the Cache Proxy
 * injected instead of the actual target class instance.			
 */
public abstract class CacheInvocationHandlerAbstract implements
		java.lang.reflect.InvocationHandler {
	//Target is the class that the proxy is for.
	private Object target = null;
	
	public CacheInvocationHandlerAbstract(CacheManager cacheManager) {
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	/**
	 * This method processes the invocation of a method on a Proxy instance.
	 */
	public final Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try {
			Object retObject = null;
			//Check if this method is to be intercepted
			if (this.canInterceptMethod(method, args) == false) {
				//Method need not be intercepted. Call method  in target
				retObject = method.invoke(target, args);
			} else {
				//Intercept Method. Try to get the value from the cache
				retObject = this.getFromCache(method, args);
				if (retObject == null) {
					//Cache does not have this value. Invoke target and add to cache
					retObject = method.invoke(target, args);
					if(retObject!=null) {
						this.addObjectToCache(method, args, retObject);
					}
				}
			}
			return retObject;
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Checks if the method passed in the parameter is to be intercepted
	 * by the Proxy.
	 * @param method
	 * @param args
	 * @return
	 */
	protected abstract boolean canInterceptMethod(Method method,
			Object[] args);

	/**
	 * This method get the value that is returned by the "Method" parameter
	 * from the Cache. Returns null if the value does not exist in the cache.
	 * @param method
	 * @param args
	 * @return
	 */
	protected abstract Object getFromCache(Method method,
			Object[] args);

	/**
	 * This method adds an object to a cache  
	 * @param method
	 * @param args
	 * @param cacheObject
	 * @return
	 * @throws Throwable
	 */
	protected abstract void addObjectToCache(Method method,
			Object[] args, Object cacheObject);
}
