package com.cpw.cache.local;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manages all the Caches in the system.
 *
 */
public class CacheManager {
	private static Log logger = LogFactory.getLog( CacheManager.class.getName() );
	private static CacheManager instance = new CacheManager();
	private Map<String, Cache> cacheMap=new HashMap<String, Cache>();
	private CachePoller cachePoller;
	
	public static CacheManager getInstance() {
		return instance;
	}
	
	private CacheManager() {
		cachePoller = new CachePoller(this); 
	}
	
	public void addCache(Cache cache) {
		logger.info("Adding Cache:" + cache.getCacheName());
		cacheMap.put(cache.getCacheName(), cache);
	}
	
	public Cache getCache(String name) {
		return cacheMap.get(name);
	}
	
    void maintainAllCache() {
    	logger.info("Removing expired objects from Cache. Interval in seconds:" + cachePoller.getPeriodInSeconds());
    	Collection<Cache> caches = cacheMap.values();
    	for(Cache cache : caches) {
    		cache.removeExpiredObjects();
    	}
    }
    
	public void setPollingPeriodInSeconds(int periodInSeconds) {
		cachePoller.setPeriodInSeconds(periodInSeconds);
	}
	
	static Lock lock = new ReentrantLock();
	public static void set(String cacheGroup,String key,Object value){
		Cache cache = CacheManager.getInstance().getCache(cacheGroup);
		if(cache==null){
			cache = new Cache(cacheGroup,60*24*365,10000);
			CacheManager.getInstance().addCache(cache);
		}
			
		cache.put(new CacheElement(key,value));
	}
	
	public static Object get(String cacheGroup,String key){
		Cache cache = CacheManager.getInstance().getCache(cacheGroup);
		if(cache==null)
			return null;
		else
			return cache.getCacheElementValue(key);
	}
}

/**
 * 
 * Responsible for Polling the CacheManager and cleaning the cache periodically
 */
class CachePoller {
	private static Log logger = LogFactory.getLog( CachePoller.class.getName() );
	private CacheManager cacheManager;
	private ScheduledFuture<?> cleanerHandle=null;
	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);
	//Define the cleaner start delay time and the periodic interval  
	private long initialDelayInSeconds = 60;
	private long periodInSeconds = 300;	

	CachePoller(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
		this.init();
	}

	private void init() {
		if(cleanerHandle !=null) {
			//cancel the current scheduled task
			logger.info("Cancelling the current CachePoller task");
			cleanerHandle.cancel(false);
		}		
		final Runnable maintainer = new Runnable() {
			public void run() {
				try {
					cacheManager.maintainAllCache();
				}
				catch(Throwable e) {
					logger.error("Error encountered when cleaning cache: " + e.toString(),e);
				}
			}
		};
		logger.info("Scheduling a new CachePoller task with period:" + periodInSeconds);
		cleanerHandle = scheduler.scheduleAtFixedRate(maintainer, initialDelayInSeconds,
						periodInSeconds, TimeUnit.SECONDS);
	}

	public long getPeriodInSeconds() {
		return periodInSeconds;
	}
	
	public void setPeriodInSeconds(int periodInSeconds) {
		this.periodInSeconds = periodInSeconds;
		//Initialize Poller again as the period has changed
		this.init();
	}	
}

