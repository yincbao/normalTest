package com.cpw.clone;

public class PhatomClone1 implements Cloneable {
	
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
		Object pc = null;
		try {
			pc = (PhatomClone) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pc;
	}
}
