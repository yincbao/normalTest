package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:TropicalVeggie.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:58:03
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  �����Ʒ��ɫ���ȴ��߲�
 */
public class TropicalVeggie implements Veggie {
    private String name;
    public TropicalVeggie(String name) {
        System.out.println("�ȴ�����Ϊ�������ˣ��ȴ�ˮ����"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}