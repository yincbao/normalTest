package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:TestApp.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    23:03:22
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  �����ࣨ�ͻ��ˣ�
 */
public class TestApp {
    private void test(){
        Veggie tv,nv;
        Fruit tf,nf;
        Gardener tg=new TropicalGardener();
        Gardener ng=new NorthernGardener();
        tv=tg.createVeggie("�ȴ���Ҷ");
        nv=ng.createVeggie("�������");
        tf=tg.createFruit("����Ҭ��");
        nf=ng.createFruit("ѩ��");
    }
    public static void main(String args[]){
        TestApp test=new TestApp();
        test.test();
    }
}
 