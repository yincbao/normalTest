package com.cpw.comparator;

import java.util.Comparator;

public class MyComparator implements Comparator<PersonComparator>{

	public int compare(PersonComparator o1, PersonComparator o2) {

		return o1.getId()-o2.getId();
	}
	
}
