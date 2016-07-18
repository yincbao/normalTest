package com.cpw;

import java.lang.reflect.ParameterizedType;

public class RawDao<T> {
	
	protected Class<T> clazz;

	@SuppressWarnings("unchecked")
	public RawDao() {
		@SuppressWarnings("rawtypes")
		Class clazz = getClass();

		while (clazz != Object.class) {
			java.lang.reflect.Type t = clazz.getGenericSuperclass();
			if (t instanceof ParameterizedType) {
				java.lang.reflect.Type[] args = ((ParameterizedType) t).getActualTypeArguments();
				if (args[0] instanceof Class) {
					this.clazz = (Class<T>) args[0];
					break;
				}
			}
			clazz = clazz.getSuperclass();
		}
	}
	
	public static void main(String args[]){
		RawDao<String> in = new RawDao<String>();
		System.out.println(in.clazz);
	}
}