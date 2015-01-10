import java.util.*;

/**
 * This file contains solutions for coins combination problem
 * 1. find the minimum number of coins for a quota N
 * 2. find the solution for the minimum number of coins
 * 3. find the total number of combinations for a quota N
 * 4. find out the sollutions
 */

public class CoinCombination {
	public static void main(String[] args) {
		CoinCombination sol = new CoinCombination();
		int[] coins = new int[]{1,2,5,10,25};
	}
	/**
	 * problem 1, find the minimum coin number for a quota N
	 * dynamical programming
	 */
	public int minimumQuota(int[] coins, int N) {
		if(coins == null || coins.length == 0 || N <= 0) return 0;
		int[] dp = new int[1+N];
		for(int i = 0; i < dp.length; i++) {
			dp[i] = -1;//mark the solution is not yet found for i
		}
		dp[0] = 0;//0 coin has quota 0
		for(int i = 1; i <= N; i++) {
			for(int j = 0; j < coins.length; j++) {
				if(i >= coins[j] && dp[i-coins[j]] != -1) {
					dp[i] = (dp[i] == -1 || dp[i] > dp[i-coins[j]] + 1)? dp[i-coins[j]] + 1  : dp[i];
				}
			}
		}
		return dp[N];
	}
	/**
	 * problem 2, find the solution for the minimum coin number for a quota N
	 */
	public List<Integer> minimumQuotaSolution(int[] coins, int N) {
		if(coins == null || coins.length == 0 || N <= 0) return new LinkedList<Integer>();
		int[] preindex = new int[N+1];
		preindex[0] = -1;
		int[] dp = new int[N+1];
		dp[0] = 0;
		for(int i = 1; i <= N; i++) {
			preindex[i] = -1;//not yet find solution
			dp[i] = -1;
			for(int j = 0; j < coins.length; j++) {
				if(i >= coins[j] && dp[i-coins[j]] != -1) {
					if(dp[i] == -1 || dp[i] > dp[i-coins[j]] + 1) {
						dp[i] = dp[i-coins[j]] + 1;
						preindex[i] = i - coins[j];
					}
				}
			}
		}
		//build the solution
		List<Integer> res = new LinkedList<Integer>();
		for(int i = N; i >= 0; i = preindex[i]) {
			if(preindex[i] != -1) {
				res.add(i - preindex[i]);
			}
		}
		return res;
	}
	/**
	 * problem 3, find the total number to represent the quota N
	 * dynamical programming
	 * O(NM)
	 */
	public int coinCombinationNumber(int[] coins, int N) {
		if(coins == null || coins.length == 0 || N <= 0) return 0;
		int[][] dp = new int[coins.length+1][N+1];
		//no coins cannot combine N
		for(int i = 0; i <= N; i++) {
			dp[0][i] = 0;
		}
		//i coins have one way to combine 0
		for(int i = 0; i <= coins.length; i++) {
			dp[i][0] = 1;
		}
		for(int i = 1; i <= coins.length; i++) {
			for(int j = 1; j <= N; j++) {
				for(int k = 0; k <= j / coins[i-1]; k++) {
					dp[i][j] += dp[i-1][j-k*coins[i-1]];
				}
			}
		}
		return dp[coins.length][N];
	}
	/**
	 * problem 4, find the solutions for all the combinations
	 * DFS
	 * exponential
	 */
	public List<List<Integer>> coinCombinationSolution(int[] coins, int N) {
		if(coins == null || coins.length == 0 || N <= 0) return new LinkedList<List<Integer>>();
		List<List<Integer>> res = new LinkedList<List<Integer>>();
		List<Integer> path = new LinkedList<Integer>();
		dfs(coins,0,N,path,res);
		return res;
	}
	private void dfs(int[] coins, int pos, int N, List<Integer> path, List<List<Integer>> res) {
		if(N == 0) {
			res.add(new LinkedList<Integer>(path)); return;
		}
		if(pos == coins.length) return;
		for(int i = pos; i < coins.length; i++) {
			for(int j = 1; j <= N/coins[i]; j++) {
				for(int k = 0; k < j; k++) {
					path.add(coins[i]);
				}
				dfs(coins,i+1,N-j*coins[i],path,res);
				for(int k = 0; k < j; k++) {
					path.remove(path.size()-1);
				}
			}
		}
	}
}