package com.cpw.classloade.LoaderThoery;


public class LoaderThoery {

	
	/**
	 * class loader 全局负责原则，和委托机制（委托机制，是指优先让父loader加载，但是java应用程序员的ckass
	 * 父loader大多数时候是不能加载的，因为classloade 只load约定路径下的class， bootstrap 直接去jre目录下lib
	 * extClassloader 只去System.getProperty("java.ext.dirs")
	 * 程序员写的代码一般都只能由AppClassloader加载。），
	 * 知道Launcher内部逻辑（ 构造extClassloader。搜索路径依据系统"System.getProperty("java.ext.dirs")"
	 * 					然后构造AppClassloader.搜索路径依据系统"System.getProperty("java.class.path")
	 * 					把AppClassloader 设为Thread.currentClassLoader，供程序使用。）
	 * 
	 * 了解自定义classLoad工作常见的几个错误 classNotFound 和classCastException、以及编译错误
	 * 
	 * 想要动态加载，肯定要依赖父类，做一次类型转换。
	 * 经典应用场景：tomcat 加载servlet，不关心你的servlet类具体在common/lib（把你的工程打jar包放进去）
	 * 还是在你工程lib下，只要自定义servlet继承了 Servlet。都能被load进来。而且不报classnotfounf 和notcast异常
	 * @param args
	 */
	public static void main(String[] args){
		ClassLoader loader = ClassLoader.getSystemClassLoader();
		System.out.println(loader.getClass().getName());
		System.out.println(loader.getParent().getClass().getName());
		//System.out.println(loader.getParent().getParent().getClass().getName());
		System.out.println(System.getProperty("java.ext.dirs"));
		 
		//URL[] urls=sun.misc.Launcher.getBootstrapClassPath().getURLs();
		System.out.println(Thread.currentThread().getContextClassLoader()== ClassLoader.getSystemClassLoader());
		System.out.println( ClassLoader.getSystemClassLoader());
		 System.out.println(System.getProperty("java.class.path"));
		 System.out.println(System.class.getClassLoader());
		 System.out.println(java.lang.String.class.getClassLoader());
		 System.out.println(ClassLoader.getSystemResource("java/lang/String.class"));
		 
	}
}
