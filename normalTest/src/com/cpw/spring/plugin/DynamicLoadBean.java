package com.cpw.spring.plugin;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

public class DynamicLoadBean implements ApplicationContextAware {

	private ConfigurableApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.context = (ConfigurableApplicationContext) arg0;
	}

	public ApplicationContext getApplicationContext() {
		return context;
	}

	public void loadBean(String configLocationString) {
		
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) context.getBeanFactory());
		beanDefinitionReader.setResourceLoader(getApplicationContext());
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(
				getApplicationContext()));

		try {
			String[] configLocations = new String[] { configLocationString };
			for (int i = 0; i < configLocations.length; i++)
				beanDefinitionReader
						.loadBeanDefinitions(getApplicationContext()
								.getResources(configLocations[i]));
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
