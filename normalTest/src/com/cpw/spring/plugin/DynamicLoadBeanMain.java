package com.cpw.spring.plugin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DynamicLoadBeanMain {
	
	/**
	 *  new ClassPathXmlApplicationContext( new String[]{"com/cpw/spring/plugin/spring.xml"});
	 *  �ͻᴴ��һ��������applicatonContext ���󣬶����ǵ����ģ�
	 *  ����û�а취ʵ�ֲ������
	 * @param args
	 */
		public static void main1(String[] args) {
			
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
	                new String[]{"com/cpw/spring/plugin/spring.xml"});
			
			Bean local = (Bean) ctx.getBean("locaBean");
			local.print();
			
			ApplicationContext ctx1 = new ClassPathXmlApplicationContext(
	                new String[]{"com/cpw/spring/plugin/spring-plugin.xml","com/cpw/spring/plugin/spring.xml"});
			Bean local1 = (Bean) ctx1.getBean("locaBean");
			Bean plugin = (Bean) ctx1.getBean("pluginBean");
			Bean plugin1 = (Bean) ctx.getBean("pluginBean");
			local1.print();
			plugin.print();
			plugin1.print();
		}
		
		public static void main(String[] args) {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
	                new String[]{"com/cpw/spring/plugin/spring.xml"});
			DynamicLoadBean loader = (DynamicLoadBean) ctx.getBean("dynamicLoadBean");
			loader.loadBean("com/cpw/spring/plugin/spring-plugin.xml");
			Bean plugin = (Bean) ctx.getBean("pluginBean");		
			plugin.print();
		}

}
