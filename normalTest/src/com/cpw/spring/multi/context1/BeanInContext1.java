package com.cpw.spring.multi.context1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cpw.spring.multi.context2.BeanInContext2;

@Service
public class BeanInContext1 {

	@Autowired
	BeanInContext2 sc;
	private String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
