package com.cpw;

 
public class TestSynchronized implements Runnable{
 //private static List<String> j = new ArrayList<String>();
 private volatile static Integer j = new Integer(0);
 
 public void run() {
  synchronized(j){
//   System.out.println(j.size());
//   j.add("a");
   System.out.println(Thread.currentThread().getName()+": "+j);
   j++;
  }
 }
 
 public static void main(String[] args){
  for(int i=0;i<100;i++){
   TestSynchronized a = new TestSynchronized();
   Thread t = new Thread(a);
   t.setName("No."+i);
  // System.out.println("ggNo."+i+": "+a.j);
   t.start();
  }
  
  
  Integer i1 = 100;
  Integer i2 = 100;
  Integer i3 = 1000;
  Integer i4 = 1000;
  System.out.println(i1==i2);
  System.out.println(i3==i4);

 }
}




