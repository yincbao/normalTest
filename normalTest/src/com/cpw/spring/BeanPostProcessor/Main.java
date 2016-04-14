package com.cpw.spring.BeanPostProcessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[]{"com/cpw/spring/BeanPostProcessor/spring.xml"});
		Entity bean =(Entity)ctx.getBean("entity");
		System.out.println("------------------main result-----------");
		System.out.println("name:"+bean.getName()+"  id:"+bean.getId()); 
	}
}
