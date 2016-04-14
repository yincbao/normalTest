package junit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cpw.desginpatten.singleton.LazyModel;

public class testSigleton {
	

	@BeforeClass
	public  static void setupBeforeTest() {
		//System.out.println("test start");
	}

	class testThread implements Runnable{
		public void run() {
			System.out.println("status is "+LazyModel.getInstanceSynchronize().status);
			try {
//				Thread.sleep(1*1000);
//				System.out.println("sleep for a second!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	@Test
	public void testHugryModel(){
		for(int i=0; i<5; i++){
			new testThread().run();
			System.out.println("----NEXT-----");
		}
	}
	
	@Test
	public void testLazyModel(){
		for(int i=0; i<20; i++){
			new testThread().run();
			System.out.println("----NEXT-----");
		}
	}
	
}
