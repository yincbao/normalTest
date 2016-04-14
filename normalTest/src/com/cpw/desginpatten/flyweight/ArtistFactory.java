package com.cpw.desginpatten.flyweight;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ArtistFactory {
	static Map<String, Artist> artistPool = Collections.synchronizedMap(new HashMap<String, Artist>());
	
	public static  Artist getArtist(String artistName){
		Artist instance;
		instance = artistPool.get(artistName);
		if(instance == null){
			instance = new ArtistImpl();
			instance.setAname(artistName);
			artistPool.put(artistName, instance);
			System.out.println("search no matchng, adding artist:  "+instance.getAname());
		}
		return instance;
		
	}
}
