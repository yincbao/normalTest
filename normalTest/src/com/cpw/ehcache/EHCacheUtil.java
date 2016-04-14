package com.cpw.ehcache;


import net.sf.ehcache.CacheManager;

public class EHCacheUtil {
	
    
	private static class SingletonHoder{
		static CacheManager cacheManager = new CacheManager(EHCacheUtil.class.getClassLoader().getResource("classpath*:ehcache.xml"));
	}
	
	public static CacheManager getCacheManager(){
		return SingletonHoder.cacheManager;
	}

	
	public static void main(String[] args) {
		String url = EHCacheUtil.class.getClassLoader().getResource(".").getPath().toString();
		System.out.println(url);
		System.out.println(System.getProperty("user.dir"));
	}
}
