package com.cpw.obd;

import java.util.Date;

/**
 * 16进制 ascII 互转 ClassName: StringToHex
 * 
 * @description
 * @author yin_changbao
 * @Date Oct 16, 2015
 *
 */
public class StringToHex {

	public static String convertStringToHex(String str) {

		char[] chars = str.toCharArray();

		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}

		return hex.toString();
	}

	public static String convertHexToString(String hex) {

		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();

		// 49204c6f7665204a617661 split into two characters 49, 20, 4c...
		for (int i = 0; i < hex.length() - 1; i += 2) {

			// grab the hex in pairs
			String output = hex.substring(i, (i + 2));
			// convert hex to decimal
			int decimal = Integer.parseInt(output, 16);
			// convert the decimal to character
			sb.append((char) decimal);

			temp.append(decimal);
		}

		return sb.toString();
	}

	public static void int2Hex(Integer in) {
		System.out.println(Integer.toHexString(in));
	}

	// 504F533838383834 POS88884
	public static void main(String[] args) {
		System.out.println(Integer.toHexString(3960));
		StringToHex strToHex = new StringToHex();
		String hex = ("4944445F3231364730325F532056312E322E3100");
		System.out.println("\n***** 16进制转换为ASCII *****");
		System.out.println("Hex : " + hex);
		System.out.println("ASCII : " + strToHex.convertHexToString(hex));

		System.out.println("\n-----ASCII码转换为16进制 -----");
		String str = "a";
		System.out.println("字符串: " + str);
		String hex1 = strToHex.convertStringToHex(str);
		System.out.println("转换为16进制 : " + hex1);

		int2Hex(1325376000);

		print((Integer.parseInt(("0f78"), 16)));
		
		print(Long.toBinaryString(0xAF));
		print((Integer.parseInt(("1010"), 2)));

	}

	public static String reverse(String s) {

		int length = s.length();

		String reverse = ""; 

		for (int i = 0; i < length; i++)

			reverse = s.charAt(i) + reverse;

		return reverse;

	}

	private static void print(Object obj) {
		System.out.println(obj);
	}
}