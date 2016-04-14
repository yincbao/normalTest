package com.cpw.refect;

public class RefectUser extends AbtractUser{

	
	private  static Integer age;
	
	private Long money;
	
	private String name;

	public Long getMoney() {
		return money;
	}

	public  final  Integer getAge() {
		return age;
	}

	public static void setAge(Integer age) {
		RefectUser.age = age;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public String getName() {
		System.out.println("getName");
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
