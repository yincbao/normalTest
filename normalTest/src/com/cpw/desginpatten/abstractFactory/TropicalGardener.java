package com.cpw.desginpatten.abstractFactory;

/**
 * Created by IntelliJ IDEA.
 * FileName:TropicalGardener.java
 * User:    LavaSoft
 * Date:    2006-12-5
 * Time:    23:01:49
 * 《Java与模式》（--阎宏博士著）读书笔记
 * 工厂模式--抽象工厂模式--一般性模式（农场应用）
 * ReadMe:  具体工厂角色：热带工厂
 */
public class TropicalGardener implements Gardener {
    public Fruit createFruit(String name) {
        return new TropicalFruit(name);
    }
    public Veggie createVeggie(String name) {
        return new TropicalVeggie(name);
    }
}