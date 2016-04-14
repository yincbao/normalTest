package com.cpw.desginpatten.decorator;

public class DecorateRed extends Decorators {

	@Override
	public String provideColor() {
		
		return ShoseColors.RED.color();
	}

}
