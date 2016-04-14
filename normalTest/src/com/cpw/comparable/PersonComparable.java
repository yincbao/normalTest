package com.cpw.comparable;

import java.util.Arrays;

public class PersonComparable implements Comparable<PersonComparable>{
	public PersonComparable(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	int id;
	String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(PersonComparable o) {
		return this.getId()-o.getId();
	}
	
	
	public static void main(String[] args) {
		PersonComparable[] pc = new PersonComparable[]{new PersonComparable(19,"zhangsan"),new PersonComparable(16,"Lisi"),new PersonComparable(25, "Wangwu")};
		Arrays.sort(pc);
		for(PersonComparable p:pc){
			System.out.println(p.getName()+"  "+p.getId());
		}
	}

}
