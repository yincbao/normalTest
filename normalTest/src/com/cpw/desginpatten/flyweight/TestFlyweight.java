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
		 * ����a-z��artist����Ԫ
		 */
		for(char a='a';a<'z';a++){
			Artist obj = new ArtistImpl();
			obj.setAname(a+"");
		}
		
		/**
		 * ����10��cd��������һ��artist
		 */
		for(int i=0;i<10;i++){
			CD cobj = new CDImpl();
//			cobj.setArtist("a");�����ûʹ��flyweight����
			Artist temp = ArtistFactory.getArtist("a");
			cobj.setArtist(temp);
			System.out.println(cobj.getArtist().getAname());
			
		}
	}

}
