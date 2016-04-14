package com.cpw.desginpattern.observer;

public class Test {
	  public static void main(String[] args) {
	    //new product
	    Product product = new Product(1000);
	    //email observer
	    new EmailSendObserver(product);
	    //sms observer
	    new SMSSendObserver(product);
	    //change price of product
	    product.changePrice(800);
	  }
	}