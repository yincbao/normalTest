package com.cpw.serialization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 反序列话的对象的finalize方法也是被注册到finalzer类中，gc前执行的。
 * 
 * 
 * 2.java.io.InvalidClassException异常原因：
 * 对象已经被序列化并且存储起来了，以后去改变这个class文件，且这个class 文件没有指定serialVersionUID。重新编译运行会报以上错误
 * 原因很简单，serialVersionUID不一致了（java 默认不指定serialVersionUID的时候，会以奇Class类实例的hashcode为serialVersionUID
 * class文件变了，Class对象hashcode肯定也跟着变了，原来的serialVersionUID和现在的不等了，）
 * 分布式缓存系统、如memedcahce 经常会有这个问题，memcache机器没有重启。但是cachedBean修改过了。又没有指定默认的serialVersionUID。
 * 
 * 
 * 3.NotSerializableException 说明class文件没有继承Serializable接口
 * 如果指定了，顶多也就是新加的属性取不到值。
 * 
 * 
 * Serializable 没有包含任何方法，这样的接口为标识接口、上了“可以进行序列化”的标签给编译器指示
 * @description
 * @author yin_changbao
 * @Date   Aug 18, 2015
 *
 */
public class willIBeCompiled implements Serializable {
	
	public willIBeCompiled(String test, String trans) {
		super();
		this.test = test;
		this.trans = trans;
	}

	public  final String test;
	private transient String trans;
	private int i; 
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	private int j;

	private static class1 clazz;

	public class1 getClazz() {
		return clazz;
	}

	public void setClazz(class1 clazz) {
		this.clazz = clazz;
	}

	public String getTrans() {
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public String getTest() {
		return test;
	}

	
	public int hashCode(){
		int hashcode = 17;
		hashcode = 31*hashcode+this.i;
		hashcode = 31*hashcode + this.j;
		return hashcode;
	}
	public void test(){
		Set LHS = new LinkedHashSet();
		Set hs = new HashSet();
		Hashtable hashTable = new Hashtable();
		Map hm = new HashMap();
	}
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(this==obj)
			return true;
		if(this.hashCode()!=obj.hashCode()){
			return false;
		}
		if(obj instanceof willIBeCompiled){
			willIBeCompiled tmp = (willIBeCompiled)obj;
			if(tmp.test == this.test && tmp.trans == this.trans)
				return true;
		}
		return false;
	}
	
	@Override
	public void finalize() {
		System.out.println("finalizable");
	}
	

}
