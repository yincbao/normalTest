package com.cpw.cache.local;


public class CacheException  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String serviceName;
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public CacheException(String serviceName, String msg) {
		super(msg);
		this.serviceName = serviceName;
	}

	public CacheException(String serviceName, String msg, Throwable throwable) {
		super(msg, throwable);
		this.serviceName = serviceName;
	}

	public CacheException(String serviceName,Throwable throwable) {
		super(serviceName,throwable);
	}
}
