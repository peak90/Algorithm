import java.util.*;

/**
*This file contains some bit algorithm
**/

public class BitMan {
	//add one without +
	public int addOne(int a) {
		int i = 1;
		int n = a;
		while((n & 1) != 0) {
			n = n >> 1;
			i = i << 1;
		}
		int mask = ~(i - 1);
		return (a & mask) | i;
	}
	//implement + with bit manipulation	
	public int add(int a, int b) {
		while(b != 0) {
			int carry = a & b; //carry bits
			a = a ^ b; //add without carry bits
			b = carry << 1; //next b
		}
		return a;
	}
	//find the single missing number in an array
	//use XOR
	public int missingNum(int[] a) {
		int res = 0;
		for(int i = 0; i < a.length; i++) {
			res ^= a[i];
		}
		return res;
	}
	//find the number appears M times
	//in an array, N-1 number appears N times, and one number
	//appear M times, M % N != 0, find that number
	//count the 1s at that bit
	public int missingNumII(int[] a, int N) {
		if(a == null || a.length == 0) return 0;
		int res = 0;
		for(int i = 0; i < 32; i++) {
			int ones = 0;
			for(int j = 0; j < a.length; j++) {
				ones += (a[j] >> i) & 1;
			}
			if(ones % N != 0) {
				res |= (1 << i);
			}
		}
		return res;
	}
		
	
	
	public static void main(String[] args) {
		BitMan sol = new BitMan();
		int[] a = new int[]{2,2,2,3,3,3,4,4,4,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8};
		System.out.println(sol.missingNumII(a,3));
	}
}
