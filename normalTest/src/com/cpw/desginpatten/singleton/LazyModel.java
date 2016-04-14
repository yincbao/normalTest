package com.cpw.desginpatten.singleton;

public class LazyModel {
	
	private static LazyModel singletonObj = null;
	public double status = 0;
	private LazyModel(){}
	private LazyModel(double status){
		this.status = status;
	}
	/*//not thread safe, more than one thread can new object and Assign value to only one reference---singletonObj 
	public static LazyModel getInstance(){
		
		if(singletonObj == null){
			singletonObj = new LazyModel(); 
		}
		return singletonObj;
	}
	*/
	
	// use object to control multi-thread dangerous, but only apply under jdk 5 or later.
	public static LazyModel getInstanceSynchronize(){
		if(singletonObj == null){
			
			synchronized(LazyModel.class){
				// must double check !!!!!!!!
				//if(singletonObj == null){
				
					singletonObj = new LazyModel(Math.random());
					try {
						Thread.sleep(5*1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//}
			}
		}
		return singletonObj;
	}
	
	// using static inner class, which can only be initialized once.
	private static class singletonHelper{
		static LazyModel singletonObjInner = new LazyModel();
	}
	public static LazyModel getInstanceInnerClass(){
		return singletonHelper.singletonObjInner;
	}
	
}
