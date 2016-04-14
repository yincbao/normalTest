package com.cpw.desginpattern.observer;

public class Product extends Observable {
	  //构造器
	  private Product(){}
	  //构造器
	  public Product(float price){
	    this.price = price;
	  }
	 
	  //商品价格
	  private float price;
	  //改变商品价格
	  public void changePrice(float price) {
	    if (this.price != price) {
	      this.price = price;
	      setChanged();
	    }
	    notifyObservers();
	  }
	  public float getPrice() {
	    return price;
	  }
	  public void setPrice(float price) {
	    this.price = price;
	  }
	}