package com.cpw;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public static void main(String arg[]){
		System.out.println("range of this month : "+getLongRangeOfMonth(new Date())[0]+"  ~ "+getLongRangeOfMonth(new Date())[1]);
		System.out.println("range of this week  : "+getLongRangeOfThisWeek(new Date())[0]+"  ~ "+getLongRangeOfThisWeek(new Date())[1]);
		System.out.println("range of last week  : "+getLongRangeOfLastWeek(new Date())[0]+" ~  "+getLongRangeOfLastWeek(new Date())[1]);
		System.out.println("range of today      : "+getLongRangeOfDay(new Date())[0]+"  ~  "+getLongRangeOfDay(new Date())[1]);
	}
	
	/**
	 * Date 转mysql datetime
	 * @return
	 */
	public static java.sql.Date  MysqlDateTime(Date date){
		return new java.sql.Date(date.getTime());
	}
	
	/**
	 * 获取给定时间月的始末utc时间
	 * @param date
	 * @return long[0] starttimg, long[1] endtime
	 */
	public static long[] getLongRangeOfMonth(Date date){
		long[] re = new long[2];
		try {
			date = date!=null?date:new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);
			
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.set(Calendar.DATE, 1); 
			re[0] = df2.parse(df.format(calStart.getTime())+"  00:00:00").getTime();
			
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			calEnd.set(Calendar.DATE, 1); 
			calEnd.add(Calendar.MONTH, 1); 
			calEnd.add(Calendar.DATE, -1); 
			re[1] =  df2.parse(df.format(calEnd.getTime())+"  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
		
	}
	
	/**
	 * 获取给定时间月的始末utc时间
	 * @param date
	 * @return long[0] starttimg, long[1] endtime
	 */
	public static long[] getLongRangeOfThisWeek(Date date){
		long[] re = new long[2];
		try {
			date = date!=null?date:new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);
			
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.set(Calendar.DAY_OF_WEEK, 1);
			re[0] = df2.parse(df.format(calStart.getTime())+"  00:00:00").getTime();
			
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			calEnd.set(Calendar.DAY_OF_WEEK, 7); 
			re[1] =  df2.parse(df.format(calEnd.getTime())+"  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
		
	}
	
	/**
	 * 获取给定时间月的始末utc时间
	 * @param date
	 * @return long[0] starttimg, long[1] endtime
	 */
	public static long[] getLongRangeOfLastWeek(Date date){
		long[] re = new long[2];
		try {
			date = date!=null?date:new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);
			
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			calStart.add(Calendar.WEEK_OF_YEAR, -1);
			calStart.set(Calendar.DAY_OF_WEEK, 1); 
			re[0] = df2.parse(df.format(calStart.getTime())+"  00:00:00").getTime();
			
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			calEnd.add(Calendar.WEEK_OF_YEAR, -1);
			calEnd.set(Calendar.DAY_OF_WEEK, 7); 
			re[1] =  df2.parse(df.format(calEnd.getTime())+"  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
		
	}
	
	public static long[] getLongRangeOfDay(Date date){
		long[] re = new long[2];
		try {
			date = date!=null?date:new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df2 = new SimpleDateFormat(dateFormat);
			
			Calendar calStart = Calendar.getInstance();
			calStart.setTime(date);
			re[0] = df2.parse(df.format(calStart.getTime())+"  00:00:00").getTime();
			
			Calendar calEnd = Calendar.getInstance();
			calEnd.setTime(date);
			re[1] =  df2.parse(df.format(calEnd.getTime())+"  23:59:59").getTime();
			return re;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return re;
		
	}

}
