package com.cpw;

public class PGenerator {
	
	public static void main(String args[]){
		String org = " c0 7d d6 56 c2 82 d6 56 01 1c 6d 00 23 f9 00 00 ab e0 00 00 30 01 00 00 04 00 03 64 01 11 1f 00 03 02 01 1a 02 10 0a 2a 0d 18 4f da 06 f0 b9 81 19 a9 00 00 00 03 01 e8 03 24 24 0d 0a";
		
		System.out.println("0x"+(org.trim()).replaceAll("\\s+", ",0x"));
		
	}

}
