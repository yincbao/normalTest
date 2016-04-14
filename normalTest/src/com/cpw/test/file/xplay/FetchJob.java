package com.cpw.test.file.xplay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FetchJob implements Runnable{
	private static final Log logger = LogFactory.getLog(FetchJob.class);
	private AtomicInteger atomCounter = null;
	public int start = 1;
	public String url;
	public String uri;
	Set<Nurl> xplayResourceSet;
	List<Nurl> whole;
	Map<String,String> name2Code;
	
	private static final String DEFAULT_PATH = "D:/tmp/";
	private int size;
	
	public static final Pattern getPgAr = Pattern.compile(".*<div class=\"page\">¹²\\d+²¿Ó°Æ¬.*(\\d+)/(\\d+)");
	//public static String getnameUrl = "title=\"([^\"]*)\"\\s+href=\"/REPLACEME/(\\d+)/\"";
	public static final Pattern getImgUrl = Pattern.compile(".*<p\\s+class=\"pic\">\\s*<img.*src=\"(http://[^\"]*)\"");
	//public static String getPlayAddr = "<li>\\s*<a\\s+href=\"([^\"]*REPLACEME[^\"]*.html)\"";
	public static final Pattern xplay = Pattern.compile("nurl\\s+=\\s+\"(xfplay[^\"]*)\"");
	public static final Pattern mainPattern = Pattern.compile("<div class=\"mainbox\"(.*)<div class=\"page\">");
	private static String DEFAULTIMG = DEFAULT_PATH+"timg.png";
	
	
	@Override
	public void run() {
		try{
			String getnameUrl = "title=\"([^\"]*)\"\\s+href=\"/REPLACEME/(\\d+)/\"";
			String getPlayAddr = "<li>\\s*<a\\s+href=\"([^\"]*REPLACEME[^\"]*.html)\"";
			if(uri.indexOf("index")>-1&&uri.indexOf(".html")>-1)
				uri = uri.substring(0,uri.indexOf("/"));
			String  sqe = uri+" "+start;
			Set<Nurl> xplayResourceSetlocal = new HashSet<Nurl>();
			List<Nurl> wholelocal = new ArrayList<Nurl>();
			for(int i=start;i<start+size;i++){
				logger.info(sqe+" start picking from page "+i+" detail url is:"+url+"/"+uri+"/index"+i+".html");
				String lv1 ="";
				String u = "";
				if(i==1){
					u = url+"/"+uri;
					lv1 =  Http.getRespInStr(u);
				}else{
					u=url+"/"+uri+"/index"+i+".html";
					lv1 = Http.getRespInStr(u);
					lv1 = lv1.replaceAll("\n", "-");
					//System.out.println(lv1);
					Matcher mainMatcher = mainPattern.matcher(lv1);
					if(mainMatcher.find()){
						lv1 = mainMatcher.group(1);
					}
				}
				
				getnameUrl = getnameUrl.replace("REPLACEME", uri);
				Matcher fileMatcher = Pattern.compile(getnameUrl).matcher(lv1);
				boolean mat = false;
				while(fileMatcher.find()){
					mat = true;
					Nurl ele = new Nurl();
					ele.setName(fileMatcher.group(1)+RandomStringUtils.randomNumeric(4));
					name2Code.put(ele.getName(), DateFormatUtils.format(new Date(), "HHmmssSSS")+RandomStringUtils.randomNumeric(4));
					logger.info(sqe+" file name£º "+ele.getName());
					String detailCode = fileMatcher.group(2);
					String detail = Http.getRespInStr("http://www.avmayi.info"+"/"+uri+"/"+detailCode);
					Matcher matcherUtl = getImgUrl.matcher(detail);
					if(matcherUtl.find()){
						try{
							String imgurl = matcherUtl.group(1);
							Http.getRespInImg(imgurl,DEFAULT_PATH+name2Code.get(ele.getName())+".png");
							ele.setImg(DEFAULT_PATH+name2Code.get(ele.getName())+".png");
						}catch(Exception e){
							ele.setImg(DEFAULTIMG);
						}
						logger.info(sqe+" file img:"+ele.getImg());
					}else{
						logger.info(sqe+" didn't get file img from "+detailCode+" of file name: "+ele.getName());
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
						ele.setUrl(xplayUrl);
						logger.info(sqe+" file xplay url: "+xplayUrl);
						xplayResourceSet.add(ele);
						whole.add(ele);
						xplayResourceSetlocal.add(ele);
						wholelocal.add(ele);
					}else{
						logger.info(sqe+" didn't get file url from: "+"http://www.avmayi.info"+playDetailAddr+" file name:"+ele.getName());
					}
				}
				if(!mat)
					logger.info(sqe+" page No."+i+ " passed, second level regpattern:"+getnameUrl+" not match data from ["+u+"]  and returns ["+lv1+"]");
				else
					logger.info(sqe+" page No."+i+ " done!");
			}
			Util.write2Xls(DEFAULT_PATH+sqe+".xls", xplayResourceSetlocal, wholelocal);
			atomCounter.incrementAndGet();
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			atomCounter.incrementAndGet();
		}
		
		//Util.write2Xls(DEFAULT_PATH+sqe+".xls", xplayResourceSet, whole);
	}


	public FetchJob( AtomicInteger atomCounter, int start, String url, String uri,
			Set<Nurl> xplayResourceSet, List<Nurl> whole,
			Map<String, String> name2Code, int size) {
		super();
		this.start = start;
		this.url = url;
		this.uri = uri;
		this.xplayResourceSet = xplayResourceSet;
		this.whole = whole;
		this.name2Code = name2Code;
		this.size = size;
		this.atomCounter = atomCounter;
	}

}
