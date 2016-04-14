package com.cpw.ehcache;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class ReloginFromCache {
	
	public void relogin(){
		CacheManager cacheManager = EHCacheUtil.getCacheManager();
		Cache cache = cacheManager.getCache("MYCACHE");
		List<User> list = cache.getKeys();
		for(int i=0;i<list.size();i++){
			System.out.println(cache.get(list.get(i)).getValue());
		}
	}
}
