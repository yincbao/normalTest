package com.cpw.myExecutor;

import java.io.File;
import java.util.concurrent.Callable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import com.cpw.ehcache.EHCacheUtil;
import com.cpw.mail.MailSender;

public class CustomlizedJob implements Job, Callable<Job> {
	private String ID;
	private String status;

	private static final String PLUG_IN_PATH = "D:/plug-in";

	CacheManager cachemanager = EHCacheUtil.getCacheManager();
	Cache cache = cachemanager.getCache("MYCACHE");

	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getID() {
		return this.ID;
	}

	public void setID(String jobid) {
		this.ID = jobid;
	}

	public Job call() throws Exception {
		
		try {
			System.out.println(this.getID()+"   job start!");
			MailSender se = new MailSender(false);
			File affix =null;
			se.sendEmailWithAttachment("tesing BCC ", "<html><body>hi, this is a test mail! t</body></html>", affix);//
			System.out.println(this.getID()+"   job done!");
			this.setStatus("SUCCEED");
		} catch (Exception e) {
			this.setStatus("FAILED");
		}
		return this;
	}

}
