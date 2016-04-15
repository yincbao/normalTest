package com.cpw.spring.propertyEditor;

public class BossBeanInfo {
	
	private String name;
	
	private int age;
	
	private Company company;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "BossBeanInfo [name=" + name + ", age=" + age + ", company=" + company + "]";
	}
	
	

}
