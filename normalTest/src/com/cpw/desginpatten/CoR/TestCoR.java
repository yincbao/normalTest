package com.cpw.desginpatten.CoR;

import org.junit.Test;

public class TestCoR {

	@Test
	public void test() {
		Request printReq = new PrintRequest();
		printReq.setRequestId(1);
		printReq.setRequestType("print");
		Request readReq = new PrintRequest();
		readReq.setRequestId(0);
		readReq.setRequestType("read");
		handle handle = new ConceretCoR();
		handle.handleRequest(readReq);
		handle.handleRequest(printReq);
		
		
	}

}
