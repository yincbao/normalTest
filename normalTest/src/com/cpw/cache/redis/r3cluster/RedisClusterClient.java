package com.cpw.cache.redis.r3cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

import redis.clients.jedis.JedisCluster;

import com.cpw.cache.redis.r3cluster.util.JacksonJsonRedisSerializerHolder;
import com.cpw.cache.util.StringUtil;

public class RedisClusterClient {
	
	private static final Log logger = LogFactory.getLog(RedisClusterClient.class);
	private  JedisCluster jc;
	
	public JedisCluster getJc() {
		return jc;
	}

	public void setJc(JedisCluster jc) {
		this.jc = jc;
	}

	public void set(Object key, Object value,boolean coverOnExist){
		if(key==null)
			return;
		set(serialize( key),serialize(value),coverOnExist);
	}
	
	public void set(String key , String value, boolean coverOnExist){
		logger.debug("key :"+ key+ " | value:"+value+" | coverOnExist:"+coverOnExist);
		if(StringUtil.isEmpty(key))
			return;
		if(coverOnExist)
			jc.set(key, value);
		else
			jc.setnx(key, value);
	}
	
	public void set(String key , String value){
		set(key,value,true);
	}
	
	public void set(Object key, Object value){
		set(key,value,true);
	}
	
	public void set(String key , String value,long second){
		if(StringUtil.isEmpty(key))
			return;
		logger.debug("opt:setex | key :"+ key+ " | value:"+value+" | second:"+second);
		jc.setex(key, (int)second/1000, value);
	}
	
	public void set(Object key , Object value,long second){
		if(key==null)
			return;
		
		set(serialize( key),serialize(value) ,second);
	}
	
	
	/**
	 * redis expire
	 * @param key
	 * @return
	 */
	public boolean expire (String key,long seconds){
		return jc.pexpire(key, seconds)==1;
	}
	
	/**
	 * redis expire
	 * @param key
	 * @return
	 */
	public boolean expire (Object key,long seconds){
		return expire(serialize(key), seconds);
	}
	
	/**
	 * redis pexpireAt
	 * @param key
	 * @return
	 */
	public boolean expireAt (String key,long expireTime){
		return jc.pexpireAt(key, expireTime)==1;
	}
	/**
	 * redis pexpireAt
	 * @param key
	 * @return
	 */
	public void expireAt (Object key,long expireTime){
		expireAt(new String(serialize(key)),expireTime);
	}
	/**
	 * redis append
	 * @param key
	 * @return
	 */
	public void append(String key, String append){
		jc.append(key, append);
	}
	
	/**
	 * redis del
	 * @param key
	 * @return
	 */
	public boolean delete(String key){
		return jc.del(key).longValue()>0;
	}
	
	/**
	 * redis del
	 * @param key
	 * @return
	 */
	public boolean delete(Object key ){
		if(key==null)
			return false;
		return delete(serialize(key));
	}
	
	/**
	 * redis type
	 * @param key
	 * @return
	 */
	public String type(String key){
		return  jc.type(key);
	}
	
	/**
	 * redus type
	 * @param key
	 * @return
	 */
	public String type(Object key){
		return type(serialize(key));
	}
	
