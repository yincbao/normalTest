package com.cpw.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class DeepClone implements Cloneable {
	
	private int status;
	private TargetObj to;
	
	private Long lognVal;//ֱ�����´������󣬶��Ǹ�������
	
	private Date date;//ֱ�����´������󣬶��Ǹ�������
	
	
	public Long getLognVal() {
		return lognVal;
	}


	public void setLognVal(Long lognVal) {
		this.lognVal = lognVal;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public TargetObj getTo() {
		return to;
	}


	public void setTo(TargetObj to) {
		this.to = to;
	}


	public Object clone(){
		DeepClone obj = null;
		try {
			obj = (DeepClone)super.clone();
			obj.to = (TargetObj)to.clone();
		} catch (CloneNotSupportedException e) {
			try {
				throw new Exception(" sj");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return obj;
	} 
	
	//besides ，there is much more common， but not effetive way to acommplish using deserialize re-create object
	
	public static Object deepClone(Object source) throws IOException, ClassNotFoundException {
		// write object into stream
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(source);
		// read object from stream
		ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
		ObjectInputStream oi = new ObjectInputStream(bi);
		return oi.readObject();
	}

}
