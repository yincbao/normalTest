package com.cpw.spring.propertyEditor;

import java.util.Date;

public class Company {
	protected Date establishDate;
	
	protected String name;
	
	protected String address;

	public Company(Date establishDate, String name, String address) {
		super();
		this.establishDate = establishDate;
		this.name = name;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Company [establishDate=" + establishDate + ", name=" + name + ", address=" + address + "]";
	}
	
	
	
}
