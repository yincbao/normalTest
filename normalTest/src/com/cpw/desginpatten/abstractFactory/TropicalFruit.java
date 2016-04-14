package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:TropicalFruit.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:57:08
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体产品角色：热带水果
 */
public class TropicalFruit implements Fruit {
    private String name;
    public TropicalFruit(String name) {
        System.out.println("热带工厂为您创建了：热带水果－"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}