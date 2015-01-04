import java.util.*;

/**
 * This file contains the solutions for longest increasing sequence
 * Dynamical programming O(N^2)
 * patience sorting O(nlog(n))
 * duplicate
 */


public class LISStudy {
	/**
	 * find the length of LIS
	 * dynamical programming
	 * find the length of LIS
	 */
	public int LISlen(int[] array) {
		if(array == null || array.length == 0) return 0;
		int[] dp = new int[array.length];
		int maxlen = 0;
		for(int i = 0; i < array.length; i++) {
			dp[i] = 1;//every element has a length of 1
			for(int j = i - 1; j >= 0; j--) {
				if(array[j] <= array[i] && dp[j] + 1 > dp[i]) {
					dp[i] = dp[j] + 1;
				}
			}
			maxlen = maxlen < dp[i] ? dp[i] : maxlen;
		}
		return maxlen;
	}
	/**
	 * find the solution of LIS
	 * Dynamical programming
	 * need to use another array to record the index of previous element in LIS
	 */
	public List<Integer> LISsol(int[] array) {
		LinkedList<Integer> res = new LinkedList<Integer>();
		if(array == null || array.length == 0) return res;
		int[] dp = new int[array.length];
		int[] preindex = new int[array.length];
		int maxindex = 0;
		for(int i = 0; i < array.length; i++) {
			dp[i] = 1;
			preindex[i] = -1;
			for(int j = i - 1; j >= 0; j--) {
				if(array[j] <= array[i] && dp[i] < dp[j] + 1) {
					dp[i] = dp[j] + 1;
					preindex[i] = j;
				}
			}
			if(dp[maxindex] < dp[i]) {
				maxindex = i;
			}
		}
		for(int i = maxindex; i >= 0; i = preindex[i]) {
			res.addFirst(array[i]);
		}
		return res;
	}
	/**
	 * find the length of LIS
	 * use patience sorting
	 * O(nlog(n))
	 */
	public int LISFastlen(int[] array) {
		if(array == null || array.length == 0) return 0;
		int[] c = new int[array.length];//c[i] is the smallest end element of a length i LIS
		c[0] = array[0];
		int len = 1;
		for(int i = 1; i < array.length; i++) {//update the first one
			if(array[i] < c[0]) {
				c[0] = array[i];
			}
			else if(array[i] > c[len-1]) {//extend
				c[len++] = array[i];
			}
			else {
				int pos = binsearch(c,0,len-1,array[i]);
				c[pos] = array[i];
			}
		}
		return len;
	}
	//find the index such that c[index-1] < key < c[index]
	private int binsearch(int[] c, int L, int R, int key) {
		while(L <= R) {
			int mid = L + (R - L) / 2;
			if(c[mid] == key) return mid;
			else if(key < c[mid]) R = mid - 1;
			else L = mid + 1;
		}
		return L;
	}
	/**
	 * find the solution of LIS
	 * use patience sorting
	 * O(nlog(n))
	 */
	public List<Integer> LISFastsol(int[] array) {
		if(array == null || array.length == 0) return new LinkedList<Integer>();
		int[] c = new int[array.length];//store the index of the smalleset end elements of a length i LIS
		int[] preindex = new int[array.length];
		preindex[0] = -1;
		c[0] = 0;
		int len = 1;
		for(int i = 1; i < array.length; i++) {
			if(array[i] < array[c[0]]) {
				c[0] = i;
			}
			else if(array[i] > array[c[len-1]]) {
				preindex[i] = c[len-1];//array[i]'s previous element
				c[len++] = i;
			}
			else {
				int pos = binsearchII(array,c,0,len-1,array[i]);
				preindex[i] = c[pos-1];
				c[pos] = i;
			}
		}
		LinkedList<Integer> res = new LinkedList<Integer>();
		for(int i = c[len-1]; i >= 0; i = preindex[i]) {
			res.addFirst(array[i]);
		}
		return res;
	}
	private int binsearchII(int[] a, int[] c, int L, int R, int key) {
		while(L <= R) {
			int mid = L + (R - L) / 2;
			if(a[c[mid]] == key) return mid;
			else if(key < a[c[mid]]) R = mid - 1;
			else L = mid + 1;
		}
		return L;
	}
	public static void main(String[] args) {
		LISStudy sol = new LISStudy();
		int[] a = new int[]{2, 5, 3, 7, 11, 8, 10, 13, 6,-9,14};
		System.out.println(sol.LISlen(a));
		System.out.println(sol.LISsol(a));
		System.out.println(sol.LISFastlen(a));
		System.out.println(sol.LISFastsol(a));
	}
}