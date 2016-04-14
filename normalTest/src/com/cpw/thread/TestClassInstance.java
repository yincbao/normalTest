package com.cpw.thread;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *  synchronized(clazz.class)
 *  申明锁加载了对象Class上。与synchronized(object)并无差异
 *  唯一的差异在于，java类的加载机制克制，Class对象在一个classloader环境下，是单例的。而对象我可以任意创建。synchronized后面的对象一般为临界资源
 *  所以，synchronized(class)更加的简单粗暴。但是如果有写状态值需要object获取，显然class就不行了
 * ClassName: TestClassInstance
 * @description
 * @author yin_changbao
 * @Date   Aug 17, 2015
 *
 */
public class TestClassInstance{
	public static void main(String[] args)throws IOException{
		MyThread t1 = new MyThread();
		YourThread t2 = new YourThread(t1);
		t1.start();
		t2.start();
	}
}

class MyThread extends Thread
{
	private static FileOutputStream out;
	static{
		try{
			out = new FileOutputStream("d:/1.txt");
		}catch(IOException e){
		}
	}
	public void run(){
		try{
			printlog();
		}catch(IOException e){
		}	
	}

	public  static void printlog()throws IOException{
		OutputStreamWriter ou = new OutputStreamWriter(out);
		synchronized(MyThread.class){
		for (int i = 0 ; i < 1000 ; i++ )
		{
			ou.write("*");
		}
		ou.flush();
		}
	}

	public  void printmsg()throws IOException{
		OutputStreamWriter ou = new OutputStreamWriter(out);
		synchronized(YourThread.class){
		for (int i = 0 ; i < 1000; i++ ){
			
			ou.write("@");
			
			synchronized(YourThread.class){
			}
		}
		ou.flush();
		}
	}
}
class YourThread extends Thread
{
	private MyThread myThread;
	public YourThread(MyThread m){
		this.myThread = m;
	}

	public void run(){
		try{
			myThread.printmsg();
		}catch(IOException e){
		}			
	}
}