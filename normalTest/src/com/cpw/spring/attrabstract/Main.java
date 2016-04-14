package com.cpw.spring.attrabstract;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[]{"com/cpw/spring/attrabstract/spring.xml"});
		SEntity ae = (SEntity) ctx.getBean("sentity");
		System.out.println(ae.getName());
	}
}
