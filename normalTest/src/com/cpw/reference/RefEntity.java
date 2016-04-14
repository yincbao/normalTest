package com.cpw.reference;

public class RefEntity {

	
	
	private long id;
	
	private String name;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RefEntity [id=" + id + ", name=" + name + "]";
	}

	public RefEntity(long i, String name) {
		super();
		this.id = i;
		this.name = name;
	}
	
	
	
}
