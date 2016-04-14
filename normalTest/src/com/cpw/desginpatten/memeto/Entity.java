package com.cpw.desginpatten.memeto;

public class Entity {
	
	private int state;
	private String name;
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Memeto cerateMemeto(){
		
		return new Memeto(this);
	}
	public Memeto cerateMemeto(Entity en){
		
		return new Memeto(en);
	}
	/**
	 * 从备份中恢复状态
	 * @param m
	 */
	public void resroteMemeto(Memeto m){
		this.state = m.getState();
		this.name = m.getName();
	}

}
