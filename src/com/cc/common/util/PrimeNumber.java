package com.cc.common.util;

import java.util.HashMap;
import java.util.Map;

public class PrimeNumber {
	private static final Map<Integer, int[]>	primeMap	= new HashMap<Integer, int[]>();

	/**
	 * 查找下一个素数
	 * 
	 * @param max
	 *        最大的数
	 * @param pre
	 *        上一个素数
	 * @return 如果没有下一个则返回0；如果上一个素数不在范围内，返回-1。
	 */
	public static int getNextPrime(int max, int pre) {
		int[] primeArr = primeMap.get(max);
		if (primeArr == null) {
			primeArr = generatorPrimes(max);
			primeMap.put(max, primeArr);
		}
		return getNext(primeArr, pre);
	}
	/**
	 * 寻找下一个
	 * 
	 * @param primeArr
	 * @param pre
	 */
	private static int getNext(int[] primeArr, int pre) {
		for (int i = 0; i < primeArr.length; i++) {
			if (pre == primeArr[i]) {
				if (primeArr.length > i + 1) {
					return primeArr[i + 1];
				} else {
					return 0;
				}
			}
		}
		return -1;
	}
	/**
	 * 创建素数
	 * 
	 * @param max
	 */
	private static int[] generatorPrimes(int max) {
		int size = max + 1;
		int[] allArr = new int[size];
		// 0和1都不是素数
		allArr[0] = 1;
		allArr[1] = 1;
		// 挖空
		for (int i = 2; i < Math.sqrt(size - 1); i++) {
			if (allArr[i] == 0) {
				for (int j = 2; j <= ((size - 1) / i); j++) {
					allArr[i * j] = 1;
				}
			}
		}
		// 素数数量
		int primeCount = 0;
		for (int i = 0; i < allArr.length; i++) {
			primeCount += allArr[i];
		}
		primeCount = size - primeCount;
		// 素数数组
		int[] primeArr = new int[primeCount];
		int index = 0;
		for (int i = 0; i < allArr.length; i++) {
			if (allArr[i] == 0) {
				primeArr[index++] = i;
			}
		}
		return primeArr;
	}
}
