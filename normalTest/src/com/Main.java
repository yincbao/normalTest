package com;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	
	private static  Pattern name = Pattern.compile("\\d{1,2}:\\d{1,2}\\s+(.*)");
	private static Pattern targetUrl = Pattern.compile(".*(http:.*htm).*");
	
	private static Pattern allSegments = Pattern.compile("\\s*Number of segments =\\s+(\\d*)");
	
	private static Pattern regBoo = Pattern.compile("\\d+;.*");
	
	private static Pattern QVOD_PATTERN = Pattern.compile("(.*)_\\d+\\.!mv");
	
	
	private Pattern contentCharsetPartten = Pattern.compile(".*;\\s*charset\\s*=\\s*(.*);.*"); 
	
	
	public static void main(String args[]){
		
	}
	public static void main1(String[] args) throws IOException {
		
		Pattern doNoAno = Pattern.compile("^(?!(Anno)).*$");
		
		Pattern doAno = Pattern.compile("^do(.*)Anno$");
		
		System.out.println(doNoAno.matcher("doadasssAnno").find());
		
		UUID uuid = UUIDUtils.create();
		System.out.println(uuid.toString()+" "+(uuid.timestamp()==UUID.fromString(uuid.toString()).timestamp()));
		
		;
//		Date date = new Date();
//		if(date==null){
//			throw new RuntimeException("null date object get!");
//		}
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(date);
//		cal.add(Calendar.MINUTE, -24*7*60);
//		System.out.println(cal.getTime()); ;
//		
//		System.out.println(URLDecoder.decode("%E7%88%B8%E7%88%B8%E5%8E%BB%E5%93%AA%E5%84%BF%E7%AC%AC%E4%BA%8C%E5%AD%A3", "utf-8"));
//		
//		Matcher match = targetUrl.matcher("<a href=\"http://baike.baidu.com/subview/416146/10365035.htm\" target=\"_blank\"><em>���������</em>_�ٶȰٿ�</a>");
//		if(match.find()){
//			System.out.println(match.group(1));
//		}
//		
//		String str = " dsd   sdfqwe234v4 53 4v ";
//		System.out.println(str.replaceAll("\\s+", ""));
//		
//		Integer oint  = null;
//		
//		System.out.println(oint);
//		
//		Double doub = 1.09D;
//		int i =(int) (doub*100);
//		System.out.println(i);
//		
//		int j = 109;
//		Double d =(double) (j/100);
//		;
//		System.out.println(Double.parseDouble(String.format("%.2f", (double)109/100)));
//		//System.out.println(String.format("%.2f", (d) ));
//		//System.out.println(d);
		
		/*Scanner sc = new Scanner(System.in);
		System.out.println("give me a code:");
		String str = sc.nextLine();
		System.out.println(str);
		System.out.println(str.replaceAll("\\\\", "\\\\\\\\"));*/
		
		//��һ���������Ҫ�ָ���String���ڶ����Ƿָ��ַ�ϣ�����������ʾ�ָ�����Ƿ���Ϊ��Ƿ��أ����ָ���ָ��ַ�Ĭ�ϵ��ǣ���\t\n\r\f��
//		StringTokenizer tokenizer = new StringTokenizer("my name is YinChang-bao, it's my honor to be here and have a speach with you guys","/f/n/r/t/v",true);
//		while(tokenizer.hasMoreElements()){
//			System.out.println(tokenizer.nextToken());
//		}
		Lock lock_1 = new ReentrantLock();
		lock_1.lock();
		Matcher match = allSegments.matcher("Number of segments = 46   ");
		if(match.find()){
			System.out.println(match.group(1));
		}
		
		Lock lock_2 = new ReentrantLock();
		lock_2.lock();
		Matcher match1 = regBoo.matcher("1175264;au401");
		System.out.println(match1.matches());
		long l = System.currentTimeMillis()/1000+230;
		
		System.out.println(timeshiftEnsure(String.valueOf(l)));;
		
		
		
		Matcher match2 = QVOD_PATTERN.matcher(".rmvb_0.!mv");
		if(match2.find()){
			System.out.println(match2.group(1));
		}
		
		System.out.println(92/3);
		
	}//1405911851111
	 //1406206980000
	
	
	private static String timeshiftEnsure(String startTime) {
		long curr = System.currentTimeMillis()/1000+1;
		System.out.println("curr"+curr);
		long staLongv = Long.parseLong(startTime);
		
		if((staLongv-240)>curr){
			System.out.println("staLongv-240="+(staLongv-240));
			startTime = String.valueOf(curr-240);
		}
		return startTime;
	}
}
