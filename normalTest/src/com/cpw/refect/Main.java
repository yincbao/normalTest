package com.cpw.refect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Main {

	/**
	 * method2.invoke(null),第一个参数为类实例，如果方法是静态的可以为空，否则报错，nullpoint
	 * @param args
	 */
	public static void main(String[] args){
		try{
			Class<?> clazz = Class.forName("com.cpw.refect.RefectUser");
			/*Method method = clazz.getMethod("setId", int.class);
			method.invoke(clazz.newInstance(), 112);
			
			Method method2 = clazz.getMethod("getAge");*/
			//System.out.println(method2.invoke(null));
			//TestClass();
			parseClass(clazz);
			System.out.println("-------------------------------");
			parseClassViaBeans(clazz);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}
	
	
	public static void TestClass(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("int",1);
		map.put("boolean",true);
		map.put("String", "s");
		System.out.println(map.get("int").getClass().getName());
		
		System.out.println(map.get("boolean").getClass().getName());
		
		System.out.println(map.get("String").getClass().getName());
	}
	
	
	public static void parseClass(Class<?>  clazz) throws NoSuchMethodException, SecurityException{
		Field fields[] = clazz.getFields();
		for(Field file:fields){
			System.out.println(file.getName());
			Method read = clazz.getMethod("get"+file.getName().substring(0,1).toUpperCase()+file.getName().substring(1), Object.class);
			Method write = clazz.getMethod("set"+file.getName().substring(0,1).toUpperCase()+file.getName().substring(1), Object.class);
			String readm = read==null?"":read.getName();
            String writem = write==null?"":write.getName(); 
            System.out.println(file.getName()+ "  "+readm+"  "+writem);
		}
	}
	
	public static void parseClassViaBeans(Class<?>  clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		 try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			 PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			 for (int i = 0; i < propertyDescriptors.length; i++) {
	                PropertyDescriptor descriptor = propertyDescriptors[i];
	                String read = descriptor.getReadMethod()==null?"":descriptor.getReadMethod().getName();
	                String write = descriptor.getWriteMethod()==null?"":descriptor.getWriteMethod().getName();
	               System.out.println(descriptor.getName()+ "  "+read+"  "+descriptor.getReadMethod().invoke(clazz.newInstance(), new Object[0])+" |  "+write);
	               ;
	               
	          }
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
