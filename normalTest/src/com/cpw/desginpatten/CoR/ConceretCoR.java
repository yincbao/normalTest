package com.cpw.desginpatten.CoR;


public class ConceretCoR implements handle {
	private Request request;
	

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void handleRequest(Request request){
		if(request instanceof PrintRequest){
			request.execute();
		}else if(request instanceof ReadRequest){
			request.execute();
		}else{
			this.handleRequest(request);
		}
	}
	
}
