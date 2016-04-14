package com.cpw.desginpatten.decorator;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestDecorator {
	
	public static Shose madeInChina;
	public static Shose madeInCongo;
	public static Decorators redDecorator;
	public static Decorators greenDecorator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		madeInChina		 = new ShoseMadeInChina();
		madeInCongo 	 = new ShoseMadeInCongo();
		redDecorator	 = new DecorateRed();
		greenDecorator	 = new DecorateGreen();
	}

	@Test
	public void testShoseMadeInChina() {
		madeInChina.decorateColor(greenDecorator);
		madeInChina.decorateColor(redDecorator);
	}
	@Test
	public void testShoseMadeInCongo(){
		madeInCongo.decorateColor(greenDecorator);
		madeInCongo.decorateColor(redDecorator);
	}

}
