package com.cpw.desginpatten.decorator;

public class ShoseMadeInCongo extends Shose{

	@Override
	public void decorateColor(Decorators decorator) {
		String color = decorator.provideColor();
		System.out.println("shose made in Congo print color "+ color);
		this.color = color;
	}
	
}
