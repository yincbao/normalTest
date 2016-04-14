package com.cpw.enumeration;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class EnumerHelper {

	private static Set<Entity> set = Collections
			.synchronizedSet(new HashSet<Entity>());

	public static Enumeration<? extends Entity> getEntry(){
		return new Enumeration<Entity>(){

			private int i = 0;
            public boolean hasMoreElements() {
                synchronized (this) {
                    return i < set.size();
                }
            }
            public Entity nextElement() throws NoSuchElementException {
                synchronized (this) {
                    Iterator<Entity> ite = set.iterator();
                    Entity e = ite.next();
                    set.remove(e);
                    return e;
                }
            }
		};
	}
	
	public static void addEntry(Set<Entity> seta){
		set.addAll(seta);
	}
	
}
