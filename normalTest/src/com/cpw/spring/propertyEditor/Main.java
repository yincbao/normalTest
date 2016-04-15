package com.cpw.spring.propertyEditor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cpw.spring.propertyEditor.jdk.BossEditorConfigurer;
import com.cpw.spring.propertyEditor.jdk.BossPropertiesEditor;
import com.cpw.spring.propertyEditor.spring.BeanDefine;

public class Main {

	
	public static void main(String args[]){
		
		//无Spring 环境下模拟实现
		
		Map<String ,String> map = new HashMap<String,String>();
		map.put(BossBeanInfo.class.getName(), "Paul,30,2017-10-01 00:00:01.000,CPW corp,Nanjing Jiangsu China");
		
		BossEditorConfigurer bec = new BossEditorConfigurer();
		bec.setPropertyEditor(new BossPropertiesEditor());
		try {
			System.out.println(((BossBeanInfo)bec.parser(map)[0]).toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//spring实现
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[]{"com/cpw/spring/propertyEditor/spring/spring.xml"});
		System.out.println(((BeanDefine)ctx.getBean("boss")).getValue());
	}
}
