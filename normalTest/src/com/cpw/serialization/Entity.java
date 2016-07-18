package com.cpw.serialization;

import java.util.List;

/**
 * @ClassName Entity.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 15, 2016 1:13:53 PM
 *
 */
public class Entity implements java.io.Serializable{
	
	
	public Entity(int age, String name, List<String> tasks) {
		super();
		this.age = age;
		this.name = name;
		this.tasks = tasks;
	}

	private int age;
	
	private String name;
	
	private List<String> tasks;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTasks() {
		return tasks;
	}

	public void setTasks(List<String> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Entity [age=" + age + ", name=" + name + ", tasks=" + tasks + "]";
	}
	
	
}
