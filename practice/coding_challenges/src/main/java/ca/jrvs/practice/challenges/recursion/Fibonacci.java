package ca.jrvs.practice.challenges.recursion;

import java.util.Map;

public class Fibonacci {

  public int fibRecurs(int n) {
    if (n == 0) return 1;
    if (n == 1) return 1;

    return fibRecurs(n - 1) + fibRecurs(n - 2);
  }

  public int fibDynamicCache(int n, Map<Integer,Integer> map) {
    if (n == 0) return 1;
    if (n == 1) return 1;

    if (map.containsKey(n)) return map.get(n);
    int result = fibDynamicCache(n - 1, map) + fibDynamicCache(n - 2, map);
    map.put(n, result);
    return result;
  }

  public int fibDynamic(int n) {
    int[] arr = new int[n];
    arr[0] = 1;
    arr[1] = 1;
    for (int i = 2; i < n; i++) {
      arr[i] = arr[i - 1] + arr[i - 2];
    }
    return arr[n-1];
  }
}
