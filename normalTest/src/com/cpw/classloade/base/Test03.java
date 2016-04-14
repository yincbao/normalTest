package com.cpw.classloade.base;

public class Test03 {

    private int i1 = printCommon();

    private static int i2 = printStatic();

    

    public Test03(){

        

    }

    public static int printCommon(){

        System.out.println("i1 is init!");

        return 1;

    }

    public static int printStatic(){

        System.out.println("i2 is init!");

        return 2;

    }

    public static void main(String[] args) {

        Test03 t = new Test03();

    }

}


