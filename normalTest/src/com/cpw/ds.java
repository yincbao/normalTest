package com.cpw;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;



public class ds {
	public static String initStr= "";
	static{
		System.out.println("1同一个classloader只load一次");
		initStr="granted1";
	}
	
	{
		System.out.println("非静态属于每个实例，实例化一个对象有一个执行一次");
		initStr="granted2";
	}
	
	
	public static String getInitStr() {
		return initStr;
	}

	public static int testt() {  
		int i =0;
	      try {  
	            i=  1;  
	        }catch(Exception e){  
	            i= 2;  
	        }finally {  
	            i= 3;  
	        }  
	       return i;
	  
	    }  

	public static void main(String arg[]) throws ClassNotFoundException{
		
		;
		
		System.out.println(new Date(1457010538000L));
		
		/*ds distance = new ds();
		ds distance2 = new ds();*/
		
	/*	List Lists = new ArrayList();
		
		System.out.println(Lists.isEmpty());
		String str[] = "".split(",");
		
		for(int i=0;i<str.length;i++){
			System.out.println("nulll");
		}
		
		StringBuffer sb = new StringBuffer("ABCFDOLFUEROFUR");
		
		System.out.println(sb.toString().toLowerCase());
		sb.append("1111111");
		System.out.println(sb);
		String a = "aa";
		String b = new String ("aa");
		System.out.println(a==b);
		
		System.out.println(arrayDefaulyEquals());
//		testForEach();
		System.out.println(ds.getInitStr());
		doubleIntVal();
		double d = 2.0D;

		System.out.println(testt());;
		System.out.println(String.format("%.2f",d));
		System.out.print("ssssssssssssssssssssss"+new Date(1446181200000L));;
		String timezone_info = System.getProperty("user.timezone");
        System.out.println("当前的时区:"+timezone_info);
        
        Map<String,Object> a1 = new HashMap<String,Object>();
        
        if(a1.get("s")==null)
        	System.out.println("@@@@@@@@@");
        
        System.out.println(1%2);
        
        
        String s0001 = "0001";
        
        long l = Long.parseLong(s0001);
        System.out.println(l);
        
        
       int i =  (int) (Math.random()*900);
       System.out.println(i);
       
       
       System.out.println(new Date(1434165517000L));
       
       
       
       
       String ird = "D:\\home\\C4D\\deviceMgr\\logo\\14338441344732138.png";
       
      String s1= ird.replaceAll("\\\\", "/");
      System.out.println(ird+"   "+s1);
      int in = 0x10000;
      System.out.println(in);
      Calendar cal = Calendar.getInstance();  
  	//cal.set(Calendar.MONTH, 1-1);  
  	//cal.add(Calendar.MONTH, -1);  
  	int MaxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
  	System.out.println(MaxDay);
  	
  	long ii = 13;
  	long j = 16;
  	double r = (double)(ii/j);
  	System.out.println(r);
  	
  
  	long cc = 1434643200000L+(long)24*3600*1000*30;
  	long ccs = (long)24*3600*1000*30;
  	Timestamp tst = new Timestamp(ccs);
  	Timestamp ts = new Timestamp(1355292000000L);
  	String sts = ts.toString();
  	System.out.println(cc+"   "+ccs+"  " +(cc+ccs)+"   "+sts+"   "+ts.toString());;
  	System.out.println(parseDate("2015-07-02 02:32:42").getTime());;
  	
  	
  	
  	testArray();
  	
  	Set<String> s = new HashSet<String>();
  	
  	s.add("1");
  	s.add("s");
  	System.out.println(s.toString());
  	
  	
  	Map<String ,Object> map1 = new HashMap<String,Object>();
  	Map<String, Object> map2 = Collections.unmodifiableMap(map1);
  	map1.put("11111", "aa23waaa");
  	System.out.println(map2.get("11111"));
  	System.out.println(map2.equals(map1));
  	
  	
  	
  	Class<?> clazz = Class.forName( "com.cpw.thread.Test" );
	Class<?> clazz1 = Class.forName( "com.cpw.thread.Test" );
	System.out.println("clazz==clazz1： "+(clazz==clazz1));*/
	}
	
	public static Date parseDate(String dateStr) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateObj = null;
		try {
			dateObj = sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return dateObj;
	}
	
	
	
	public static void testArray(){
		List<String> org = new ArrayList<String>();
		for(int i=0;i<10;i++){
			org.add(RandomStringUtils.randomNumeric(4));
		}
		List<String> minues = new ArrayList<String>();
		
		for(int j = 0;j<3;j++){
			minues.add(org.get(j));
			
		}
		
		List<String> left = new ArrayList<String>();
		left.addAll(org);
		left.removeAll(minues);
		 
		 printArray(org);
		 printArray(minues);
		 printArray(left);
	}
	
	
	private static void printArray(List<? extends Object> input){
		String result = "";
		for(Object obj: input){
			result+=obj+"|";
		}
		System.out.println(result);
	}
	
	volatile int i;
	final Map<String,Object> fMap = new HashMap<String,Object>();
	public void modifyFinal(){
		fMap.put("", "");
	}
	
	
	/**
	 * 测试double intvalue取值
	 */
	public static void doubleIntVal(){
		
		
		Double d  = 1.9D;
		System.out.println(d.doubleValue());
	}
	
	
	public static void testForEach(){
		
		String[] strArr= null;
		
		for(String str:strArr){
			System.out.println(str);
		}
	}
	
	public static boolean arrayDefaulyEquals(){
		
		String[]  s ={"1","2"};
		String[]  s1 ={"1","2"};
		return s1.equals(s); 
	}
	
}
