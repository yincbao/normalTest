package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:Gardener.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:55:23
 * ��Java��ģʽ����--�ֺ격ʿ��������ʼ�
 * ����ģʽ--���󹤳�ģʽ--һ����ģʽ��ũ��Ӧ�ã�
 * ReadMe:  ���󹤳���ɫ�������ӿ�
 */
public interface Gardener {
    public Fruit createFruit(String name);
    public Veggie createVeggie(String name);
}