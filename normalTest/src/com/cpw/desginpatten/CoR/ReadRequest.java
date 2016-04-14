package com.cpw.desginpatten.CoR;

public class ReadRequest implements Request {
	private int id;
	private String type;

	@Override
	public int getRequestId() {
		return this.id;
	}

	@Override
	public String getRequestType() {
		return this.type;
	}

	@Override
	public void setRequestId(int id) {
		this.id = id;

	}

	@Override
	public void setRequestType(String type) {
		this.type = type;

	}

	@Override
	public void execute() {
		System.out.println("request id :"+this.getRequestId()+" request type: "+this.getRequestType());	

	}

}
