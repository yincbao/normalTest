package com.cpw.desginpattern.observer;

public class Product extends Observable {
	  //������
	  private Product(){}
	  //������
	  public Product(float price){
	    this.price = price;
	  }
	 
	  //��Ʒ�۸�
	  private float price;
	  //�ı���Ʒ�۸�
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