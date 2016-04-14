package com.cpw.cache.local;

/**
 * An element of a cache.
 * This contains a key, value pair along with 
 * other attributes 
 *
 */
public class CacheElement {
	private Object key;
	private Object value;
	private long creationTime;
	private long expiryTime;

	public CacheElement(Object key, Object value) {
		this.key = key;
		this.value = value;
	}

	public long getCreationTime() {
		return creationTime;
	}

	void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public Object getKey() {
		return key;
	}

	void setKey(Object key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	void setValue(Object value) {
		this.value = value;
	}
}