	/**
	 * redis get
	 * @param key
	 * @param objectType
	 * @return
	 */
	public <T> T get(String key,Class<T> objectType){
		try{
			String result = jc.get(key);
			logger.debug("opt: get | key: "+key+" | value: "+result);
			return deserialize(result,objectType);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
		
	}
	
	/**
	 * redis get
	 * @param key
	 * @param objectType
	 * @return
	 */
	public  <T> T get(Object key,Class<T> objectType){
		try{
			return get(serialize(key),objectType);
		}catch(Exception e ){
			logger.error(e.getMessage(), e);
		}
		return null;
		
	}
	
	/**
	 * redis get
	 * @param key
	 * @return
	 */
	public String get(String key){
		return jc.get(key);
	}
	
	
	/**
	 * redis get
	 * @param key
	 * @return
	 */
	public String get(Object key){
		return jc.get(serialize(key));
	}
	
	
	/**
	 * redis ttl
	 * @param key
	 * @return
	 */
	public long ttl(String key){
		return jc.ttl(key);
	}
	
	/**
	 * redis ttl
	 * @param key
	 * @return
	 */
	public long ttl(Object key){
		return ttl(serialize(key));
	}
	
	
	/**
	 * redis hset
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public void setHashtable(String tableName,String key, String value,boolean coverOnExist){
		if(StringUtil.isEmpty(tableName)||StringUtil.isEmpty(key))
			return;
		if(coverOnExist)
			jc.hset(tableName, key, value);
		else
			jc.hsetnx(tableName, key, value);
	}
	
	/**
	 * redis hset
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public void setHashtable(Object tableName,Object key, Object value,boolean coverOnExist){
		if(tableName==null||key==null)
			return;
		
		setHashtable(serialize(tableName), serialize(key), serialize(value),coverOnExist);
	}
	
	/**
	 * redis hmset
	 * @param tableName
	 * @param dataMap
	 */
	public void setHashtable(String tableName, Map<String,String> dataMap){
		jc.hmset(tableName, dataMap);
	}
	
	/**
	 * redis hmset
	 * @param tableName
	 * @param dataMap
	 */
	public <K, V> void setHashtable(Object tableName, Map<K,V> dataMap){
		if(tableName==null||dataMap==null||dataMap.isEmpty())
			return;
		Map<String,String> sMap = new HashMap<String,String>();
		for(K key:dataMap.keySet()){
			V value = dataMap.get(key);
			try{
				sMap.put(serialize(key), serialize(value));
			}catch(Exception e){
				logger.error("class not cast ",e);
			}
			
		}
		setHashtable(serialize(tableName), sMap);
	}
	
	/**
	 * redis hmget
	 * @param tableName
	 * @param objectType
	 * @param keys
	 * @return
	 */
	public <T> List<T> getValueListFromHashtable(Object tableName,Class<T> objectType,String...keys){
		
		if(tableName==null)
			return null;
		
		List<String> list = jc.hmget(serialize(tableName), keys);
		List<T> resultList = new ArrayList<T>();
		for(String value:list){
			resultList.add(deserialize(value,objectType));
		}
		return resultList;
	}
	/**
	 * redis hget
	 * @param tableName
	 * @param key
	 * @param objectType
	 * @return
	 */
	public <T> T getFromHashtable(String tableName,String key,Class<T> objectType){
		if(StringUtil.isEmpty(tableName)){
			return null; 
		}
		String result = jc.hget(tableName, key);
		logger.debug("opt: get | key: "+key+" | value: "+result);
		return deserialize(result,objectType);
	}
	
	/**
	 * redis hget
	 * @param tableName
	 * @param key
	 * @param objectType
	 * @return
	 */
	public <T> T getFromHashtable(Object tableName,Object key,Class<T> objectType){
		if(tableName==null){
			return null; 
		}
		return getFromHashtable(serialize(tableName),serialize(key),objectType);
	}
	
	/**
	 * redus hget
	 * @param tableName
	 * @param key
	 * @param objectType
	 * @return
	 */
	public <T> T getKeysFromHashtable(String tableName,Object key,Class<T> objectType){
		if(tableName==null){
			return null; 
		}
		return getFromHashtable(serialize(tableName),serialize(key),objectType);
	}
	
	/**
	 * redis hgetall
	 * @param tableName
	 * @return
	 */
	public Map<String,String>  getWholeHashtable(String tableName){
		if(StringUtil.isEmpty(tableName)){
			return null; 
		}
		return jc.hgetAll(tableName);
	}
	
	/**
	 * redis hgetall
	 * @param tableName
	 * @param mapKeyType
	 * @param mapValueType
	 * @return
	 */
	public <K, V> Map<K, V> getWholeHashtable(Object tableName,Class<K> mapKeyType,Class<V> mapValueType){
		if(tableName==null)
			return null;
		Map<String,String> tmp = jc.hgetAll(serialize(tableName));
		Map<K,V> resultMap = new HashMap<K,V>();
		for(String key:tmp.keySet()){
			String value = tmp.get(key);
			resultMap.put(deserialize(key,mapKeyType), deserialize(value,mapValueType));
		}
		return null;
	}
	
	/**
	 * redis hdel
	 * @param tableName
	 * @param keys
	 */
	public void deleteFromHashtable(String tableName,String... keys){
		if(StringUtil.isEmpty(tableName)||keys==null||keys.length<1)
			return;
		jc.hdel(tableName, keys);
	}
	
	/**
	 * redus hdel
	 * @param tableName
	 * @param keys
	 */
	public void deleteFromHashtable(Object tableName,Object... keys){
		if(tableName==null||keys==null||keys.length<1)
			return;
		String[] redisKeys = new String[keys.length];
		for(int i = 0;i<keys.length;i++){
			redisKeys[i] = serialize(keys[i]);
		}
		deleteFromHashtable(serialize(tableName),redisKeys);
	}
	
	/**
	 * String type not serialize, since jasksonjson will add "" around a String value, this will make client confused. 
	 * @param key
	 * @return
	 */
	private String serialize(Object key) {
		String redisKey = "";
		if(!key.getClass().getName().equals("java.lang.String")){
			JacksonJsonRedisSerializer<?> serializer =  JacksonJsonRedisSerializerHolder.getSerializer(key.getClass());
			redisKey = new String(serializer.serialize(key));
		}else
			redisKey = (String) key;
		return redisKey;
	}
	
	
	/**
	 *redis hvals
	 *
	 */
	public <T> List<T> getValFromHashtable(Object key,Class<T> objectType){
		
		List<String> metaData = jc.hvals(serialize(key));
		List<T> result = new ArrayList<T>();
		
		for(String meta:metaData){
			result.add(deserialize(meta,objectType));
		}
		return result;
	}
	
	/**
	 * redis hsets
	 * @param meta
	 * @param objectType
	 * @return
	 */
	private <T> T deserialize(String meta,Class<T> objectType) {
		try{
			if(!objectType.getName().equals("java.lang.String")){
				JacksonJsonRedisSerializer<?> deserializer =  JacksonJsonRedisSerializerHolder.getSerializer(objectType);
				return (T) deserializer.deserialize(meta.getBytes());
			}else
				return (T) meta;
		}catch(Exception e){
			logger.error("can't deserialize ["+meta+" to object: "+objectType.getName()+"]", e);
			throw e;
		}
	}


	
}
