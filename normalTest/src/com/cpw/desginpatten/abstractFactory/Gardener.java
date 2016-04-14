package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:Gardener.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:55:23
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  抽象工厂角色：工厂接口
 */
public interface Gardener {
    public Fruit createFruit(String name);
    public Veggie createVeggie(String name);
}