package com.cpw.test.file.xplay;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BaseComparator implements Comparator<Object>{
	
static Map<String, BaseComparator> SMPComparatorPool = Collections.synchronizedMap(new HashMap<String, BaseComparator>());
	
	@Override
	public int compare(Object o1, Object o2) {
		int compareResult = 0;
		if(o1 instanceof Nurl&&o2 instanceof  Nurl){
			Nurl nurl1 = (Nurl) o1;
			Nurl nurl2 = (Nurl) o2;
			compareResult = compareNurl(nurl1,nurl2);
		}
		return compareResult;
	}
	
	public int compareNurl(Nurl nurl1, Nurl nurl2) {
		
		int compareResult = 0;
		return compareResult = nurl1.getUrl().compareTo(nurl2.getUrl());
	}
	
}
