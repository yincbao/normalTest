package com.cpw.obd;

public class Main {

	
	public static void main(String args[]){
		Integer i = 196;
		byte b = (byte) 0xC4;
		System.out.println(int2OneByte(i));;
		System.out.println(b);;
	}
	
	public static byte int2OneByte(int num) {  
        return (byte) (num & 0x000000ff);  
    } 
}
