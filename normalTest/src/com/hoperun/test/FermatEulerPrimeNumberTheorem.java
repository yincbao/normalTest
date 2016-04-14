package com.hoperun.test;

/**
 * 
 * ClassName: FermatEulerPrimeNumberTheorem
 * 
 * @description 遍历符合费马-欧拉素数定理数
 * @author yin_changbao
 * @Date Jul 23, 2015
 *
 */
public class FermatEulerPrimeNumberTheorem {

	/**
	 * 遍历打印给定范围（至少大于等于5）以内符合费马-欧拉素数定理数
	 * 
	 * @param input
	 */
	public static void ergodicFermatEulerPrimeNumber(int input) {
		for (int i = 5; i <= input; i = i + 4) {//满足4n+1
			if (isPrime(i)) {//是素数
				printFermatPrime(i);
			}
		}
	}

	/**
	 * 判断传入参数是否为素数
	 * 
	 * @param input 待判定数
	 * @return true是素数，false不是素数
	 */
	public static boolean isPrime(long input) {
		boolean isPrime = true;
		if (input > 2) { // 如果n是大于2的偶数 认定不是素数 修改变量值为false
			if (input % 2 == 0) {
				isPrime = false;
			} else {
				for (int i = 3; i <= Math.sqrt(input); i += 2) {
					if (input % i == 0) {
						isPrime = false;
						break;
					}
				}
			}
		}
		return isPrime;
	}

	/**
	 * 打印符合费马-欧拉素数定理数
	 * 算法解释：设a为较小值，a=b的时候，a取值为num一般的平方根。所以a取值不可能超过Math.sqrt(num / 2)，
	 * 依此缩小穷举范围，然后for循环穷举出a，b值。
	 * 另此方法也可由递归穷举实现，但是遇到大素数可能会出现嵌套层次过多导致stackoverflowError. 于是舍弃。
	 * @param num 需要打印的符合费马-欧拉素数定理数
	 */
	private static void printFermatPrime(int num) {
		
		for (int a = 1; a <= Math.sqrt(num / 2); a++) {
			int b = (int) Math.sqrt(num - a * a); // java double强转int，向下取整。
			if ((a * a + b * b) == num) {
				System.out.println(num + "=" + a + "*" + a + "+" + b + "*" + b);
				break;
			}
		}

	}
	
	/**
	 * main方法，本例要求10000范围内符合费马-欧拉素数定理数，
	 * @param args
	 */
	public static void main(String args[]) {
		ergodicFermatEulerPrimeNumber(10000);
	}
}

