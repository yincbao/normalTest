package com.cpw.cache.dao;

import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


public abstract class AbstractDao<K,V> {

	
	protected RedisTemplate<K,V> redisTemplate;
	
	/**
	 * packaging redis cmd keys partten
	 * @param key
	 * @return
	 */
	public Set<K> keys(K key){
		return redisTemplate.keys(key);
	}
	
    public boolean exists(final K key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<K> keySerializer = (RedisSerializer<K>) redisTemplate.getKeySerializer();
                return connection.exists(keySerializer.serialize(key));
            }
        });
    }
    
    /**
     * clean all keys packaging redis cmd FLUSHDB
     * @return
     */
    public boolean flushDB() {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            	try{
            		 connection.flushDb();
                     return true;
            	}catch(Exception e){
            		return false;
            	}
               
            }
        });
    }

    
    /**
     * return dbsize， key amount， packaging redis cmd DBSIZE
     * @return
     */
    public long dbSize() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * check is current connect still alive  packaging redis cmd PING, if connection alive return PONG
     * @return
     */
    public boolean ping() {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping().equalsIgnoreCase("PONG");
            }
        });
    }

}
