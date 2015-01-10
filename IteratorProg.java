import java.util.*;

/**
 * This file contains solutions for some iterator interview quesionts.
 *
 * 1. Linkedlist iterator
 * 2. nested list iterator!!! use stack
 * 3. BST preorder, inorder, postorder iterator
 * 4. flatten list iterator. [[7,8,9],[],[2,3,5],[1,19]]
 * 5. peak iterator: use another list to store the elements when using peek
 * 5. jump iterator: jump one element every time
 */

class ListNode {
	public int val;
	public ListNode next;
	public ListNode(int v) {
		val = v;
		next = null;
	}
}

class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode(int v) {
		val = v;
		left = null;
		right = null;
	}
}

//linkedlist iterator
class ListIterator {
	private ListNode head;
	public ListIterator(ListNode ls) {
		head = ls;
	}
	public boolean hasNext() {
		return head != null;
	}
	public int next() {
		int val = head.val;
		head = head.next;
		return val;
	}
}

//Iterator for nested list
//the element of the list can be object or list
class NestedListIterator {
	private Stack<Iterator> stack;
	private Object next;
	public NestedListIterator(List<Object> ls) {
		stack = new Stack<Iterator>();
		if(ls != null) {
			stack.push(ls.iterator());
		}
		next = null;
	}
	public boolean hasNext() {
		helper();
		return next != null;
	}
	public Object next() {
		return next;
	}
	private void helper() {
		next = null;
		while(! stack.isEmpty()) {
			Iterator it = stack.peek();
			if(! it.hasNext()) {
				stack.pop();
			} else {
				while(it.hasNext()) {
					Object obj = it.next();
					if(obj instanceof List) {
						stack.push(((List)obj).iterator()); break;//find a new composite pattern
					} else {
						next = obj;return;
					}
				}
			}
		}
	}
}

/**
 * BST pinorder iterator
 */
class BSTInorderIterator {
	private TreeNode p;
	private Stack<TreeNode> stack;
	public BSTInorderIterator(TreeNode t) {
		p = t;
		stack = new Stack<TreeNode>();
	}
	public boolean hasNext() {
		return p != null || ! stack.isEmpty();
	}
	public int next() {
		while(p != null) {
			stack.push(p);
			p = p.left;
		}
		p = stack.pop();
		int val = p.val;
		p = p.right;
		return val;
	}
}
/** 
 * BST pre order iterator
 */
class BSTPreorderIterator {
	private Stack<TreeNode> stack;
	public BSTPreorderIterator(TreeNode t) {
		stack = new Stack<TreeNode>();
		if(t != null) {
			stack.push(t);
		}
	}
	public boolean hasNext() {
		return ! stack.isEmpty();
	}
	public int next() {
		TreeNode t = stack.pop();
		if(t.right != null) stack.push(t.right);
		if(t.left != null) stack.push(t.left);
		return t.val;
	}
}
/**
 * BST post order iterator
 */
class BSTPostorderIterator {
	private TreeNode pre;
	private Stack<TreeNode> stack;
	public BSTPostorderIterator(TreeNode t) {
		stack = new Stack<TreeNode>();
		if(t != null) {
			stack.push(t);
		}
	}
	public boolean hasNext() {
		return ! stack.isEmpty();
	}
	public int next() {
		while(true) {
			TreeNode p = stack.peek();
			if(p.left == null && p.right == null || (pre != null && (pre == p.left || pre == p.right))) {
				pre = p;
				stack.pop();
				return p.val;
			} else {
				if(p.right != null) stack.push(p.right);
				if(p.left != null) stack.push(p.left);
			}
		}
	}
}

/**
 * flatten list iterator
 */

class FlattenListIterator {
	private List<List<Integer>> ls;
	private int[] indexes;
	private int currindex;
	public FlattenListIterator(List<List<Integer>> ls) {
		this.ls = ls;
		indexes = new int[ls.size()];
		currindex = 0;
	}
	public boolean hasNext() {
		int preindex = currindex;
		do {
			if(ls.get(currindex).size() == indexes[currindex]) {
				currindex = (currindex+1) % indexes.length; 
			} else {
				break;
			}

		} while(currindex != preindex);
		return ls.get(currindex).size() > indexes[currindex];
	}
	public int next() {
		int val = ls.get(currindex).get(indexes[currindex]);
		indexes[currindex]++;
		currindex = (currindex + 1) % indexes.length;
		return val;
	}
}
/**
 * peak iterator
 */
class PeekIterator {
	private Iterator<Integer> it;
	private LinkedList<Integer> store;
	public PeekIterator(Iterator<Integer> it) {
		this.it  = it;
		store = new LinkedList<Integer>();
	}
	public boolean hasNext() {
		return it.hasNext() || ! store.isEmpty();
	}
	public int next() {
		if(!store.isEmpty()) return store.poll();
		return it.next();
	}
	public int peek() {
		if(store.isEmpty()) {
			store.add(it.next());
		}
		return store.peekFirst();
	}
}
/**
 * jump iterator
 */
class JumpIterator {
	private Iterator<Integer> it;
	public JumpIterator(Iterator<Integer> it) {
		this.it = it;
	}
	public boolean hasNext() {
		if(!it.hasNext()) return false;
		it.next();
		return it.hasNext();
	}
	public int next() {
		return it.next();
	}
}

public class IteratorProg {
	public static void main(String[] args) {

	}
}



