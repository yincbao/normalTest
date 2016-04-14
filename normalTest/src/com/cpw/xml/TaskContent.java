package com.cpw.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

public class TaskContent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appName;
	
	private String additionalParams;
	
	@XmlElementWrapper
	@XmlElement(name = "app") 
	private List<Application> appList;
	
	
	public String getAppName() {
		return appName;
	}
	@XmlTransient
	public List<Application> getAppList() {
		return appList;
	}

	public void setAppList(List<Application> appList) {
		this.appList = appList;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAdditionalParams() {
		return additionalParams;
	}

	public void setAdditionalParams(String additionalParams) {
		this.additionalParams = additionalParams;
	}

	public TaskContent(String appName, String additionalParams) {
		super();
		this.appName = appName;
		this.additionalParams = additionalParams;
	}

	public TaskContent() {
		super();
	}
	
}
