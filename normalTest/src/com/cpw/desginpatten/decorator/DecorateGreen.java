package com.cpw.desginpatten.decorator;

public class DecorateGreen extends Decorators {

	@Override
	public String provideColor() {
		return ShoseColors.Green.color();
	}

}
