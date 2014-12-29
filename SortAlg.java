import java.util.*;

/**
 * This file contains merge sort, quick sort, heap sort, radix sort, counting sort and bucket sort
 */

public class SortAlg {
	/**
	 * merge sort for array
	 * Time complexity: O(nlog(n))
	 * Space complexity: O(N)
	 * stable
	 */
	public <E> void mergesort(E[] array, Comparator<E> comp) {
		mergesort(array,0,array.length-1,comp);
	}
	private <E> void mergesort(E[] array, int start, int end, Comparator<E> comp) {
		if(start < end) {
			int mid = start + (end - start) / 2;
			mergesort(array,start,mid,comp);
			mergesort(array,mid+1,end,comp);
			merge(array,start,mid,end,comp);
		}
	}
	//left part [p,q], right part[q+1,r]
	private <E> void merge(E[] array, int p, int q, int r, Comparator<E> comp) {
		E[] left = (E[]) new Object[q-p+1];
		E[] right = (E[]) new Object[r-q];
		for(int i = p; i <= q; i++) {
			left[i-p] = array[i];
		}
		for(int i = q + 1; i <= r; i++) {
			right[i-q-1] = array[i];
		}
		int index = p;
		int L = 0;
		int R = 0;
		while(L < left.length || R < right.length) {
			if(L == left.length) array[index++] = right[R++];
			else if(R == right.length) array[index++] = left[L++];
			else {
				if(comp.compare(left[L],right[R]) < 0) {
					array[index++] = left[L++];
				} else {
					array[index++] = right[R++];
				}
			}
		}
	}
	/**
	 * merge sort for list
	 */
	public <E> ListNode<E> mergesort(ListNode<E> ls,Comparator<E> comp) {
		if(ls == null || ls.next == null) return ls;
		ListNode<E> mid = findMid(ls);
		ListNode<E> next = mid.next;
		mid.next = null;
		ListNode<E> ls1 = mergesort(ls,comp);
		ListNode<E> ls2 = mergesort(next,comp);
		return merge(ls1,ls2,comp);
	}
	private <E> ListNode<E> findMid(ListNode<E> ls) {
		ListNode<E> p = ls;
		ListNode<E> q = ls;
		while(q.next != null && q.next.next != null) {
			p = p.next;
			q = q.next.next;
		}
		return p;
	}
	private <E> ListNode<E> merge(ListNode<E> ls1, ListNode<E> ls2,Comparator<E> comp) {
		ListNode<E> dummy = new ListNode<E>(null);
		ListNode<E> p = dummy;
		while(ls1 != null || ls2 != null) {
			if(ls1 == null) {
				p.next = ls2;
				ls2 = ls2.next;
			}
			else if(ls2 == null) {
				p.next = ls1;
				ls1 = ls1.next;
			}
			else {
				if(comp.compare(ls1.val,ls2.val) < 0) {
					p.next = ls1;
					ls1 = ls1.next;
				} else {
					p.next = ls2;
					ls2 = ls2.next;
				}
			}
			p = p.next;
		}
		return dummy.next;
	}
	/**
	 * quick sort for array
	 */
	public <E> void quicksort(E[] array, Comparator<E> comp) {
		quicksort(array,0,array.length-1,comp);
	}
	private <E> void quicksort(E[] array,int start,int end,Comparator<E> comp) {
		if(start < end) {
			int pivot = partition(array,start,end,comp);
			quicksort(array,start,pivot-1,comp);
			quicksort(array,pivot+1,end,comp);
		}
	}
	private <E> int partition(E[] array, int p, int r, Comparator<E> comp) {
		int q = p - 1;
		Random rand = new Random();
		int rindex = rand.nextInt(r-p)+p;
		swap(array,rindex,r);
		while(p < r) {
			if(comp.compare(array[p],array[r]) < 0) {
				swap(array,p,++q);
			}
			p++;
		}
		swap(array,++q,r);
		return q;
	}
	private <E> void swap(E[] array, int i, int j) {
		E e = array[i];
		array[i] = array[j];
		array[j] = e;
	}
	/**
	 * quick sort, 3-way partition
	 */
	public <E> void quicksort3(E[] array, Comparator<E> comp) {
		quicksort3(array,0,array.length-1,comp);
	}
	private <E> void quicksort3(E[] array, int p, int r, Comparator<E> comp) {
		if(p < r) {
			int lo = p;
			int gt = r;
			int i = p;
			E key = array[p];
			while(i <= gt) {
				if(comp.compare(array[i],key) < 0) {
					swap(array,i++,lo++);
				}
				else if(comp.compare(array[i],key) == 0) {
					i++;
				} else {
					swap(array,i,gt--);
				}
			}
			quicksort3(array,p,lo-1,comp);
			quicksort3(array,gt+1,r,comp);
		}
	}

