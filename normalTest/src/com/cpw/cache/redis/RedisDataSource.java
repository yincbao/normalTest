package com.cpw.cache.redis;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {
	
	
	/**
	 * 从redispool获取redis客户端对象，相当于获取connection
	 * @return
	 */
    public abstract ShardedJedis getRedisClient();
    /**
     * 返回redis客户端对象给redispool
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);
    
    /**
     * 
     * @param shardedJedis
     * @param broken 断开的连接true  false 正常连接
     */
    public void returnResource(ShardedJedis shardedJedis,boolean broken);
}

