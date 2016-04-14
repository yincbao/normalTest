package com.cpw.clone;

public class PhatomClone implements Cloneable {
	
	private int canBeClone;
	
	private TargetObj targetObj;
	
	public int getCanBeClone() {
		return canBeClone;
	}

	public void setCanBeClone(int canBeClone) {
		this.canBeClone = canBeClone;
	}

	public TargetObj getTargetObj() {
		return targetObj;
	}

	public void setTargetObj(TargetObj targetObj) {
		this.targetObj = targetObj;
	}

	public Object clone(){
		PhatomClone1 pc = null;
		try {
			pc =  (PhatomClone1) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pc;
	}
}
