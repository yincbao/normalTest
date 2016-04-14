package com.cpw.desginpattern.observer;

public class SMSSendObserver implements Observer {
	 
	  //ͨ���������ķ�ʽ��Productע�����Ϣ���͹۲���
	  public SMSSendObserver(Product product) {
	    product.addObserver(this);
	  }
	  public void update(Observable product, Object arg) {
	    // TODO Auto-generated method stub
	    System.out.println("Product price has been changed to "
	    		 + ((Product)product).getPrice()
	        + " ;ntt The notify SMS has sent to all client.");   
	  }
	}