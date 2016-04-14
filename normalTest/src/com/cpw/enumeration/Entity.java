package com.cpw.enumeration;

public class Entity {
	
	private String name;
	private int id;
	public String getName() {
		return name;
	}
	public Entity(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
