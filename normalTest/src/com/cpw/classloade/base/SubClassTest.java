package com.cpw.classloade.base;

class SuperClass2{

    public SuperClass2(){

        System.out.println("SuperClass of constructor");

        m();

    }

    public void m(){

        System.out.println("SuperClass.m()");

    }

}

public class SubClassTest extends SuperClass2 {

    private int i = 10;

    public SubClassTest(){

        System.out.println("SubClass of constructor");

        super.m();

        m();

    }

    public void m(){

        System.out.println("SubClass.m(): i = " + i);

    }

    public static void main(String[] args){

        SuperClass2 t = new SubClassTest();

    }

}


