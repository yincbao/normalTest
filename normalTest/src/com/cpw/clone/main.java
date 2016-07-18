package com.cpw.clone;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class main {
	
	/**
	 * phatom clone
	 * @param args
	 */
	public static void main1(String[] args) {
		PhatomClone pc = new PhatomClone();
		pc.setCanBeClone(100);
		TargetObj to = new TargetObj("PhatomCloe");
		pc.setTargetObj(to);
		PhatomClone pcClone = (PhatomClone)pc.clone();
		System.out.println("befor target Changed original: "+pcClone.getTargetObj().getFlag());
		System.out.println("befor target Changed clone: "+pcClone.getTargetObj().getFlag());
		to.setFlag("phatom1");
		System.out.println("after target changed original: "+pcClone.getTargetObj().getFlag());
		System.out.println("befor target Changed clone: "+pcClone.getTargetObj().getFlag());
	}
	
	
	public static void main2(String[] args) {
		DeepClone dc = new DeepClone();
		dc.setStatus(100);
		TargetObj to = new TargetObj("DeepCloe");
		dc.setTo(to);
		dc.setDate(new Date());
		dc.setLognVal(1000L);
		DeepClone pcClone = (DeepClone)dc.clone();
		System.out.println("befor target Changed original: "+dc.getTo().getFlag()+dc.getDate()+dc.getLognVal());
		System.out.println("befor target Changed clone: "+pcClone.getTo().getFlag()+pcClone.getDate()+pcClone.getLognVal());
		to.setFlag("phatom1");
		dc.setDate(new Date(1406469600000L));
		dc.setLognVal(1001L);
		System.out.println("befor target Changed original: "+dc.getTo().getFlag()+dc.getDate()+dc.getLognVal());
		System.out.println("after target changed clone: "+pcClone.getTo().getFlag()+pcClone.getDate()+pcClone.getLognVal());
	}
	
	
	public static void main(String[] args) {
		
		
		System.out.println(System.currentTimeMillis()-new Date().getTime());
		System.out.println(TimeUnit.DAYS.toMillis(7));
		PhatomClone pc = new PhatomClone();
		pc.setCanBeClone(100);
		TargetObj to = new TargetObj("PhatomCloe");
		pc.setTargetObj(to);
		PhatomClone1 pcClone = (PhatomClone1)pc.clone();
		System.out.println("befor target Changed original: "+pcClone.getTargetObj().getFlag());
		System.out.println("befor target Changed clone: "+pcClone.getTargetObj().getFlag());
	}
	
}
