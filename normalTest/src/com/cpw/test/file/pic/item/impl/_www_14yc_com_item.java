package com.cpw.test.file.pic.item.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.cpw.test.file.pic.Constant_www_14yc_com;
import com.cpw.test.file.pic.item.ItemJob;

public class _www_14yc_com_item  implements ItemJob{

	
	private String item;
	private String homePage;
	private int pageCount;
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public _www_14yc_com_item(String item, String homepage,int pageCount) {
		this.item = item;
		this.homePage = homepage;
		this.pageCount = pageCount;
	}

	@Override
	public void run() {
		ExecutorService executorService = Executors.newFixedThreadPool(pageCount+2) ;
		int threadNum = pageCount/Constant_www_14yc_com.size;
		threadNum = pageCount%Constant_www_14yc_com.size==0?threadNum:threadNum+1;
		AtomicInteger atomCounter = new AtomicInteger();
		int index = 0;
		if(item.indexOf("index_")>-1)
			index = Integer.parseInt(item.substring(item.lastIndexOf("index_")+6,item.lastIndexOf(".html")));
		for(int i = 0;i<threadNum;i++){
			index = index+Constant_www_14yc_com.size;
			executorService.execute(new _www_14yc_com_fetchContentJob(atomCounter,homePage,item,index,Constant_www_14yc_com.size));
		}
		
	}

}
