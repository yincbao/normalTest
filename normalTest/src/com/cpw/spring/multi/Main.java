package com.cpw.spring.multi;

import java.io.FileNotFoundException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * 此例，只为验证，springbean在不同容器中不做特殊处理是不可以共享的，ctx2.getBean("bean1");就报错了，
 * 无论是xml配置，还是@service都一样。
 * 可见 spring和springMVC两个beanDefinition肯定做了特殊处理了。
 * ClassName: Main
 * @description
 * @author yin_changbao
 * @Date   Aug 6, 2015
 *
 */
public class Main {
	
	
	public static void main(String arg[]) throws FileNotFoundException{
		Log4jConfigurer.initLogging("classpath:com/log4j.properties");
		ApplicationContext ctx1 = new ClassPathXmlApplicationContext(
                new String[]{"com/cpw/spring/multi/context1/ctx1.xml"});
		
		
		ApplicationContext ctx2 = new ClassPathXmlApplicationContext(
                new String[]{"com/cpw/spring/multi/context2/ctx2.xml"});
		ctx1.getBean("bean1");
		ctx2.getBean("bean1");
	}

}
