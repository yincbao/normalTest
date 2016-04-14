package com.cpw.test.file.pic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cpw.test.file.xplay.BaseComparator;
import com.cpw.test.file.xplay.FetchJob;
import com.cpw.test.file.xplay.Nurl;
import com.cpw.test.file.xplay.Util;

public class Main {
	
	private static final Log logger = LogFactory.getLog(Main.class);
	

	public static String getnameUrl = "title=\"([^\"]*)\"\\s+href=\"/REPLACEME/(\\d+)/\"";
	public static final Pattern getImgUrl = Pattern.compile(".*<p\\s+class=\"pic\">\\s*<img.*src=\"(http://[^\"]*)\"");
	public static String getPlayAddr = "<li>\\s*<a\\s+href=\"([^\"]*REPLACEME[^\"]*.html)\"";
	public static final Pattern xplay = Pattern.compile("nurl\\s+=\\s+\"(xfplay[^\"]*)\"");
	private static Map<String,String> name2Code = new HashMap<String,String>();
	
	public static final int size = 1;
	public static final int MAX_WORKLOAD_PRE_ITEM_THREAD = 30;
	
	private static final String DEFAULT_PATH = "D:/tmp/";
	private static String DEFAULTIMG = DEFAULT_PATH+"timg.png";
	
	private static IitemDividerJob itemDividerJob;
	
   public static void superMultiThreadGetXplayUrl(String url,String uri,int index, int pageCount2) throws InterruptedException{
		Set<Nurl> xplayResourceSet = new HashSet<Nurl>();
		List<Nurl> whole = new ArrayList<Nurl>();
		//if(matcher.find()){
			int pageCount = pageCount2;
			logger.info("job pre-action: total pagenum:"+pageCount);
			int threadNum = pageCount/size;
			threadNum = pageCount%size==0?threadNum:threadNum+1;
			logger.info("job pre-action: total threadNum:"+threadNum);
			int start = index+1;
			AtomicInteger atomCounter = new AtomicInteger();
			ExecutorService executorService = Executors.newFixedThreadPool(threadNum+2) ;
			for(int i = 0;i<threadNum;i++){
				/*Thread thread = new Thread(new FetchJob(atomCounter, start, url, uri, xplayResourceSet, whole, name2Code, size));
				thread.start();*/
				executorService.execute(new FetchJob(atomCounter, start, url, uri, xplayResourceSet, whole, name2Code, size));
				start+=size;
			}
			while(true){
				if(atomCounter.get() == threadNum){
					logger.info("Job done, all "+name2Code.size()+" files collected");
					break;
				}else {
					int threadCount = ((ThreadPoolExecutor)executorService).getActiveCount();
					logger.info("Active Thread Count: "+threadCount);
					logger.info(atomCounter.get()+" of"+ threadNum+" sub taks done.");
					Thread.sleep(10000);
				}
			}
		//}
		BaseComparator compartor = new  BaseComparator();
		Collections.sort(whole, compartor);
		Util.write2Xls(DEFAULT_PATH+DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4)+".xls",xplayResourceSet, whole);
	}
   
   
   
	public static void main(String[] args)  {
		PropertyConfigurator.configure("D:/exe_normal/etc/properties/xplaylog4j.properties");
		AbstractApplicationContext actx = new  ClassPathXmlApplicationContext("com/cpw/test/file/pic/spring-pic.xml");
		String homepage = args[0];
		String[] items = args[1].split(",");
		itemDividerJob = (IitemDividerJob) actx.getBean("itemDividerJob");
		itemDividerJob.preLaunch(homepage,items);
		
		/*for(String item:items){
			String lv1 =  Http.getRespInStr(homepage+"/"+item);
			fetchPage.fetchTotalPage(lv1);
			Matcher matcher = Constant_www_14yc_com.getPgAr.matcher(lv1);
			if(matcher.find()){
				int pageCount = Integer.parseInt(matcher.group(2));
				if(pageCount>MAX_WORKLOAD_PRE_ITEM_THREAD){
					int subCount = pageCount/MAX_WORKLOAD_PRE_ITEM_THREAD;
					subCount = pageCount%MAX_WORKLOAD_PRE_ITEM_THREAD==0?subCount:(subCount+1);
					logger.info("item: "+item+" is too large: "+pageCount+", divided into: "+subCount+" items");
					for(int j=0;j<subCount;j++){
						//itmeThread.add(item+"/index"+(j*MAX_WORKLOAD_PRE_ITEM_THREAD)+".html");
						logger.info(item+"/index"+(j*MAX_WORKLOAD_PRE_ITEM_THREAD)+".html"+" should start as anther lv1 Thread");
						new Thread(new ItemJob(item+"/index"+(j*MAX_WORKLOAD_PRE_ITEM_THREAD)+".html",homepage,MAX_WORKLOAD_PRE_ITEM_THREAD)).start();
					}
				}else{
					//itmeThread.add(item);
					logger.info("item: "+item+" :"+pageCount+" should start only lv1 Thread ");
					new Thread(new ItemJob(item,homepage,pageCount)).start();
				}
			}
		}*/
	}
}
