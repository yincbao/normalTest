package com.cpw.desginpattern.observer;


public class EmailSendObserver implements Observer {

	 //通过构造器的方式给Product注入邮件发送观察者
	  public EmailSendObserver(Product product) {
	    product.addObserver(this);
	  }
	  public void update(Observable product, Object arg) {
	    // TODO Auto-generated method stub
	    System.out.println("Product price has been changed to "
	        + ((Product)product).getPrice()
	        + " ;ntt The notify e-mail has sent to all client.");   
	  }

}
