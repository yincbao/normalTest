package com.cpw.json.gson;

import java.util.Date;

/**
 * demon 类，演示如何使用com.hoperun.ubi.framework.helper.annotation.GsonDataFotmat注解。
 * 实现对业务对象制定数据类型（目前实现了Date类型）格式化
 * 注意：因为gson TypeAdapter限制，适配器会影响所有同一类型的对象属性format
 * ClassName: TestBean
 * @description
 * @author yin_changbao
 * @Date   Oct 16, 2015
 *
 */
public class TestBean {

	private Date  createDate;
	
	private Long id;
	
	private String name;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestBean(Date createDate, Long id, String name) {
		super();
		this.createDate = createDate;
		this.id = id;
		this.name = name;
	}

	public TestBean() {
		super();
	}
	
	
	
	public static void main(String args[]){
		TestBean tb = new TestBean(new Date(),1000000L,"tbName");
		System.out.println(JsonHelper.toJson(tb));
		System.out.println(JsonHelper.fromJson("{\"createDate\":\"2015-10-16\",\"id\":1000000,\"name\":\"tbName\"}", TestBean.class));
	}
	
}
