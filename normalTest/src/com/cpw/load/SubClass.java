package com.cpw.load;


public class SubClass extends AbstracClass{
	
	
	
	public static void main(String arg[]){
		AbstracClass sc = new AbstracClass();
		System.out.println(isExtended("java.lang.a",sc.getClass()));
		System.out.println(isImplementation("java.io.Serializable",sc.getClass()));
		
	}
	
	private static boolean isImplementation(String fullNmae,Class<?> clazz){
		Class<?>[] intfs = clazz.getInterfaces();
		if(intfs!=null&&intfs.length>0){
			for(Class<?> clz:intfs){
				if(clz.getName().equals(fullNmae))
					return true;
				else{
					return isImplementation( fullNmae,clz);
				}
			}
		}
		return false;
	}
	
	/**
	 * check if clazz is an subclass of super class, in a extended tree.
	 * @param intf
	 * @param clazz
	 * @return
	 */
	private static boolean isExtended(String fullName,Class<?> subClass){
		Class<?> superC = subClass.getSuperclass();
		if(superC!=null){
			if(superC.getName().equals(fullName))
				return true;
			else{
				return isExtended(fullName,superC);
			}
		}
		return false;
	}
	
	
	

}
