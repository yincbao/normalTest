package com.cpw.mongodb.spring;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.cpw.cache.util.StringUtil;
import com.mongodb.WriteResult;

public class MongodbDaoSupport {

	private static final Log logger = LogFactory.getLog(MongodbDaoSupport.class);

	protected MongoTemplate mongoTemplate;

	public <T> T save(T entity, String collectionName) {
		if (logger.isDebugEnabled())
			logger.debug("[mongodb] trying save entity: " + entity + " into collection: " + collectionName);
		else
			logger.info("[mongodb] trying save entity");
		if (StringUtil.isEmpty(collectionName))
			mongoTemplate.save(entity);
		else
			mongoTemplate.save(entity, collectionName);
		return entity;
	}

	public <T> void delete(T entity, String collectionName) {
		if (StringUtil.isEmpty(collectionName))
			mongoTemplate.remove(entity);
		else
			mongoTemplate.remove(entity, collectionName);
	}

	public <T> WriteResult update(Object id, Class<T> entityClass, Map<String, Object> params, String collectionName) {
		if (params == null || params.isEmpty())
			return null;
		Update update = null;
		for (String key : params.keySet()) {
			if (update == null)
				update = Update.update(key, params.get(key));
			else
				update.set(key, params.get(key));
		}
		if (StringUtil.isEmpty(collectionName))
			return mongoTemplate.upsert(new Query(Criteria.where("id").is(id)), update, entityClass);
		else
			return mongoTemplate.upsert(new Query(Criteria.where("id").is(id)), update, entityClass, collectionName);
	}

	public <T> List<T> findAll(Class<T> clazz, String collectionName) {
		if (StringUtil.isEmpty(collectionName))
			return mongoTemplate.findAll(clazz);
		else
			return mongoTemplate.findAll(clazz, collectionName);
	}
	
	//public 

}
