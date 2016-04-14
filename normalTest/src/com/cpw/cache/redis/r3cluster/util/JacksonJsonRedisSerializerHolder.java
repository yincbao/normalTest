package com.cpw.cache.redis.r3cluster.util;

import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

import com.cpw.cache.local.CacheManager;

public class JacksonJsonRedisSerializerHolder {
	
	private final static String jacksonJsonRedisSerializerCache = "JacksonJsonRedisSerializerCache";
	

	private JacksonJsonRedisSerializerHolder(){}
	
	public static JacksonJsonRedisSerializer<?> getSerializer(Class<?> objectType){
		
		JacksonJsonRedisSerializer<?> serializer = (JacksonJsonRedisSerializer<?>) CacheManager.get(jacksonJsonRedisSerializerCache,objectType.getName());
		if(serializer==null){
			serializer = getInstance(objectType);
			CacheManager.set(jacksonJsonRedisSerializerCache, objectType.getName(), serializer);
		}
		return 	serializer;
	}
	
	private static <T> JacksonJsonRedisSerializer<T> getInstance(Class<T> objectType){
		return new JacksonJsonRedisSerializer<T>(objectType);
	}
}
