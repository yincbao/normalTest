package com.cpw.serialization.externalizable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
	
	public static void main(String args[]){
		serialize(new ExtEntity("Name:zhangsan",1,9999999999999.99));
		System.out.println(deserialize());
	}

	private static ExtEntity deserialize() {
		FileInputStream in;
		ObjectInputStream s = null;
		try {
			in = new FileInputStream("d:/serialization/tmp1");

			s = new ObjectInputStream(in);
			return (ExtEntity) s.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static void serialize(ExtEntity extEntity) {
		ObjectOutputStream s = null;
		try {
			FileOutputStream f = new FileOutputStream("d:/serialization/tmp1");
			s = new ObjectOutputStream(f);

			s.writeObject(extEntity);
			s.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
