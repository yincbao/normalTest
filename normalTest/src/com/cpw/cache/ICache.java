package com.cpw.cache;

/**
 * @author Weimmy
 * @version :1.0
 * @date:2011-11-25
 */
public interface ICache {
    public Object get(String key);

    public boolean set(String key, Object value);

    public boolean set(String key, Object value, int expireTime);

    public boolean keyExists(String key);

    public boolean delete(String key);

    public boolean cas(String key, Object value, long casUnique);

    public long incr(String key, long value);

    public long incr(String key, long value, long defaultValue);

    public long decr(String key, long value);
}
