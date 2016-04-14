package com.hoperun.test;

/*****************************************************************************
*
*                      HOPERUN PROPRIETARY INFORMATION
*
*          The information contained herein is proprietary to HopeRun
*           and shall not be reproduced or disclosed in whole or in part
*                    or used for any design or manufacture
*              without direct written authorization from HopeRun.
*
*            Copyright (c) 2014 by HopeRun.  All rights reserved.
*
*****************************************************************************/

/**
* ClassName: FermatPrime
*
* @description
* @author xing_peng
* @Date 2015-7-23
* 
*/
public class FermatPrime {

	/**
	 * 输出10000以内4n+1, 可拆分成 A*A + B*B 的素数
	 */
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		for (int i = 5; i <= 10000; i = i + 4) {
			if (isPrime(i)) {
				split(i);
			}
		}
		System.out.println("ergodic Fermat Euler Prime Numbe less than 10000 costs "+(System.currentTimeMillis()-startTime) +"Millis");
	}

	/**
	 * 判断一个数是否为素数，若为素数，返回true,否则返回false
	 * 
	 * @param a
	 *            输入的整數
	 * 
	 * @return true、false
	 */
	public static boolean isPrime(int a) {

		boolean flag = true;

		// 4n+1不能被偶数整除
		for (int i = 3; i <= Math.sqrt(a); i = i + 2) {
			if (a % i == 0) { // 若能被整除，则说明不是素数，返回false
				flag = false;
				break;
			}
		}

		return flag;
	}

	/**
	 * 拆分素数为两个数平方和
	 * 
	 * @param a
	 *            需要拆分的素數
	 * 
	 */
	public static void split(int a) {

		// i 的范围 取： 从1开始，到 a的一半取平方根
		for (int i = 1; i <= Math.sqrt(a / 2); i++) {
			int j = (int) Math.sqrt(a - i * i); // a = i*i + j*j, 已知 a,i 算出j,取整
			if ((i * i + j * j) == a) { // 判断 是否满足条件
				System.out.println(a + "=" + i + "*" + i + "+" + j + "*" + j);
				break;
			}
		}

	}
}
