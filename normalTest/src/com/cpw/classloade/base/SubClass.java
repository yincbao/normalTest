package com.cpw.classloade.base;

class SuperClass {

    static{

        System.out.println("SuperClass of static block");

    }

    

    public SuperClass(){

        System.out.println("SuperClass of constracutor");

    }

}

public class SubClass extends SuperClass{

    static{

        System.out.println("SubClass of static block");

    }

    

    public SubClass(){

        System.out.println("SubClass of constracutor");

    }

    

    public static void main(String[] args){

        SuperClass t = new SubClass();

    }

}


