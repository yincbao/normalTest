package com.cpw.colloectionTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentHashMap {
	
	LinkedHashMap lhm = new LinkedHashMap();
	LinkedHashSet lhs = new LinkedHashSet();
	Map sm = Collections.synchronizedMap(lhm);
	
	ConcurrentHashMap chm;
	ConcurrentSkipListSet chs;
	ConcurrentLinkedQueue clq;
	
	public static void test(){
		Map map = new HashMap();
		for(int i = 0; i<10;i++){
			map.put(i, i);
		}
		
		
		
	}
}