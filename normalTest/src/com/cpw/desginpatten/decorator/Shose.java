package com.cpw.desginpatten.decorator;

public abstract class Shose {
	
	public String madeIn;
	public int size;
	public String color;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public Decorators decorator;
	public String getMadeIn() {
		return madeIn;
	}
	public void setMadeIn(String madeIn) {
		this.madeIn = madeIn;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public abstract void decorateColor(Decorators decorator);
	}
