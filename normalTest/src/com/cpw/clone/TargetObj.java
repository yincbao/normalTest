package com.cpw.clone;

public class TargetObj implements Cloneable {
	
	public String flag;

	public TargetObj(String flag) {
		super();
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public Object clone(){
		TargetObj to = null;
		try {
			to = (TargetObj) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return to;
	}
}
