package com.cpw.test.file;

import java.io.File;
import java.io.IOException;

public class IteratorFiles {
	
	public static void Recursion(File dir) throws IOException{
		File files[] = dir.listFiles();
		for(File file:files){
			if(file.isDirectory()){
				Recursion(file);
			}else{
				System.out.println("get File named: "+file.getParentFile().getAbsolutePath());
			}
		}
		
	}
	public static void main(String[] args) throws IOException {
		Recursion(new File("D:/"));
	}

}
