package ca.jrvs.practice.challenges.recursion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ValidAnagram {

  public boolean approachOne(String one, String two) {

    char[] arr1 = one.toCharArray();
    char[] arr2 = two.toCharArray();

    Arrays.sort(arr1);
    Arrays.sort(arr2);

    return Arrays.equals(arr1, arr2);
  }

  public boolean approachTwo(String one, String two) {
    Map<Character, Integer> map = new HashMap<>();
    if (one.length() != two.length()) {
      return false;
    }

    for (Character c : one.toCharArray()) {
      if (map.containsKey(c)) {
        map.replace(c, map.get(c) + 1);
      } else {
        map.put(c, 1);
      }
    }

    for (Character k : two.toCharArray()) {
      if (map.get(k) != null) {
        map.replace(k, map.get(k) - 1);
        if (map.get(k) < 0) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }

  public boolean approachThree(String s, String t) {
    if (s.length() != t.length()) {
      return false;
    }
    int[] table = new int[26];
    for (int i = 0; i < s.length(); i++) {
      table[s.charAt(i) - 'a']++;
    }
    for (int i = 0; i < t.length(); i++) {
      table[t.charAt(i) - 'a']--;
      if (table[t.charAt(i) - 'a'] < 0) {
        return false;
      }
    }
    return true;
  }
}
