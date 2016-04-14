package com.cpw.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalUtil {

	/**
	 * 自动四舍五入
	 * @param orig
	 */
	public static void double2StringDecimalFormat(double orig) {
		DecimalFormat df = new DecimalFormat("######0.00");
		double d1 = 1234567890123.23256D;
		double d2 = 0.0;
		double d3 = 2.0;
		System.out.println(df.format(d1));
		System.out.println(df.format(d2));
		System.out.println(df.format(d3));
	}

	/**
	 * 可指定是否四舍五入
	 */
	public static void double2StringBigDecimal() {
		double f = 111231.5585;
		BigDecimal b = new BigDecimal(f);
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();//BigDecimal.ROUND_HALF_UP
		System.out.println(f1);
	}
	
	/**
	 * 自动四舍五入
	 * @param orig
	 */
	public static void double2StringString(){
		double d = 1233.1415926;
		String result = String .format("%.2f",d);
		System.out.println(result);
	}
	
	public static void main(String arg[]){
		double2StringDecimalFormat(8.0d);
		double2StringBigDecimal();
		double2StringString();
		double2StringNumberFormat();
	}
	
	/**
	 * 自动四舍五入
	 * @param orig
	 */
	public static void double2StringNumberFormat(){

		NumberFormat ddf1=NumberFormat.getNumberInstance() ;
		ddf1.setMaximumFractionDigits(2); 
		double x=23.5455; 
		String s= ddf1.format(x) ; 
		System.out.println(s);

	}
}

