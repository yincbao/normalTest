package com.cpw.corejava;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
   
 public class WeakReferenceTest {  
     public static void main1(String[] args) throws Exception {  
         String a = new String("a");  
         String b = new String("b");  
         Map weakmap = new WeakHashMap();  
         Map map = new HashMap();  
         map.put(a, "aaa");  
         map.put(b, "bbb");  
   
           
         weakmap.put(a, "aaa");  
         weakmap.put(b, "bbb");  
           
         //map.remove(a);  
           
       a=null;  
       b=null;  
           
         System.gc();  
         Iterator i = map.entrySet().iterator();  
         while (i.hasNext()) {  
             Map.Entry en = (Map.Entry)i.next();  
             System.out.println("map:"+en.getKey()+":"+en.getValue());  
         }  
   
         Iterator j = weakmap.entrySet().iterator();  
         while (j.hasNext()) {  
             Map.Entry en = (Map.Entry)j.next();  
             System.out.println("weakmap:"+en.getKey()+":"+en.getValue());  
               
         }  
     }  
   
     
     public static void main2(String[] args) {
    	 ele a = new ele("name1","id1");
    	 ele b = new ele("name2","id2");
		Map weakmap = new WeakHashMap(); 
		Map map = new HashMap(); 
		weakmap.put("a", a);
		weakmap.put("b", b);
		map.put("a", a);
		map.put("b", b);
		b.name="name3";
		b = null;
		//System.gc(); 
		Iterator<ele> i = map.entrySet().iterator();  
        while (i.hasNext()) {  
            Map.Entry en = (Map.Entry)i.next();  
            System.out.println("map:"+en.getKey()+":"+((ele)en.getValue()).name);  
        }  
  
        Iterator j = weakmap.entrySet().iterator();  
        while (j.hasNext()) {  
            Map.Entry en = (Map.Entry)j.next();  
            System.out.println("weakmap:"+en.getKey()+":"+((ele)en.getValue()).name);  
              
        }  
	}
     
     
 	public static void main3(String[] args) throws Exception {

		List<WeakHashMap<byte[][], byte[][]>> maps = new ArrayList<WeakHashMap<byte[][], byte[][]>>();

		for (int i = 0; i < 1000; i++) {
			WeakHashMap<byte[][], byte[][]> d = new WeakHashMap<byte[][], byte[][]>();
			d.put(new byte[1000][1000], new byte[1000][1000]);
			maps.add(d);
			System.gc();
			System.err.println(i);


		}

	}
 	
 	public static void main(String[] args) throws Exception {

		//List<WeakHashMap<byte[][], byte[][]>> maps = new ArrayList<WeakHashMap<byte[][], byte[][]>>();
 		WeakHashMap<String, byte[][]> d = new WeakHashMap<String, byte[][]>();
 		Map<String, byte[][]> d1 = new HashMap<String, byte[][]>();
		for (int i = 0; i < 100000; i++) {
			String key1 = new String(String.valueOf(i));
			String key2 = new String(String.valueOf(i));
			d.put(key1, new byte[1000][1000]);
			d1.put(key2, new byte[1000][1000]);
		//	maps.add(d);
			//System.gc();
			System.err.println(i+" "+d.size()+" in hashmap:"+d1.size());

		}
	}
     
       
 } 
 class ele{
	 String name;
	 String id;
	public ele(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}
	 
 }
