package com.cpw.StringAssociate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Mian {
	
	public static void main(String[] args) throws IOException {
		String s = "   ";
		String s1 = s.trim();
		System.out.println(s1);
		Properties pro = new Properties();
		InputStream innerIs = Mian.class.getClassLoader().getResourceAsStream("com/cpw/StringAssociate/test.properties");
		pro.load(innerIs);
		System.out.println(pro.getProperty("coverred"));
		File f = new File("D:/test/test.properties");
		InputStream fileips = new FileInputStream(f);
		pro.load(fileips);
		
		System.out.println(pro.getProperty("coverred"));
	}
}
