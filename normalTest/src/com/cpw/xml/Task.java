package com.cpw.xml;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="naTask")
public class Task implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskCmd;
	private TaskContent taskContent;
	public String getTaskCmd() {
		return taskCmd;
	}
	public void setTaskCmd(String taskCmd) {
		this.taskCmd = taskCmd;
	}
	public TaskContent getTaskContent() {
		return taskContent;
	}
	public void setTaskContent(TaskContent taskContent) {
		this.taskContent = taskContent;
	}
	public Task() {
		super();
	}
	public Task(String taskCmd, TaskContent taskContent) {
		super();
		this.taskCmd = taskCmd;
		this.taskContent = taskContent;
	}
	public Task(String taskCmd) {
		this.taskCmd = taskCmd; 
	}
	
}
