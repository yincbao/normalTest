package com.cpw.desginpatten.CoR;

public interface Request {
	
	public int getRequestId();
	public String getRequestType();
	public void setRequestId(int id);
	public void setRequestType(String type);
	public void execute();
	
}
