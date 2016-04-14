package com.cpw.exit;

public class SystemExit {
	
	public static String test(){
		
		try{
			System.out.println("in try before return");
			//System.exit(0);
			return "I am return";
		}catch(Exception e){
			
			System.out.println("in catch");
			
		}finally{
			System.out.println("in finally");
			//System.exit(0);
			
		}
		return "";
	}
	
	public static void main(String[] args) {
		
		System.out.println(test());
	}

}
