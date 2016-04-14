package com.cpw.json;

import com.alibaba.fastjson.annotation.JSONField;

public class A implements java.io.Serializable{
	  @JSONField(serialize = false) 
	private B b;
	private String str;
	public B getB() {
		return b;
	}
	public void setB(B b) {
		this.b = b;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	
}
