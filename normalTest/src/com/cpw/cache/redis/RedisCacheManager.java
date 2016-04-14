package com.cpw.cache.redis;

import java.io.UnsupportedEncodingException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.cpw.cache.pojo.Entity;


public class RedisCacheManager<K, V> {
	
	
	private  RedisTemplate<K,V> redisTemplate;
	private static ApplicationContext ctx;
	static{
		
		 ctx = new ClassPathXmlApplicationContext("com/cpw/cache/redis/spring-redis.xml");
		 
	         
	}
	
	
	public  Object get(final Object keyId) {
		this.redisTemplate = (RedisTemplate<K, V>) ctx.getBean("redisTemplate");
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
             public Object doInRedis(RedisConnection connection)
                       throws DataAccessException {
                  RedisSerializer<String> valueSerializer =  (RedisSerializer<String>) redisTemplate.getStringSerializer();
                  System.out.println("FETCH keyId serializer data: "+keyId);
                  byte[] key = valueSerializer.serialize(String.valueOf(keyId));
                  System.out.println("FETCH key serializer data: "+key);
                  byte[] value = connection.get(key);
                  if (value == null) {
                       return null;
                  }
                  return valueSerializer.deserialize(value);
             }
        });
        redisTemplate.multi();
        return result;
	}
	
	public boolean set(final Object keyId,final Object value){
		this.redisTemplate = (RedisTemplate<K, V>) ctx.getBean("redisTemplate");
		return redisTemplate.execute(new RedisCallback<Boolean>() {
         public Boolean doInRedis(RedisConnection connection)
                   throws DataAccessException {
              RedisSerializer<Object> serializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
              System.out.println("SAVEING keyId serializer data: "+keyId);
              byte[] key  = serializer.serialize(keyId);
              System.out.println("SAVEING key serializer data: "+key);
              byte[] name = serializer.serialize(value);
              System.out.println("SAVEING value serializer data: "+name);
              return connection.setNX(key, name);
              
         }
    
   	 });
   	   
     }
    
    public static void main1(String args[]){
    	RedisCacheManager<String,Object> instance = new RedisCacheManager<String,Object>();
    	Entity obj = new Entity("zhangsan",1000,"Nanjing");
    	System.out.println(obj+" been set to redis");
    	
    	instance.set("zhangsna", obj);
    	
    	System.out.println(instance.get("zhangsna"));
    }
    
    public static void main(String args[]) throws UnsupportedEncodingException{
    	RedisCacheManager<String,Object> instance = new RedisCacheManager<String,Object>();
    	instance.redisTemplate = (RedisTemplate<String, Object>) RedisCacheManager.ctx.getBean("redisTemplate");
    	RedisSerializer serializer = instance.redisTemplate.getValueSerializer();
    	String str = "12344544";
    	byte[] serialized = serializer.serialize(str);
    	byte[] serialized1 = serializer.serialize(str);
    	System.out.println(serialized==serialized1);
    	System.out.println(serialized);
    	System.out.println(serialized1);
    	System.out.println(serializer.deserialize(serializer.serialize("12344544")));
    }

}
