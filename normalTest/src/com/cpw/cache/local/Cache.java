package com.cpw.cache.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class manages CacheElements for a cache. 
 *
 */
public class Cache {
	private static Log logger = LogFactory.getLog( Cache.class.getName() );
	//The time period before a cache expires
	private long timeToLiveInSeconds;
	private String cacheName;
	//Maximum number of elements in the cache. When max elements is reached,
	//new element are not added to the cache.
	private int maxElements;
	private Map<Object, CacheElement> elementMap=new HashMap<Object, CacheElement>();
	
	public Cache(String cacheName,long timeToLiveInSeconds, int maxElements){
		this.cacheName = cacheName;
		this.timeToLiveInSeconds = timeToLiveInSeconds;
		this.maxElements = maxElements;
	}

	public String getCacheName() {
		return cacheName;
	}

    public long getTimeToLiveInSeconds() {
		return timeToLiveInSeconds;
	}

	public void put(CacheElement cacheElement) {
		if(elementMap.size() > maxElements) {
			logger.info("Not adding element to cache " + this.getCacheName() + " as cache has reached the max capacity");
		}
		else {
			logger.debug("Adding element to Cache: " + this.getCacheName() + ". Key=" + cacheElement.getKey());
			elementMap.put(cacheElement.getKey(), cacheElement);
			long creationTime = System.currentTimeMillis();
			cacheElement.setCreationTime(creationTime);
			cacheElement.setExpiryTime(creationTime + (timeToLiveInSeconds * 1000));
		}
	}
	
	public CacheElement get(Object key) {
		return elementMap.get(key);
	}

	public Object getCacheElementValue(Object key) {
		CacheElement element =  elementMap.get(key);
		if(element!=null) {
			return element.getValue();
		}
		else {
			return null;
		}
	}
	
	public List<CacheElement> getAllCachedElements() {
		return new ArrayList<CacheElement>(elementMap.values());
	}
	
	protected void removeExpiredObjects() {
		Set<Object> keys = elementMap.keySet();
		List<Object> expiredKeys = new ArrayList<Object>();
		for(Object key:keys) {
			CacheElement cacheElement = elementMap.get(key);
			if(System.currentTimeMillis() > cacheElement.getExpiryTime()) {
				expiredKeys.add(cacheElement.getKey());
			}
		}
		for(Object key : expiredKeys) {
			logger.debug("Removing expired object from cache= " + this.getCacheName() + ". Key=" + key);
			elementMap.remove(key);
		}
	}
}
