package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:NorthernVeggie.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:59:36
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  �����Ʒ��ɫ�����ȴ��߲�
 */
public class NorthernVeggie implements Veggie {
    private String name;
    public NorthernVeggie(String name) {
        System.out.println("���ȴ�����Ϊ�������ˣ����ȴ��߲ˣ�"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}