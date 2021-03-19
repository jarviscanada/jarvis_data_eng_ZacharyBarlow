package ca.jrvs.practice.challenges.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TwoSum {

  /**
   * Brute Force approach. Two loops
   * @param nums
   * @param target
   */
  public List<Integer> approachOne(int[] nums, int target) {
    List<Integer> ret = new ArrayList<>();

    for (int i = 0; i < nums.length; i++) {
      for (int j = 0; j < nums.length; j++) {
        if (nums[i] == target - nums[j] && i != j) {
          ret.add(i);
        }
      }
    }
    return ret;
  }

  /**
   * Sorting : built in APIs
   * @param nums
   * @param target
   */
  public void approachTwo(int[] nums, int target) {

  }

  /**
   * O(n) -> hashmap
   * @param nums
   * @param target
   */
  public List<Integer> approachThree(int[] nums, int target) {
    HashMap<Integer, Integer> map = new HashMap<>();
    List<Integer> ret = new ArrayList<>();

    for (int i = 0; i < nums.length; i++) {
      int n = target - nums[i];
      if (!map.containsKey(n)) {
        map.put(nums[i], i);
      } else {
        ret.add(map.get(n));
        ret.add(i);
        return ret;
      }
    }
    return ret;
  }

  public static void main(String[] args) {
    int[] n = {2,7,11,15};
    TwoSum ts = new TwoSum();
    List<Integer> nums = ts.approachOne(n, 9);
    System.out.println(nums);

//    List<Integer> nums2 = ts.approachThree(n, 9);
//    System.out.println(nums2);

    List<Integer> nums3 = ts.approachThree(n, 9);
    System.out.println(nums3);
  }
}
