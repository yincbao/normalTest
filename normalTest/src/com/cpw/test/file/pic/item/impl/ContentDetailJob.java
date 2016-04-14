package com.cpw.test.file.pic.item.impl;

import java.util.regex.Matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cpw.test.file.pic.Constant_www_14yc_com;
import com.cpw.test.file.xplay.Http;

public class ContentDetailJob implements Runnable{

	private static final Log logger = LogFactory.getLog(ContentDetailJob.class);
	private static final String DEFAULT_PATH = "D:/tmp/png/";
	private String uri;
	private String contentName;
	
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public ContentDetailJob(String uri, String contentName) {
		this.uri = uri;
		this.contentName = contentName;
	}

	@Override
	public void run() {
		String lv1 =  Http.getRespInStr(uri);
		Matcher httpContentDetailMatcher = Constant_www_14yc_com.httpContentDetailPattern.matcher(lv1);
		int count = 0;
		while(httpContentDetailMatcher.find()){
			String detail = httpContentDetailMatcher.group(1);
			logger.info("trying getting http png: "+contentName+"    "+detail);
			 try {
				String seq = "";
				if(count<10)
					seq = "0"+count;
				else
					seq = ""+count;
				Http.getRespInImgAsStream(detail,DEFAULT_PATH+contentName+seq+".png");
				
			} catch (Exception e) {
				logger.info("file : "+contentName+count+" not downloaded,please trying maunlly:   "+detail);
			}
			 count++;
		}
	}

}
