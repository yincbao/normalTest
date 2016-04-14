package com.cpw.cache.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.cpw.cache.dao.AbstractDao;
import com.cpw.cache.dao.IEntityDao;
import com.cpw.cache.pojo.Entity;

/**
 *编程核心还是基于对redis 命令的封装，所以，需要对redis原生命令有一定了解
 *关于，key-value，redis接受的都是byte[]，所有key和value 的序列化策略在CURD操作要保持一致， 基于序列化性能和扩展性考量，
 *	Key
 *		*明确业务背景key都为string的前提下，建议用stringSerializer.
 *		*业务背景布确定key是否全为string，前提下建议使用JacksonJsonRedisSerializer。不建议key 用JdkSerializationRedisSerializer虽然高效，但不是不能保证可用。
 *		*当然也可以根据具体业务逻辑自定义序列化方法：比如实现java.io.Externalizable接口，根据业务需要选取重要属性。重写write/readExternal方法就是个不错的选择。
 *		*JacksonJsonRedisSerializer作key 的序列化策略，理论上说， 可以结合redis的KEYS pattern实现简单的模糊查询功能。
 *	value
 *		*基于性能考量建议全部使用JdkSerializationRedisSerializer。
 *		*JacksonJsonRedisSerializer适合直接缓存restful接口数据
 * ClassName: EntityDaoImpl
 * @description
 * @author yin_changbao
 * @Date   Aug 31, 2015
 *
 */
public class EntityDaoImpl extends AbstractDao<String,Entity> implements IEntityDao<Entity> {

	@Override
	public Entity save(final Entity entity,final boolean cover,final Long expeir) {

		return redisTemplate.execute(new RedisCallback<Entity>() {
			public Entity doInRedis(RedisConnection connection)
                   throws DataAccessException {
              RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
              RedisSerializer<Entity> valueSerializer = (RedisSerializer<Entity>) redisTemplate.getValueSerializer();
              byte[] key  = keySerializer.serialize(table+entity.getId());
              byte[] value = valueSerializer.serialize(entity);
              if(cover){
            	  if(expeir!=null)
            		  connection.setEx(key, expeir, value);
            	  else connection.set(key, value); 
            		  
              }else
            	  connection.setNX(key, value);
              return entity;
         }
   	 });
	}

	@Override
	public Entity query(final long id) {
		Entity result = redisTemplate.execute(new RedisCallback<Entity>() {
             public Entity doInRedis(RedisConnection connection)
                       throws DataAccessException {
            	 RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
                 RedisSerializer<Entity> valueSerializer = (RedisSerializer<Entity>) redisTemplate.getValueSerializer();
                  byte[] key = keySerializer.serialize(table+id);
                  byte[] value = connection.get(key);
                  if (value == null) {
                       return null;
                  }
                  return valueSerializer.deserialize(value);
             }
        });
        return result;
		
	}

	@Override
	public boolean delete(final long id) {
		
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
                throws DataAccessException {
           RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
           byte[] key  = keySerializer.serialize(table+id);
           connection.del(key);
           return true;
      }
	 });
	}

	@Override
	public Entity update(final Entity entity) {
		 return redisTemplate.execute(new RedisCallback<Entity>() {
			public Entity doInRedis(RedisConnection connection)
                throws DataAccessException {
           RedisSerializer<String> keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
           RedisSerializer<Entity> valueSerializer = (RedisSerializer<Entity>) redisTemplate.getValueSerializer();
           byte[] key  = keySerializer.serialize(table+entity.getId());
           if(connection.del(key)==1) 
        	   return entity;
           return null;
      }
	 });
		
	}

}
