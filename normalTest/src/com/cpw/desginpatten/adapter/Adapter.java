package com.cpw.desginpatten.adapter;

import java.io.IOException;

/**
 * ������ģʽ����������A��B
 * �������̳�һ��������һ����Ϊ�Լ����췽���Ĳ�����дwork������ʵ�ʵ��õ�B��work��
 * ��Ϊ�Ѿ��̳���A����������wrok����A�ķ���
 * ��ʵ������ȫ����д�ɣ�ȫ������B�������룬���Ǽ̳�----this is called composition Adapter partten!!!
 * 
 * ���������ȫ�ǽӿڣ�ֱ��ȫ���̳��ˣ��ֱ�ʵ�ָ��Բ�ͬ�ķ�����------this is called inheritance Adapter partten!!!
 *
 */
public class Adapter extends AdapteeA{
	
	public Adaptee abd;
	
	public Adapter(Adaptee abd){
		this.abd = abd;
	}
	public void work(Adaptee abd){
		abd.work();
	}
	
	public static void main1(String[] args) throws IOException {
//		FileReader fr = new FileReader("");
//		BufferedReader br = new BufferedReader(fr);
//		br.readLine();

		AdapteeB b = new AdapteeB();
		Adapter a = new Adapter(b);
		a.work();
		a.work(b);
	}
	/**
	 * ���������ڲ���ʵ��adaptee��swing��������
	 * @param args
	 */
	public static void main(String args[]){
		
		Adaptee adp1 = new Adaptee(){

			@Override
			public void work() {
				System.out.println("I am adptee1");
				
			}
			
		};
		Adaptee adp2 = new Adaptee(){

			@Override
			public void work() {
				System.out.println("I am adptee2");
				
			}
			
		};
		Adapter adpr = new Adapter(adp1);
		adpr.work();
		adpr.work(adp2);
		
	}

}
