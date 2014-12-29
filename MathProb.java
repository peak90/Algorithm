import java.util.*;

/**
 * This file contains some usual math problems
 */

public class MathProb {
	public static void main(String[] args) {
		MathProb sol = new MathProb();
		int[] array = new int[]{1,2,3,4,5,6,7,8,9,10};
		sol.shuffle(array);
		System.out.println(Arrays.toString(array));
	}
	/**
	 * Check prime
	 * use the basic 1 to sqrt(n)
	 */
	public boolean isPrime(int n) {
		if(n == 1) return false;
		if(n == 2) return true;
		for(int i = 2; i <= (int)Math.sqrt(n); i++) {
			if(n % i == 0) return false;
		}
		return true;
	}

	/**
	 * generate all primes less or equal to N
	 * Time complexity: O(nloglog(N))
	 * Space complexity: O(N)
	 */
	public List<Integer> generatePrimes(int N) {
		List<Integer> res = new LinkedList<Integer>();
		if(N <= 1) return res;
		boolean[] flags = new boolean[N+1];
		int prime = 2;
		while(prime <= (int)Math.sqrt(N)) {
			crossFlags(prime,flags);
			prime = nextPrime(prime,flags);
		}
		for(int i = 2; i < flags.length; i++) {
			if(!flags[i]) {
				res.add(i);
			}
		}
		return res;
	}
	private void crossFlags(int prime, boolean[] flags) {
		for(int i = prime*prime; i < flags.length; i += prime) {
			flags[i] = true;
		}
	}
	private int nextPrime(int prime, boolean[] flags) {
		for(int i = prime + 1; i < flags.length; i++) {
			if(!flags[i]) return i;
		}
		return -1;
	}
	/**
	 * implement double sqrt(double)
	 * using binary search
	 */
	public double sqrt(double n) {
		if(n < 0) throw new ArithmeticException();
		double L = 0;
		double R = n;
		while(Math.abs(L-R) > 1e-9) {
			double mid = (L + R) / 2;
			if(mid * mid == n) return mid;
			else if(mid * mid > n) R = mid;
			else L = mid;
		}
		return L;
	}
	/**
	 * 
	 */
	public double sqrtNT(double n) {
		if(n < 0) throw new ArithmeticException();
		double pre = 0;
		double cur = 1;
		while(pre != cur) {
			pre = cur;
			cur = (cur + n / cur) / 2;
		}
		return pre;
	}
	/**
	 * implement int sqrt(int)
	 * using binary search
	 */
	public int sqrt(int n) {
		if(n < 0) throw new ArithmeticException();
		long L = 0;
		long R = n / 2;
		while(L <= R) {
			long mid = L + (R - L) / 2;
			if(mid * mid == n || mid * mid < n && (mid+1)*(mid+1) > n) return (int)mid;
			else if(mid * mid < n) L = mid + 1;
			else R = mid - 1;
		}
		return (int)L;
	}
	/**
	 * implement using new ton
	 */
	public int sqrtNT(int n) {
		if(n < 0) throw new ArithmeticException();
		double pre = 0;
		double cur = 1;
		while(pre != cur) {
			pre = cur;
			cur = (cur + n / cur) / 2;
		}
		return (int)cur;
	}
	/**
	 * implement csqrt(int a)
	 * using binary search
	 */
	public int csqrt(int a) {
		boolean neg = a < 0;
		long num = a;
		num = Math.abs(num);
		long L = 0;
		long R = num;
		while(L <= R) {
			long mid = L + (R - L) / 2;
			if(mid * mid * mid == a || mid * mid * mid < a && (mid+1)*(mid+1)*(mid+1) > a) {
				return neg ? (int)(-mid) : (int)mid;
			}
			else if(mid * mid * mid > a) R = mid - 1;
			else L = mid + 1;
		}
		return neg ? (int)(-L) : (int)L;
	}
	/**
	 * implement cube sqrt with newton methods
	 * x(i+1) = (a/x^2+2*x)/3
	 */
	public int csqrtNT(int a) {
		double pre = 0;
		double cur = 1;
		while(Math.abs(pre-cur) > 1e-9) {
			pre = cur;
			cur = (2*cur+a/(cur*cur)) / 3;
		}
		return (int)cur;
	}