	/**
	 * heap sort for array
	 */
	public <E> void heapsort(E[] array, Comparator<E> comp) {
		if(array == null || array.length == 0) return;
		for(int i = array.length / 2; i >= 0; i--) {
			heapify(array,i,array.length,comp);
		}
		int heapsize = array.length;
		while(heapsize > 0) {
			swap(array,0,heapsize-1);
			heapsize--;
			heapify(array,0,heapsize,comp);
		}
	}
	private <E> void heapify(E[] array, int index, int heapsize,Comparator<E> comp) {
		while(index < heapsize) {
			int left = index*2+1;
			int right = index*2+2;
			int small = index;
			if(left < heapsize && comp.compare(array[small],array[left]) < 0) {
				small = left;
			}
			if(right < heapsize && comp.compare(array[small],array[right]) < 0) {
				small = right;
			}
			if(small == index) break;
			swap(array,small,index);
			index = small;
		}
	}
	/**
	 * radix sort for positive integer
	 * Time complexity: O(nk),k is the maximum length of the integer
	 * Space complexity: O(n). need a tmparay array
	 */
	public void radixsort(int[] array) {
		if(array == null || array.length == 0) return;
		boolean isend = false;
		int digit = 0;
		int[] old = array;
		while(! isend) {
			int[] count = new int[10];
			isend = true;
			//count digit
			for(int i = 0; i < array.length; i++) {
				int n = array[i] / (int)(Math.pow(10, digit)) % 10;
				if(n != 0) isend = false;
				count[n]++;
			}
			if(isend) break;
			for(int i = 1; i < count.length; i++) {
				count[i] += count[i-1];
			}
			//copy
			int[] tmp = new int[array.length];
			for(int i = array.length - 1; i >= 0; i--) {
				int n = array[i] / (int)(Math.pow(10, digit)) % 10;
				tmp[count[n]-1] = array[i];
				count[n]--;
			}
			array = tmp;
			digit++;
		}
		for(int i = 0; i < old.length; i++) {
			old[i] = array[i];
		}
	}
	/**
	 * radix sort for string
	 * sort in alphabeta order
	 * Time complexity: O(kn)
	 * Space complexity: O(kn)
	 */
	public void radixsort(String[] strs) {
		if(strs == null || strs.length == 0) return;
		int maxlen = 0;
		for(int i = 0; i < strs.length; i++) {
			maxlen = maxlen < strs[i].length() ? strs[i].length() : maxlen;
		}
		String[] old = strs;
		for(int i = maxlen - 1; i >= 0; i--) {
			//count sort
			int[] count = new int[256];
			for(int j = 0; j < strs.length; j++) {
				//padding as 0
				if(strs[j].length() <= i) {
					count[0]++;
				} else {
					count[strs[j].charAt(i)]++;
				}
			}
			for(int j = 1; j < count.length; j++) {
				count[j] += count[j-1];
			}
			//copy
			String[] tmp = new String[strs.length];
			for(int j = strs.length - 1; j >= 0; j--) {
				int index = strs[j].length() <= i ? 0 : strs[j].charAt(i);
				tmp[count[index]-1] = strs[j];
				count[index]--;
			}
			strs = tmp;
		}
		for(int i = 0; i < old.length; i++) {
			old[i] = strs[i];
		}
	}
	/**
	 * bucket sort for float number ranging from [0,1)
	 */
	public void bucketsort(double[] array) {
		if(array == null || array.length == 0) return;
		//create buckets
		ArrayList<Double>[] buckets = (ArrayList<Double>[]) new ArrayList[array.length];
		for(int i = 0; i < buckets.length; i++) {
			buckets[i] = new ArrayList<Double>();
		}
		//put elements in buckets
		for(int i = 0; i < array.length; i++) {
			int index = (int)(array[i] * array.length);
			buckets[index].add(array[i]);
		}
		//sort every buckets
		for(int i = 0; i < buckets.length; i++) {
			Collections.sort(buckets[i]);
		}
		//merge elements
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			for(Double d : buckets[i]) {
				array[index++] = d;
			}
		}
	}
	/**
	 * bucket sort for array
	 */
	public<E> void bucketsort(E[] array, Comparator<E> comp) {
		if(array == null || array.length == 0) return;
		ArrayList<E>[] buckets = (ArrayList<E>[]) new ArrayList[array.length];
		for(int i = 0; i < buckets.length; i++) {
			buckets[i] = new ArrayList<E>();
		}
		for(int i = 0; i < array.length; i++) {
			int index = array[i].hashCode() % buckets.length;
			buckets[index].add(array[i]);
		}
		for(int i = 0; i < buckets.length; i++) {
			Collections.sort(buckets[i],comp);
		}
		int index = 0;
		for(int i = 0; i < buckets.length; i++) {
			for(E e : buckets[i]) {
				array[index++] = e;
			}
		}
	}
	public static void main(String[] args) {
		SortAlg sol = new SortAlg();
		//String[] strs = new String[]{"php","C#","Basic","Linux","Erlang","Firefox","Object-C"};
		Integer[] its = new Integer[]{3,56,78,123,4,568,98};
		sol.bucketsort(its,new Comparator<Integer>(){
			public int compare(Integer s1, Integer s2) {
				if(s1 == s2) return 0;
				if(s1 < s2) return -1;
				return 1;
			}
		});
		for(Integer s : its)
			System.out.println(s);
	}
}

class ListNode<E> {
	public E val;
	public ListNode<E> next;
	public ListNode(E v) {
		val = v;
		next = null;
	}
}


