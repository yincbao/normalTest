package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:TropicalGardener.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    23:01:49
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  ���幤����ɫ���ȴ�����
 */
public class TropicalGardener implements Gardener {
    public Fruit createFruit(String name) {
        return new TropicalFruit(name);
    }
    public Veggie createVeggie(String name) {
        return new TropicalVeggie(name);
    }
}