	/**
	 * compute log2(N) with sqrt
	 * log2(N) = 2 * log2(sqrt(N))
	 * when N is near 1, log2(N) = (N-1) / ln(2) = (N-1)/0.693147
	 * Time complexity: O(log(N))
	 */
	public double xlog(double N) {
		if(N <= 0) throw new ArithmeticException();
		if(N - 1 < 1e-8) return (N-1)/0.693147;
		return 2 * xlog(Math.sqrt(N));
	}
	//iteration
	public double xlogII(double N) {
		if(N <= 0) throw new ArithmeticException();
		double res = 1;
		while(N - 1 > 1e-8) {
			N = Math.sqrt(N);
			res *= 2;
		}
		return res * (N-1)/0.693147;
	}
	/**
	 * implement + with bit manipulation
	 */
	public int add(int a, int b) {
		while(b != 0) {
			int c = a & b;//carry bits
			a = a ^ b; //add without carry bits
			b = c << 1; //move carry bit to left by 1
		}
		return a;
	}
	/**
	 * implement *
	 * use divide and conquer
	 * Time complexity: O(log(N))
	 * recursion
	 */
	public int multiply(int a, int b) {
		int sign = ((a ^ b) >> 31) == 0 ? 1 : -1;
		long n1 = a;
		long n2 = b;
		n1 = n1 < 0 ? -n1 : n1;
		n2 = n2 < 0 ? -n2 : n2;
		long res = mulhelper(n1,n2);
		return (int)(res * sign);
	}
	private long mulhelper(long a, long b) {
		if(b == 0) return 0;
		long res = a;
		long time = 1;
		while(time < b) {
			time = time << 1;
			res = res << 1;
		}
		if(time == b) return res;
		return (res >> 1) + mulhelper(a,b-(time >> 1));
	}
	/**
	 * implement *
	 * iteration
	 */
	public int multiplyII(int a, int b) {
		int sign = ((a ^ b >> 31)) == 0 ? 1 : -1;
		if(b == 0) return 0;
		long n1 = a;
		long n2 = b;
		n1 = n1 < 0 ? -n1 : n1;
		n2 = n2 < 0 ? -n2 : n2;
		long res = 0;
		while(n2 > 0) {
			long t = 1;
			long val = a;
			while(t < n2) {
				t = t << 1;
				val = val << 1;
			}
			if(t == n2) {
				res += val;break;
			} else {
				res += (val >> 1);
				n2 = n2 - (t >> 1);
			}
		}
		return (int)(res * sign);
	}
	/**
	 * implement /
	 * recursion
	 */
	public int divide(int a, int b) {
		if(b == 0) throw new ArithmeticException();
		int sign = ((a ^ b) >> 31) == 0 ? 1 : -1;
		long n1 = a;
		long n2 = b;
		n1 = n1 < 0 ? -n1 : n1;
		n2 = n2 < 0 ? -n2 : n2;
		long res = dividehelper(n1,n2);
		return (int)(res * sign);
	}
	private long dividehelper(long a, long b) {
		if(a < b) return 0;
		long tmp = b;
		long quo = 1;
		while(tmp < a) {
			tmp = tmp << 1;
			quo = quo << 1;
		}
		if(tmp == a) return quo;
		return (quo >> 1) + dividehelper(a-(tmp >> 1),b);
	}
	/**
	 * iteration
	 */
	public int divideII(int a, int b) {
		if(b == 0) throw new ArithmeticException();
		int sign = ((a ^ b) >> 31) == 0 ? 1 : -1;
		long n1 = a;
		long n2 = b;
		n1 = n1 < 0 ? -n1 : n1;
		n2 = n2 < 0 ? -n2 : n2;
		long res = 0;
		while(n1 >= n2) {
			long tmp = n2;
			long quo = 1;
			while(tmp < n1) {
				tmp = tmp << 1;
				quo = quo << 1;
			}
			if(tmp == n1) {
				res += quo;
				break;
			} else {
				res += (quo >> 1);
				n1 = n1 - (tmp >> 1);
			}
		}
		return (int)(res * sign);
	}
	/**
	 * implement pow(double x,int n)
	 * not handle n = Integer.MIN_VALUE.
	 * recursion
	 */
	public double pow(double x,int n) {
		if(n == 0) return 1;
		if(n < 0) return 1/pow(x,-n);
		double tmp = pow(x,n/2);
		return n % 2 == 0 ? tmp * tmp : tmp * tmp * x;
	}
	/**
	 * iteration
	 * N = 2^0*a0+2^1*a1+....+2^m*am
	 * in every iteration, we try to fetch a0
	 */
	public double powII(double x, int n) {
		long N = n;
		N = N < 0 ? -N : N;
		double base = x;
		double res = 1;
		while(N > 0) {
			if(N % 2 == 1) {
				res = res * base;
			}
			base = base * base; //compute x^(2^i)
			N /= 2;
		}
		return n >= 0 ? res : 1 / res;
	}
	//integer plus one without + (a is not negative)
	public int addOne(int a) {
		int v = 1;
		int n = a;
		while(n != 0 && (n & 1) == 1) {
			n = n >> 1;
			v = v << 1;
		}
		return a & (~(v-1)) | v;
	}
	/**
	 * add two numbers represented as string
	 */
	public String addNumber(String s1, String s2) {
		if(s1 == null || s1.length() == 0) return s2;
		if(s2 == null || s2.length() == 0) return s1;
		char[] chs1 = s1.toCharArray();
		char[] chs2 = s2.toCharArray();
		char[] res = new char[Math.max(chs1.length,chs2.length)+1];//max length
		int i = chs1.length - 1;
		int j = chs2.length - 1;
		int carry = 0;
		int index = res.length - 1;
		while(i >= 0 || j >= 0) {
			int v1 = i < 0 ? 0 : chs1[i--] - '0';
			int v2 = j < 0 ? 0 : chs2[j--] - '0';
			int v = v1 + v2 + carry;
			res[index--] = (char)(v % 10 + '0');
			carry = v / 10;
		}
		res[0] = (char)(carry+'0');
		int start = res[0] == '0' ? 1 : 0; //starting index for copy
		return new String(res,start,res.length - start);
	}
	/**
	 * multiply two numbers represented as string
	 */
	public String mulNumber(String s1, String s2) {
		if(s1 == null || s1.length() == 0 || s1 == null || s2.length() == 0) return "";
		char[] chs1 = s1.toCharArray();
		char[] chs2 = s2.toCharArray();
		int[] res = new int[chs1.length + chs2.length];
		int offset = 0;
		for(int i = chs1.length - 1; i >= 0; i--) {
			int index = res.length - 1 - offset;
			for(int j = chs2.length - 1; j >= 0; j--) {
				int v = (chs1[i] - '0') * (chs2[j] - '0') + res[index];
				res[index] = v % 10;
				res[index-1] += v / 10;
				index--;
			}
			offset++;
		}
		StringBuilder sb = new StringBuilder();
		int start = res[0] == 0 ? 1 : 0;
		for(int i = start; i < res.length; i++) {
			sb.append(res[i]);
		}
		return sb.toString();
	}
	/**
	 * check convex polygons
	 * use cross product
	 * check pi+1pi * pi+1pi+2
	 */
	public boolean isConvex(Point[] points) {
		if(points == null || points.length < 3) return false;
		boolean negcrossprod = false;
		for(int i = 0; i < points.length; i++) {
			Point p1 = points[i];
			Point p2 = points[(i+1) % points.length];
			Point p3 = points[(i+2) % points.length];
			int dx1 = p2.x - p1.x;
			int dy1 = p2.y - p1.y;
			int dx2 = p2.x - p3.x;
			int dy2 = p2.y - p3.y;
			if(i == 0) {
				negcrossprod = (dx1 * dy2 - dx2 * dy1) < 0;
			} else {
				if(negcrossprod != (dx1 * dy2 - dx2 * dy1) < 0) return false;
			}
		}
		return true;
	}
	/**
	 * compute fibnacci number O(log(N))
	 */
	public int fib(int N) {
		if(N < 0) return -1;
		if(N == 0) return 1;
		int[][] f = new int[][]{{1,1},{1,0}};
		fibhelper(f,N);
		return f[0][0];
	}
	private void fibhelper(int[][] f, int N) {
		if(N == 1) return;
		fibhelper(f,N/2);
		multiply(f,f);
		if(N % 2 == 1) {
			multiply(f,new int[][]{{1,1},{1,0}});
		}
	}
	private void multiply(int[][] f, int[][] g) {
		int a = f[0][0] * g[0][0] + f[0][1] * g[1][0];
		int b = f[0][0] * g[0][1] + f[0][1] * g[1][1];
		int c = f[1][0] * g[0][0] + f[1][1] * g[1][0];
		int d = f[1][0] * g[0][1] + f[1][1] * g[1][1];
		f[0][0] = a;
		f[0][1] = b;
		f[1][0] = c;
		f[1][1] = d;
	}
	//the fastest way using formular
	//F(n) = 1/sqrt(5) *((1+sqrt(5)/2)^n - ((1-sqrt(5))/2)^n)
	/**
	 * Knuth shuffle
	 */
	public void shuffle(int[] array) {
		if(array == null || array.length == 0) return;
		Random r = new Random();
		for(int i = 0; i < array.length; i++) {
			int randindex = i + r.nextInt(array.length - i);
			int tmp = array[randindex];
			array[randindex] = array[i];
			array[i] = tmp;
		}
	}
}

class Point {
	public int x;
	public int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
