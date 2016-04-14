package com.cpw.spring.BeanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
			throws BeansException {
		System.out.println("MyBeanFactoryPostProcessor entered!");
		BeanDefinition definition = arg0.getBeanDefinition("entity");
		MutablePropertyValues pv =  definition.getPropertyValues();  
		if(pv.contains("name"))  
			pv.addPropertyValue("name", "MyBeanFactoryPostProcessor");
		
	}

}
