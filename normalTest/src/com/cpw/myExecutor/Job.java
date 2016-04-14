package com.cpw.myExecutor;

public interface Job {
	public String getStatus();

	public void setStatus(String status);

	public String getID();

	public void setID(String jobid);
}
