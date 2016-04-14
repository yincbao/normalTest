package com.cpw.cache.redis.util;


/**
 * 
 * ClassName: IRedisCache
 * @description
 * @author yin_changbao
 * @Date   Aug 31, 2015
 *
 */
public interface IRedisCache {

    /**
     *wrap redis  cmd "del"
     * 
     * @param key
     */
    public abstract long del(String... keys);

    /**
     * SETEX command
     * 
     * @param key
     * @param value
     * @param liveTime second
     */
    public abstract void set(byte[] key, byte[] value, long liveTime);

    /**
     * stringnize reis key, wrap redis SETEX command
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public abstract void set(String key, Object value, long liveTime);

    /**
     * redis SET, stringnized
     * 
     * @param key
     * @param value
     */
    public abstract void set(String key, Object value);

    /**
     * 
     * @param key
     * @param value
     */
    public abstract void set(byte[] key, byte[] value);

    /**
     * 
     * @param key
     * @return
     */
    public abstract String get(String key);

    /**
     * 通过正则匹配keys
     * 
     * @param pattern
     * @return
     */
    public abstract void Setkeys(String pattern);

    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    public abstract boolean exists(String key);

    /**
     * 清空redis 所有数据
     * 
     * @return
     */
    public abstract String flushDB();

    /**
     * 查看redis里有多少数据
     */
    public abstract long dbSize();

    /**
     * 检查是否连接成功
     * 
     * @return
     */
    public abstract String ping();

}