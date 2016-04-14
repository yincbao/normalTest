package com.cpw.test.file.xplay;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * http://www.519933.com/list/index61_3.html
 * http://www.chaopeng1.com/qiangjianluanlun/index-3.html
 * @author hadoop
 *
 */
public class XPlayUrl {
	
	private static final Log logger = LogFactory.getLog(XPlayUrl.class);
	//var nurl = "xfplay://dna=EGqeBdMfmxD4mHD3AGyeBdfcAHD1mdt1AwIdmGxXAxeYmGe1Ewa0ma|dx=548026459|mz=Rdd-094.rmvb|zx=nhE0pdOVl3P5AF5uLKP5rv5wo206BGa4mc94MzXPozS|zx=nhE0pdOVl3Ewpc5xqzD4AF5wo206BGa4mc94MzXPozS";
	public static final Pattern getPgAr = Pattern.compile(".*<div class=\"page\">¹²\\d+²¿Ó°Æ¬.*(\\d+)/(\\d+)");
	public static String getnameUrl = "title=\"([^\"]*)\"\\s+href=\"/REPLACEME/(\\d+)/\"";
	public static final Pattern getImgUrl = Pattern.compile(".*<p\\s+class=\"pic\">\\s*<img.*src=\"(http://[^\"]*)\"");
	public static String getPlayAddr = "<li>\\s*<a\\s+href=\"([^\"]*REPLACEME[^\"]*.html)\"";
	public static final Pattern xplay = Pattern.compile("nurl\\s+=\\s+\"(xfplay[^\"]*)\"");
	private static Map<String,String> name2Code = new HashMap<String,String>();
	
	public static final int size = 1;
	public static final int MAX_WORKLOAD_PRE_ITEM_THREAD = 30;
	
	private static final String DEFAULT_PATH = "D:/tmp/";
	private static String DEFAULTIMG = DEFAULT_PATH+"timg.png";
	
	public static void getXplayUrl(String url,String uri) throws InterruptedException{
		
		String lv1 =  Http.getRespInStr(url+"/"+uri);
		Matcher matcher = getPgAr.matcher(lv1);
		Set<Nurl> xplayResourceSet = new HashSet<Nurl>();
		List<Nurl> whole = new ArrayList<Nurl>();
		if(matcher.find()){
			int pageCount = Integer.parseInt(matcher.group(2));
			
			for(int i=1;i<=pageCount;i++){
				logger.info("start picking from page "+i);
				if(i>1){
					lv1 = Http.getRespInStr(url+"/"+uri+"/index"+i+".html");
				}
				getnameUrl = getnameUrl.replace("REPLACEME", uri);
				Matcher fileMatcher = Pattern.compile(getnameUrl).matcher(lv1);
				while(fileMatcher.find()){
					Nurl ele = new Nurl();
					ele.setName(fileMatcher.group(1)+RandomStringUtils.randomNumeric(4));
					name2Code.put(ele.getName(), DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4));
					logger.info("file name£º "+ele.getName());
					String detailCode = fileMatcher.group(2);
					String detail = Http.getRespInStr("http://www.avmayi.info"+"/"+uri+"/"+detailCode);
					Matcher matcherUtl = getImgUrl.matcher(detail);
					if(matcherUtl.find()){
						try{
							String imgurl = matcherUtl.group(1);
							Http.getRespInImg(imgurl,DEFAULT_PATH+name2Code.get(ele.getName())+".png");
							ele.setImg(DEFAULT_PATH+name2Code.get(ele.getName())+".png");
						}catch(Exception e){
							
						}finally{
							ele.setImg(DEFAULTIMG);
						}
						logger.info("file img:"+name2Code.get(ele.getName()));
					}else{
						logger.info("didn't get file img from "+detailCode+" of file name: "+ele.getName());
					}
					getPlayAddr = getPlayAddr.replace("REPLACEME", detailCode);
					Pattern playAdrPartten = Pattern.compile(getPlayAddr);
					Matcher playAddMatch = playAdrPartten.matcher(detail);
					String playDetailAddr = "";
					if(playAddMatch.find()){
						playDetailAddr = playAddMatch.group(1);
					}else{
						playDetailAddr = "/"+uri+"/"+detailCode+"/0-1.html";
					}
					String playdetail = Http.getRespInStr("http://www.avmayi.info"+playDetailAddr);
					Matcher matcherXplay = xplay.matcher(playdetail);
					if(matcherXplay.find()){
						String xplayUrl = matcherXplay.group(1);
						ele.setUrl(url);
						logger.info("file xplay url: "+xplayUrl);
						xplayResourceSet.add(ele);
						whole.add(ele);
					}else{
						logger.info("didn't get file url from: "+"http://www.avmayi.info"+playDetailAddr+" file name:"+ele.getName());
					}
				}
			}
			BaseComparator compartor = new  BaseComparator();
			Collections.sort(whole, compartor);
			Util.write2Xls(DEFAULT_PATH+DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4)+".xls",xplayResourceSet, whole);
		}
		
		
	}
	
	
   
   
   public static void multiThreadGetXplayUrl(String url,String uri,int index) throws InterruptedException{
		
		String lv1 =  Http.getRespInStr(url+"/"+uri);
		Matcher matcher = getPgAr.matcher(lv1);
		Set<Nurl> xplayResourceSet = new HashSet<Nurl>();
		List<Nurl> whole = new ArrayList<Nurl>();
		if(matcher.find()){
			int pageCount = Integer.parseInt(matcher.group(2));
			pageCount = pageCount>30?30:pageCount;
			logger.info("job pre-action: total pagenum:"+pageCount);
			int threadNum = pageCount/size;
			threadNum = pageCount%size==0?threadNum:threadNum+1;
			logger.info("job pre-action: total threadNum:"+threadNum);
			int start = 1;
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
		}
		BaseComparator compartor = new  BaseComparator();
		Collections.sort(whole, compartor);
		Util.write2Xls(DEFAULT_PATH+DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4)+".xls",xplayResourceSet, whole);
	}
   
   public static void superMultiThreadGetXplayUrl(String url,String uri,int index, int pageCount2) throws InterruptedException{
		
		//String lv1 =  Http.getRespInStr(url+"/"+uri);
		//Matcher matcher = getPgAr.matcher(lv1);
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
   
	public static void main(String[] args) throws InterruptedException {
		PropertyConfigurator.configure("etc/properties/xplaylog4j.properties");
		String homepage = args[0];
		String[] items = args[1].split(",");
		//List<String> itmeThread = new ArrayList<String>();
		for(String item:items){
			String lv1 =  Http.getRespInStr(homepage+"/"+item);
			Matcher matcher = getPgAr.matcher(lv1);
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
		}
	}

}
