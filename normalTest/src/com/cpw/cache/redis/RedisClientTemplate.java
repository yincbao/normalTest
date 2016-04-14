package com.cpw.cache.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;

public class RedisClientTemplate {


    private RedisDataSource     redisDataSource;
    
    

    public RedisDataSource getRedisDataSource() {
		return redisDataSource;
	}

	public void setRedisDataSource(RedisDataSource redisDataSource) {
		this.redisDataSource = redisDataSource;
	}

	public void disconnect() {
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        shardedJedis.disconnect();
    }

    /**
     * 设置单个值
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        String result = null;

        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            broken = true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }

    /**
     * 获取单个值
     * 
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if (shardedJedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = shardedJedis.get(key);

        } catch (Exception e) {
            broken = true;
            e.printStackTrace();
        } finally {
            redisDataSource.returnResource(shardedJedis, broken);
        }
        return result;
    }
    

    public static void main(String[] args) {
        ApplicationContext ac =  new ClassPathXmlApplicationContext("com/cpw/cache/redis/spring-redis.xml");
        RedisClientTemplate redisClient = (RedisClientTemplate)ac.getBean("redisClient");
        //redisClient.set("a", "abc");
        System.out.println(redisClient.get("a"));
    }


}

