package com.cpw.desginpatten.CoR;

public class PrintRequest implements Request{
	
	private String type;
	private int id;

	@Override
	public int getRequestId() {
		return this.id;
		
	}

	@Override
	public String getRequestType() {
		return this.type;
		
	}

	@Override
	public void execute() {
		System.out.println("request id :"+this.getRequestId()+" request type: "+this.getRequestType());		
		
	}

	@Override
	public void setRequestId(int id) {
		this.id = id;
		
	}

	@Override
	public void setRequestType(String type) {
		this.type = type;
	}

}
