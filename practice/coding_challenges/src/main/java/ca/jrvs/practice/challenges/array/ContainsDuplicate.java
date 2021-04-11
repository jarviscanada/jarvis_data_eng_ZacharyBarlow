package ca.jrvs.practice.challenges.array;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicate {

  public boolean containsDupSet(int[] nums) {
    Set<Integer> s = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
      s.add(nums[i]);
    }
    return s.size() != nums.length;
  }

  public boolean containsDup(int[] nums) {
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 1; ++i) {
      if (nums[i] == nums[i + 1]) return true;
    }
    return false;
  }
}
