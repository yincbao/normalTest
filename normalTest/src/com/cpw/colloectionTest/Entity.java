package com.cpw.colloectionTest;


public class Entity implements Comparable<Entity>{
	int id;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public Entity(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int hashCode(){
		 
		int i=13;
		i = i*7+this.id;
		i = i*7+this.id;
		return i;
	}
	
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(this==obj)
			return true;
		if(this.hashCode()!=obj.hashCode()){
			return false;
		}
		if(obj instanceof Entity){
			Entity tmp = (Entity)obj;
			if(tmp.id == this.id && tmp.name == this.name)
				return true;
		}
		return false;
		
	}
	@Override
	public int compareTo(Entity o) {
		int i =-(this.id-o.id);
		return i;
	}
}
