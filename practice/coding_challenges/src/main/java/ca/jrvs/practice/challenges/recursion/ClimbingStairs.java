package ca.jrvs.practice.challenges.recursion;

import java.util.Map;

/*
You are climbing a staircase. It takes n steps to reach the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 */
public class ClimbingStairs {

  public int stairsRecursive(int n) {
    if (n == 1) return 1;
    if (n == 2) return 2;

    return stairsRecursive(n - 1) + stairsRecursive(n - 2);
  }

  public int stairsMemo(int n, Map<Integer, Integer> map) {
    if (n == 1) return 1;
    if (n == 2) return 2;
    if (map.containsKey(n)) return map.get(n);

    int result = stairsMemo(n - 1, map) + stairsMemo(n - 2, map);
    map.put(n, result);
    return result;
  }

  public int stairsDynamic(int n) {
    int[] arr = new int[n];

    arr[0] = 1;
    arr[1] = 2;
    for (int i = 2; i < n; i++) {
      arr[i] = arr[i-1] + arr[i-2];
    }
    return arr[n-1];
  }
}
