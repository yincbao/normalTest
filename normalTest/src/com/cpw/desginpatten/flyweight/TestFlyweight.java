package com.cpw.desginpatten.flyweight;

import org.junit.Before;
import org.junit.Test;

public class TestFlyweight {
	
	@Before
	public void init(){
	}

	@Test
	public void test() {
		/**
		 * 创建a-z个artist，享元
		 */
		for(char a='a';a<'z';a++){
			Artist obj = new ArtistImpl();
			obj.setAname(a+"");
		}
		
		/**
		 * 创建10个cd，都公用一个artist
		 */
		for(int i=0;i<10;i++){
			CD cobj = new CDImpl();
//			cobj.setArtist("a");这就是没使用flyweight做法
			Artist temp = ArtistFactory.getArtist("a");
			cobj.setArtist(temp);
			System.out.println(cobj.getArtist().getAname());
			
		}
	}

}
