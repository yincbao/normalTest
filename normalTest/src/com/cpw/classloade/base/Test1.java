package com.cpw.classloade.base;

class A{

    private int i = 9;

    protected static int j;

    static{

        System.out.println("-- Load First SuperClass of static block start!-- ");

        System.out.println("j = " + j);

        System.out.println("-- Load First SuperClass of static block End  -- ");

    }

    

    public A(){

        System.out.println("------- Load SuperClass of structor start --------");

        System.out.println("Frist print j = " + j);

        j = 10;

        m();

        System.out.println("k = " + k);

        System.out.println("Second print j = " + j);

        System.out.println("-----------  Load  SuperClass End    ----------- ");

    }

    

    private static int k = getInt();

        

    public static int getInt(){

        System.out.println("Load SuperClass.getInt() ");

        return 11;

    } 

    static{

        System.out.println("--- Load Second SuperClass of static block!-------");

        System.out.println("j = " + j);

        System.out.println("k = " + k);

        System.out.println("-- Load Second SuperClass of static block End -- ");

    }

    

    public void m(){

        System.out.println("SuperClass.m() , " + "j = " +j);

        

    }

}

class B extends A {

    private  int a = 10;

    

    static{

        System.out.println("---- Load SubClass of static block!------");

        System.out.println("-- Load SubClass of static block End -- ");

    }

    

    public B(){

        System.out.println("Load SubClass of structor");

        m();

        System.out.println("---   Load SubClass End  ---- ");

    }

    

    public void m(){

        System.out.println("SubClass.m() ," + "a = " + a );

    }

}


/**
 * 此包旨在探讨 class 变量 static 变量 构造方法初始化顺序
 * 
 * 
 * 原则1：static 语句块最优先 非static其次，static{}在一个classloader下只执行一次。{}类似构造方法
 * 原则2：变量默认值早于构造方法。不分是否static
 * 原则3：继承关系下，优先执行父类的static{} {}然后初始化父类的全局变量，但是，如果子类有override，子类优先，父类构造方法
 * 最后才到子类
 * ClassName: Test1
 * @description
 * @author yin_changbao
 * @Date   Aug 6, 2015
 *
 */
public class Test1{

    public static void main(String[] args){

        A a = new B();

    }

}


