package com.cpw.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageSourceTest {
 
	public static void main(String[] args) throws UnsupportedEncodingException { 
		Locale l = new Locale("zh");
		System.out.println(l.getCountry());
		   ApplicationContext ctx = new ClassPathXmlApplicationContext("com/cpw/i18n/testMessage.xml");  
		   MessageSource messageSource =  (MessageSource) ctx.getBean("messageSource");
		   String message1 = messageSource.getMessage("register.smartphone.account.mail.content", new Object[]{"http://10.20.71.50:8080/ubi-webportal/verifyAccount?username=yin_changbao@hoperun.com&type=app","http://10.20.71.50:8080/ubi-webportal/verifyAccount?username=yin_changbao@hoperun.com&type=app"}, new Locale("zh_CN"));
		   String message2 = messageSource.getMessage("accident121", new Object[]{"ycl"}, "haha", Locale.SIMPLIFIED_CHINESE);
		   System.out.println(message1);
		   System.out.println(message2);
		   String te= "<html>中文";
		   System.out.println(new String(message1.getBytes("ISO-8859-1"),"UTF-8"));
	}

}

