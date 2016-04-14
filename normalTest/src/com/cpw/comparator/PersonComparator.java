package com.cpw.comparator;

import java.util.Arrays;

public class PersonComparator {
	
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
	
	public PersonComparator(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public static void main(String[] args) {
		PersonComparator[] pc = new PersonComparator[]{new PersonComparator(26,"zhangsan"),new PersonComparator(17,"Lisi"),new PersonComparator(23,"wangwu")};
		MyComparator mc = new MyComparator();
		Arrays.sort(pc, mc);
		for(PersonComparator p:pc){
			System.out.println(p.getName()+"  "+p.getId());
		}
	}

}
