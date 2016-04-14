package com.cpw.ehcache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class loginedAndCache {
	
	public void login(){
		CacheManager cacheManager = EHCacheUtil.getCacheManager();
		Cache cache = cacheManager.getCache("MYCACHE");
		User user = new User(1,"Paul");
		
		Element cachedUser  = new Element(user.getId(),user);
		cache.put(cachedUser);
		System.out.println("You have login successfully");
	}

}
