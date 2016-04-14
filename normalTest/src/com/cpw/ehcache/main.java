package com.cpw.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class main {
	
	public static void main(String[] args) {
		CacheManager cachemanager = EHCacheUtil.getCacheManager();
		Cache cache = cachemanager.getCache("MYCACHE");
		Element e1 = new Element("NAME", "pour");
		Element e2 = new Element("bac", "Paul");
		cache.put(e1);
		cache.put(e2);
		System.out.println(cache.get("NAME").getValue());
		System.out.println(cache.get("bac").getValue());
		System.out.println(cache.getName());
		
	}
	public static void main1(String[] args) {
//		loginedAndCache l = new loginedAndCache();
//		l.login();
		ReloginFromCache r = new ReloginFromCache();
		r.relogin();
		
	}

}
