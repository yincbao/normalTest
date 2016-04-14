package com.cpw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ICMP {
	public static void main1(String[] args) {
		String host = "209.116.186.251";
		int timeOut = 3000;
		try {
			boolean status = InetAddress.getByName(host).isReachable(timeOut);

			System.out.println(status);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String line = null;
		Process pro;
		try {
			long t1 = System.currentTimeMillis();
			//pro = Runtime.getRuntime().exec("ping 192.168.83.128");
			pro = Runtime.getRuntime().exec("ping 172.16.16.107");
			BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			while ((line = buf.readLine()) != null){
				System.out.println(line);
			}
			
			long t2 = System.currentTimeMillis();
			System.out.println(t2-t1);
			
			//boolean status = InetAddress.getByName("192.168.83.128").isReachable(300000);
			boolean status = InetAddress.getByName("172.16.16.107").isReachable(300000);
			System.out.println(status);
			
			long t3 = System.currentTimeMillis();
			System.out.println(t3-t2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
