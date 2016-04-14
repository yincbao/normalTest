package com.cpw.spring.multi.context2;

import org.springframework.stereotype.Service;

@Service
public class BeanInContext2 {

	
	
	private String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
