package com.cpw.desginpatten.singleton;

public class HungryModel {
	
	/**
	 * describe word is static, so singletonObj is shared by all instance.
	 */
	private static HungryModel singletonObj= new HungryModel((int)Math.random());
	
	private int status = 0;
	
	public int getStatus() {
		return status;
	}
	private HungryModel(){}
	private HungryModel(int status){
		this.status = status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static HungryModel getInstance(){
		return singletonObj;
	}

}
