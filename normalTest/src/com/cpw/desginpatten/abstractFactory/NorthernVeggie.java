package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:NorthernVeggie.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    22:59:36
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体产品角色：亚热带蔬菜
 */
public class NorthernVeggie implements Veggie {
    private String name;
    public NorthernVeggie(String name) {
        System.out.println("亚热带工厂为您创建了：亚热带蔬菜－"+name);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}