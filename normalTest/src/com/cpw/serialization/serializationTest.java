package com.cpw.serialization;

import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class serializationTest {

	InputStream is;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	DataOutput dop;

	public void serialize() {
		try {
			System.out.println("Authority		>>> " + this.getClass().getClassLoader().getResource("").getAuthority());
			System.out.println("Connect			>>> " + this.getClass().getClassLoader().getResource("").getContent());
			System.out.println("Default Port	>>> " + this.getClass().getClassLoader().getResource("").getDefaultPort());
			System.out.println("File			>>>	" + this.getClass().getClassLoader().getResource("").getFile());
			System.out.println("Protocol		>>> " + this.getClass().getClassLoader().getResource("").getProtocol());
			System.out.println("Query			>>>	" + this.getClass().getClassLoader().getResource("").getQuery());
			System.out.println("REF				>>>	" + this.getClass().getClassLoader().getResource("").getRef());
			System.out.println("UserInfo		>>>	" + this.getClass().getClassLoader().getResource("").getUserInfo());
			FileOutputStream f = new FileOutputStream("d:/serialization/tmp");
			ObjectOutputStream s = new ObjectOutputStream(f);
			willIBeCompiled d = new willIBeCompiled("a2334bc","can not serialize");
			d.setClazz(new class1("clazz"));

			s.writeObject("Today");
			s.writeObject(d);
			s.flush();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deserialize() {
		FileInputStream in;
		try {
			in = new FileInputStream("d:/serialization/tmp");

			ObjectInputStream s = new ObjectInputStream(in);
			String str = (String) s.readObject();
			willIBeCompiled obj = (willIBeCompiled) s.readObject();
			WeakReference<willIBeCompiled> weak = new WeakReference<willIBeCompiled>(obj);
			System.out.println("obj.equals(str)" + obj.equals(str));
			System.out.println(str);
			System.out.println("obj.getTest() " + obj.getTest());
			System.out.println(obj.getTrans());// transient, static不参与序列化，但是，本例能测试出来，是因为static变量存在于常量池，不属于任何一个实例。这里序列化和反序列话分开执行，中间关掉jvm一次就不一样了
			System.out.println(obj.getClazz());
			System.out.println(obj.hashCode());
			obj = null;

			System.gc();
			Thread.sleep(1000);
			System.out.println(weak.get());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * hash
	 * 
	 * @param args
	 */
	public void hash() {
		Set set = new HashSet();
		String str1 = "str1";
		String str2 = str1;
		str1.equals(str2);
		set.add("abc");
		set.add(str1);
		set.add(str2);
		str1 = "str2";
		set.add(str1);
		System.out.println("set size>>> " + set.size());
	}

	/**
	 * override willIBeCompiled equlas and hashCode method
	 * 
	 * @param args
	 */

	public void hashTest() {
		willIBeCompiled wbc = new willIBeCompiled("abc", "123");
		willIBeCompiled wbc1 = new willIBeCompiled("abc", "123");
		// wbc.setI(9);
		boolean bool = wbc.equals(wbc1) ? true : false;
		boolean bool1 = wbc == wbc1 ? true : false;
		System.out.println("instance 1's hashcode is >>> " + wbc.hashCode());
		System.out.println("instance 2's hashcode is >>> " + wbc1.hashCode());
		System.out.println(bool);
		System.out.println(bool1);
	}
	public static void main(String[] args) {
		mkfile("d:/serialization");
		serializationTest serializationtest = new serializationTest();
		serializationtest.serialize();
		
		serializationtest.deserialize();
		serializationtest.hash();
		serializationtest.hashTest();
		
		
		System.out.println(checkObject());



	}
	

	/**
	 * 
	 * 检查序列化后，对象是重新构造的还是原来对象的引用
	 *  true，传引用，
	 *  false，重新申请heap 内存构造的
	 * 
	 * 实际返回，fasle。
	 */
	public static boolean checkObject() {
		try {
			FileOutputStream f = new FileOutputStream("tmp");
			ObjectOutputStream s = new ObjectOutputStream(f);
			willIBeCompiled d = new willIBeCompiled("checkObject","can not serialize");
			s.writeObject(d);
			s.flush();
			s.close();

			Thread.sleep(100);

			 FileInputStream in = new FileInputStream("tmp");

			ObjectInputStream s1 = new ObjectInputStream(in);
			willIBeCompiled obj = (willIBeCompiled) s1.readObject();
			return d==obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	
	
	private static void mkfile(String path){
		File f = new File(path);
		if(!f.exists())
			f.mkdirs();
	}
}
