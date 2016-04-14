package com.cpw.serialization.externalizable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * 目的，了解接口Externalizable
 * Externalizable 继承了Serializable。区别于Serializable。他提供了writeExternal、writeExternal两个接口方法。需要实现。
 * 在最宠io.readObject 或者writeObject的时候，调用。
 * Externalizable优势在于，暴露了序列化this 的细节，java程序员可以自己在writeObject选择需要序列化的属性，本例就故意忽略掉了id，而不是依靠transient或者把对象属性设为static
 *  
 * 缺点在于：readObject的顺序要和writeObject顺序完全一样，且么有任何检查机制，完全靠手工，对于一个有几个属性的对象来说这是不可想象的工作量
 * 
 * 
 * ClassName: ExtEntity
 * @description
 * @author yin_changbao
 * @Date   Aug 18, 2015
 *
 */
public class ExtEntity implements Externalizable {
	
	
	private  String name;
	
	private int id;
	
	private Double money;
	
	public ExtEntity(String name, int id, double money) {
		this.name = name;
		this.id = id;
		this.money = money;
	}

	public String getName() {
		return name;
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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.name);
		out.writeObject(this.money);

	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		//读的顺序要和谐的顺序一致。否则可能出现张冠李戴的情况
		this.name = (String) in.readObject();
		this.money = (Double) in.readObject();
	}

	@Override
	public String toString() {
		return "ExtEntity [name=" + name + ", id=" + id + ", money=" + money + "]";
	}

}
