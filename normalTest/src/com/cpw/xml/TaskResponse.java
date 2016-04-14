package com.cpw.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
@XmlRootElement(name="naTaskResp")
public class TaskResponse {

	private String taskCmd;
	
	private Integer errorCode;
	
	private String errorDetail;
	@XmlElementWrapper
	@XmlElement(name = "app") 
	private List<Application> appList;
	
	@XmlTransient
	public List<Application> getAppList() {
		return appList;
	}

	public void setAppList(List<Application> appList) {
		this.appList = appList;
	}

	public String getTaskCmd() {
		return taskCmd;
	}

	public void setTaskCmd(String taskCmd) {
		this.taskCmd = taskCmd;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public TaskResponse(String taskCmd, Integer errorCode, String errorDetail) {
		super();
		this.taskCmd = taskCmd;
		this.errorCode = errorCode;
		this.errorDetail = errorDetail;
	}

	public TaskResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
