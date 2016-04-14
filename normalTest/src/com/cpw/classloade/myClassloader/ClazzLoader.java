package com.cpw.classloade.myClassloader;

import java.io.Closeable;
import java.io.IOException;
import java.security.SecureClassLoader;

public class ClazzLoader extends SecureClassLoader implements Closeable  {

	@Override
	public void close() throws IOException {
		System.out.println("close");
		
	}
	
}

