package com.cpw.desginpatten.memeto;

import org.junit.Before;
import org.junit.Test;

public class TestMemeto {

	@Before
	public void  sdfgioDown() throws Exception {
	}

	@Test
	public void test() {
		Entity entity = new Entity();
		entity.setName("Orignal data");
		entity.setState(0);
		Memeto m = entity.cerateMemeto(entity);
		entity.setName("new data");
		entity.setState(1);
		System.out.println("new data: "+entity.getName()+" state:"+entity.getState());
		entity.resroteMemeto(m);
		System.out.println("restroing from Memeto: "+entity.getName()+" state: "+entity.getState());
	}

}
