package com.cpw;

import java.util.ArrayList;
import java.util.List;

/**
 * 遍历给定整数范围（main函数给定10000）以内符合费马-欧拉素数定理数
 * ClassName: FermatEulerPrimeNumberTheorem
 * @description
 * @author yin_changbao
 * @Date   Jul 23, 2015
 *
 */
public class FermatEulerPrimeNumberTheorem2{
	
	
	
	
	public static void main(String args[]){
		FermatEulerPrimeNumberTheorem2 instance = new FermatEulerPrimeNumberTheorem2();
		instance.ergodicFermatEulerPrimeNumber(1000000);
	}
	
	/**
	 * 遍历10000以内符合费马-欧拉素数定理数
	 * 时间复杂度为O(nlgn),空间复杂度为O(lgn),fermatPrimeList为保证时间复杂度牺牲的空间复杂度
	 * @param args
	 */
	public void ergodicFermatEulerPrimeNumber(long max){
		long startTime = System.currentTimeMillis();
		//此处时间复杂度为O(nlgn)
		List<Integer> fermatPrimeList = findFitsdNumFromRange(max);
		//此处for循环时间复杂度为O(n)
		for(int fermatPrime:fermatPrimeList ){
			//目前只想到递归遍历的方式计算满足a*a+b*b=费马-欧拉素数定理数
			printFermatPrime(fermatPrime);
		}
		System.out.println("Iterate Fermat Euler Prime Numbe less than 10000 costs "+(System.currentTimeMillis()-startTime) +"Millis");
	}

	
	/**
	 * 计算3~maxRange范围内素数，且满足4n+1
	 * 时间复杂度为 O(n*logn),maxRange以10000计，需要循环最坏情况为：（2500-3）*（100-3）=242209次
	 * @param maxRange
	 * @return 列表
	 */
	public List<Integer> findFitsdNumFromRange(long maxRange) { 
		boolean bool; 
		List<Integer> primeList = new ArrayList<Integer>();
		for (int i = 5; i < maxRange; i+=2) {
			bool = true; 
			for (int j = 2; j <= Math.sqrt(i); j++) { 
				if (i % j == 0) { 
					bool = false; 
				}
				if(bool&&!fermatRequirsOne(i)){
					bool = false; 
					break;
				}
			} 
			if (bool) 
				primeList.add(i);
		}
		return primeList; 
	}
	
	/**
	 * 计算number是否满足4n+1,从5开始
	 * @param number
	 * @return
	 */
	public boolean fermatRequirsOne(long number){
		return number>=5&&(number-1)%4==0;
	}
	
	
	/**
	 * 打印符合费马-欧拉素数定理数
	 * @param num
	 */
	public  void printFermatPrime(int num){
		int a = (int) Math.sqrt(num);
		int numPart2 = (int) Math.pow(a,2);
		int b = (int) Math.sqrt(num-numPart2);
		determinant(a,b,num);
		
	}
	
	/**
	 * 计算出组成符合费马-欧拉素数定理数的两数平方和之两数
	 * @param a
	 * @param b
	 * @param num
	 */
	public  void determinant(int a,int b,int num){
		if((int) Math.pow(a,2)+(int) Math.pow(b,2)!=num){
			a -=1;
			int numPart2 = (int) Math.pow(a,2);
			b = (int) Math.sqrt(num-numPart2);
			determinant( a, b, num);
		}else{
			System.out.println(num+"="+b+"*"+b+"+"+a+"*"+a);
		}
	}
}
