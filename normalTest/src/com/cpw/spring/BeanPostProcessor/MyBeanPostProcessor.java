package com.cpw.spring.BeanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object paramObject,String paramString) throws BeansException {
		if(paramObject instanceof Entity){
			
			System.out.println("after beanpost entered ");
			Entity entity = (Entity) paramObject ;
			entity.setName("beanpost after");
			System.out.println("quiting after beanpost");
		}
		return paramObject;
		
	}

	@Override
	public Object postProcessBeforeInitialization(Object paramObject,
			String paramString) throws BeansException {
if(paramObject instanceof Entity){
			
			System.out.println("before beanpost entered ");
			Entity entity = (Entity) paramObject ;
			entity.setName("beanpost before");
			System.out.println("quiting before beanpost");
		}
		return paramObject;
	}

}
