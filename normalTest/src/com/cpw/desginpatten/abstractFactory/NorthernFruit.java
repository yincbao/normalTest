package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:NorthernFruit.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:58:55
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  �����Ʒ��ɫ�����ȴ�ˮ��
 */
public class NorthernFruit implements Fruit {
    private String name;
    public NorthernFruit(String name) {
        System.out.println("���ȴ�����Ϊ�������ˣ����ȴ�ˮ����"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}