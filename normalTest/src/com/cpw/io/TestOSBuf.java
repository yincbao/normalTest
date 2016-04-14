package com.cpw.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
* @ClassName: TestOSBuf 
* @Description: 
* @author: Yin-Changbao
* @date: Mar 17, 2016 11:15:22 AM 
*
 */
public class TestOSBuf {
	
	public void readFile(String filePath){
		File f = new File(filePath);
		InputStream in = null;
		try {
			in = new FileInputStream(f);
			int bsize = 1024;
			byte[] buff = new byte[bsize];
			
			while(bsize<=in.read(buff))
				System.out.println(new String(buff));
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public void readFileViaBuff(String filePath){
		File f = new File(filePath);
		BufferedInputStream bin = null;
		try {
			bin = new BufferedInputStream(new FileInputStream(f));
			int bsize = 1024;
			byte[] buff = new byte[bsize];
			while(bsize<=bin.read(buff))
				System.out.println(new String(buff));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(bin!=null)
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static String tinyFileRead(File file) {
		BufferedReader in = null;
		try {
			if (!file.exists())
				file.createNewFile();
			in = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while ((temp = in.readLine()) != null)
				sb.append(temp);
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String args[]){
		new TestOSBuf().readFileViaBuff("D:/china-latest.osm");
	}
}
