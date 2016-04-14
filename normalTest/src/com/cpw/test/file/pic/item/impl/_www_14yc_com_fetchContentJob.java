package com.cpw.test.file.pic.item.impl;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cpw.test.file.pic.Constant_www_14yc_com;
import com.cpw.test.file.xplay.Http;

public class _www_14yc_com_fetchContentJob implements Runnable {

	private static final Log logger = LogFactory.getLog(_www_14yc_com_fetchContentJob.class);
	
	private AtomicInteger atomCounter;
	private String homePage;
	private int start;
	private int size;
	private String item;
	
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public AtomicInteger getAtomCounter() {
		return atomCounter;
	}

	public void setAtomCounter(AtomicInteger atomCounter) {
		this.atomCounter = atomCounter;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public _www_14yc_com_fetchContentJob(AtomicInteger atomCounter,String homePage, String item, int start, int size) {
		this.atomCounter = atomCounter;
		this.homePage = homePage;
		this.item = item;
		this.start = start;
		this.size = size;
	}

	@Override
	public void run() {
		ExecutorService executorService = Executors.newFixedThreadPool(30+2) ;
		for(int i=start;i<start+size;i++){
			String u = homePage+"/"+item;
			if(start!=1)
				u+="/index_"+i+".html";
			logger.debug(u);
			String lv1 =  Http.getRespInStr(u);
			Matcher ContentMatcher = Constant_www_14yc_com.contentListPattern.matcher(lv1);
			Map<String,String> cids = new LinkedMap();
			while (ContentMatcher.find()) {
				String contentId = ContentMatcher.group(1);
				String contentName = ContentMatcher.group(2);
				logger.info(" find:" + contentId+" name:"+contentName);
				executorService.execute(new ContentDetailJob(homePage+"/tupianzhuanqu/katongdongman/"+contentId+".html",contentName));
			}
			
		}

	}

}
