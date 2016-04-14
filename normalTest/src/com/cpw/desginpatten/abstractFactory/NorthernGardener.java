package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:NorthernGardener.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    23:00:31
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  ���幤����ɫ�����ȴ�����
 */
public class NorthernGardener implements Gardener {
    public Fruit createFruit(String name) {
        return new NorthernFruit(name);
    }
    public Veggie createVeggie(String name) {
        return new NorthernVeggie(name);
    }
}
