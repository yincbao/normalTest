package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:TestApp.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    23:03:22
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  测试类（客户端）
 */
public class TestApp {
    private void test(){
        Veggie tv,nv;
        Fruit tf,nf;
        Gardener tg=new TropicalGardener();
        Gardener ng=new NorthernGardener();
        tv=tg.createVeggie("热带菜叶");
        nv=ng.createVeggie("东北甜菜");
        tf=tg.createFruit("海南椰子");
        nf=ng.createFruit("雪梨");
    }
    public static void main(String args[]){
        TestApp test=new TestApp();
        test.test();
    }
}
 